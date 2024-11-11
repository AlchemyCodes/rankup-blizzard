package blizzard.development.plantations.listeners.plantation;

import blizzard.development.plantations.Main;
import blizzard.development.plantations.database.cache.methods.PlayerCacheMethod;
import blizzard.development.plantations.database.cache.methods.ToolCacheMethod;
import blizzard.development.plantations.managers.upgrades.ToolUpgradeManager;
import blizzard.development.plantations.managers.upgrades.agility.AgilityManager;
import blizzard.development.plantations.managers.upgrades.explosion.ExplosionManager;
import blizzard.development.plantations.plantations.events.PlantationBreakEvent;
import blizzard.development.plantations.plantations.item.ToolBuildItem;
import blizzard.development.plantations.tasks.PlantationRegenTask;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static blizzard.development.plantations.builder.ItemBuilder.getPersistentData;
import static blizzard.development.plantations.builder.ItemBuilder.hasPersistentData;
import static blizzard.development.plantations.tasks.HologramTask.initializeHologramTask;

public class PlantationBreakListener implements Listener {

    private final PlayerCacheMethod playerCacheMethod = new PlayerCacheMethod();
    private final ToolCacheMethod toolCacheMethod = ToolCacheMethod.getInstance();
    public static Map<Player, Block> plantations = new HashMap<>();

    private final List<Material> plantationsBlock = Arrays.asList(
            Material.POTATOES,
            Material.CARROTS,
            Material.BEETROOTS,
            Material.WHEAT
    );

    @EventHandler
    public void onBreak(BlockBreakEvent event) {
        Player player = event.getPlayer();
        Block block = event.getBlock();
        ItemStack item = player.getInventory().getItemInMainHand();

        if (!playerCacheMethod.isInPlantation(player)) {
            return;
        }

        if (!plantationsBlock.contains(block.getType())) {
            return;
        }

        if (!hasPersistentData(Main.getInstance(), item, "ferramenta")) {
            player.sendActionBar("§c§lEI! §cUse uma ferramenta da estufa para isso.");
            event.setCancelled(true);
            return;
        }

        Material originalType = block.getType();
        PlantationBreakEvent plantationBreakEvent = new PlantationBreakEvent(player, block, originalType);
        Bukkit.getPluginManager().callEvent(plantationBreakEvent);

        if (plantationBreakEvent.isCancelled()) {
            event.setCancelled(true);
            return;
        }

    }


    @EventHandler
    public void onPlantationBreak(PlantationBreakEvent event) {
        Player player = event.getPlayer();
        Block block = event.getBlock();
        Material originalType = event.getOriginalType();
        ItemStack item = player.getInventory().getItemInMainHand();

        if (!playerCacheMethod.isInPlantation(player)) {
            return;
        }

        if (hasPersistentData(Main.getInstance(), item, "ferramenta.arar")) {
            player.sendMessage("");
            player.sendMessage(" §c§lEI! §cUse uma ferramenta");
            player.sendMessage(" §cadequada para isso §l⚠§c.");
            player.sendMessage("");
            event.setCancelled(true);
            return;
        }

        initializeHologramTask(player, block, block.getType());

        String id = getPersistentData(Main.getInstance(), item, "ferramenta-id");

        player.sendMessage(playerCacheMethod.getPlantations(player) + "");
        player.getInventory().setItemInMainHand(ToolBuildItem.tool(
                id,
                playerCacheMethod.getPlantations(player),
                toolCacheMethod.getBotany(id),
                toolCacheMethod.getAgility(id),
                toolCacheMethod.getExplosion(id),
                1
        ));

        plantations.put(player, block);

        ToolUpgradeManager.check(player);
        AgilityManager.check(player, id);
        ExplosionManager.check(player, block, id);

        plantations.forEach((players, plantations) -> {
            PlantationRegenTask.create(plantations, originalType, 5);
        });

        playerCacheMethod.setPlantations(player, playerCacheMethod.getPlantations(player) + 1);
        block.setType(Material.AIR);
    }
}