package blizzard.development.plantations.listeners.packets.plantation;

import blizzard.development.plantations.Main;
import blizzard.development.plantations.database.cache.methods.PlayerCacheMethod;
import blizzard.development.plantations.database.cache.methods.ToolCacheMethod;
import blizzard.development.plantations.managers.AreaManager;
import blizzard.development.plantations.managers.BlockManager;
import blizzard.development.plantations.managers.PlantationManager;
import blizzard.development.plantations.managers.upgrades.blizzard.BlizzardEffect;
import blizzard.development.plantations.managers.upgrades.blizzard.BlizzardManager;
import blizzard.development.plantations.managers.upgrades.explosion.ExplosionManager;
import blizzard.development.plantations.managers.upgrades.lightning.LightningManager;
import blizzard.development.plantations.managers.upgrades.xray.XrayManager;
import blizzard.development.plantations.plantations.enums.PlantationEnum;
import blizzard.development.plantations.plantations.item.ToolBuildItem;
import blizzard.development.plantations.tasks.PlantationRegenTask;
import blizzard.development.plantations.utils.packets.PacketUtils;
import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketEvent;
import com.comphenix.protocol.wrappers.BlockPosition;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import static blizzard.development.plantations.builder.ItemBuilder.getPersistentData;
import static blizzard.development.plantations.builder.ItemBuilder.hasPersistentData;
import static blizzard.development.plantations.tasks.HologramTask.initializeHologramTask;

public class PlantationBreakListener extends PacketAdapter {
    private static final Logger LOGGER = Logger.getLogger(PlantationBreakListener.class.getName());

    public PlantationBreakListener() {
        super(PacketAdapter.params(
            Main.getInstance(),
            PacketType.Play.Client.BLOCK_DIG
        ).optionAsync());
    }

    public static Map<Player, Block> plantations = new HashMap<>();

    @Override
    public void onPacketReceiving(PacketEvent event) {
        final var packet = event.getPacket();
        final var action = packet.getPlayerDigTypes().readSafely(0);

        PlayerCacheMethod playerCacheMethod = PlayerCacheMethod.getInstance();
        ToolCacheMethod toolCacheMethod = ToolCacheMethod.getInstance();

        if (action == null || switch (action) {
            case START_DESTROY_BLOCK, STOP_DESTROY_BLOCK, ABORT_DESTROY_BLOCK -> false;
            default -> true;
        }) {
            return;
        }

        final var player = event.getPlayer();
        final BlockPosition blockPosition = packet.getBlockPositionModifier().read(0);

        if (blockPosition == null) {
            return;
        }

        final var blockX = blockPosition.getX();
        final var blockY = blockPosition.getY();
        final var blockZ = blockPosition.getZ();

        ItemStack item = player.getInventory().getItemInMainHand();

        World world = player.getWorld();
        Block block = world.getBlockAt(blockX, blockY, blockZ);
        Block plantationToRegen = world.getBlockAt(blockX, blockY - 1, blockZ);

        if (!playerCacheMethod.isInPlantation(player)) {
            return;
        }

        if (player.getGameMode() == GameMode.SURVIVAL) {
            if (!hasPersistentData(Main.getInstance(), item, "ferramenta")) {
                player.sendActionBar("§c§lEI! §cUse uma ferramenta da estufa para isso.");
                event.setCancelled(true);
                return;
            }
        }

        if (!BlockManager.isPlantation(blockX, blockY, blockZ)) {
            return;
        }

        if (block.getType() == Material.FARMLAND) {
            return;
        }

        String id = getPersistentData(Main.getInstance(), item, "ferramenta-id");
        if (id == null || id.isEmpty()) {
            LOGGER.log(Level.WARNING, "Ferramenta sem ID encontrada para o jogador: " + player.getName());
            event.setCancelled(true);
            return;
        }

        int botany, agility, explosion, thunderstorm, xray, blizzard;
        try {
            botany = toolCacheMethod.getBotany(id);
            agility = toolCacheMethod.getAgility(id);
            explosion = toolCacheMethod.getExplosion(id);
            thunderstorm = toolCacheMethod.getThunderstorm(id);
            xray = toolCacheMethod.getXray(id);
            blizzard = toolCacheMethod.getBlizzard(id);
        } catch (NullPointerException e) {
            LOGGER.log(Level.SEVERE, "Erro ao buscar dados da ferramenta para ID: " + id, e);
            event.setCancelled(true);
            return;
        }

        plantations.put(player, plantationToRegen);

        initializeHologramTask(player, plantationToRegen, Material.POTATOES);
        PacketUtils.getInstance().sendPacket(
            player,
            plantationToRegen,
            Material.getMaterial(AreaManager.getInstance().getAreaPlantation(player)
            ));


        player.getInventory().setItemInMainHand(ToolBuildItem.tool(
            id,
            toolCacheMethod.getBlocks(id),
            botany,
            agility,
            explosion,
            thunderstorm,
            xray,
            blizzard,
            1
        ));

        ExplosionManager.check(player, plantationToRegen, id);
        LightningManager.check(player, plantationToRegen, id);
        XrayManager.check(player, plantationToRegen, id);
        BlizzardManager.check(player, id);

        plantations.forEach((p, plantation) -> {
            PlantationRegenTask.create(plantationToRegen, player, 5);
            PlantationManager.getInstance()
                .growthDelay(
                    player,
                    plantationToRegen
                );
        });

        int seeds = 0;

        switch (AreaManager.getInstance().getAreaPlantation(player)) {

            case "POTATOES":
                playerCacheMethod.setPlantations(
                    player,
                    playerCacheMethod.getPlantations(player) + 3
                );
                seeds = 3;
                break;
            case "CARROTS":
                playerCacheMethod.setPlantations(
                    player,
                    playerCacheMethod.getPlantations(player) + 8
                );
                seeds = 8;
                break;
            case "BEETROOTS":
                playerCacheMethod.setPlantations(
                    player,
                    playerCacheMethod.getPlantations(player) + 12
                );
                seeds = 12;
                break;
            case "WHEAT":
                playerCacheMethod.setPlantations(
                    player,
                    playerCacheMethod.getPlantations(player) + 16
                );
                seeds = 16;
        }

        playerCacheMethod.setBlocks(
            player,
            playerCacheMethod.getBlocks(player) + 1
        );

        if (BlizzardEffect.getInstance().blizzard.containsKey(player)) {
            playerCacheMethod.setPlantations(
                player,
                playerCacheMethod.getPlantations(player) + 20
            );
        }

        toolCacheMethod.setBlocks(
            id,
            toolCacheMethod.getBlocks(id) + 1
        );


        if (BlizzardEffect.getInstance().blizzard.containsKey(player)) {
            player.sendActionBar("§d§lEstufa! §8▼ §b§l+20 §b★ §8▶ §7[20% de Bônus] §8✎ §bNevasca ativada!");
        } else {
            player.sendActionBar("§d§lEstufa! §8▼ §a§l+" + seeds + " §a★ §8▶ §7[20% de Bônus]");
        }
    }
}