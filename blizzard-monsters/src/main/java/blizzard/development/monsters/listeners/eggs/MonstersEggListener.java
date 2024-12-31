package blizzard.development.monsters.listeners.eggs;

import blizzard.development.monsters.builders.ItemBuilder;
import blizzard.development.monsters.monsters.handlers.monsters.MonstersHandler;
import blizzard.development.monsters.monsters.handlers.packets.MonstersPacketsHandler;
import blizzard.development.monsters.monsters.handlers.world.MonstersWorldHandler;
import blizzard.development.monsters.utils.PluginImpl;
import blizzard.development.monsters.utils.items.TextAPI;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;
import org.bukkit.util.Vector;

import java.util.Arrays;
import java.util.List;
import java.util.Set;

public class MonstersEggListener implements Listener {

    private final Plugin plugin = PluginImpl.getInstance().plugin;

    @EventHandler
    public void onEggInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();

        if (event.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
            MonstersHandler handler = MonstersHandler.getInstance();
            MonstersPacketsHandler packetsHandler = MonstersPacketsHandler.getInstance();

            ItemStack item = player.getInventory().getItemInMainHand();
            boolean isMonsterEgg = ItemBuilder.hasPersistentData(plugin, item, "blizzard.monsters.monster");
            String eggType = ItemBuilder.getPersistentData(plugin, item, "blizzard.monsters.monster");

            if (isMonsterEgg) {
                if (!MonstersWorldHandler.getInstance().containsPlayer(player)) {
                    player.sendActionBar(TextAPI.parse("§c§lEI! §cVocê só pode chocar este ovo no mundo de monstros."));
                    event.setCancelled(true);
                    return;
                }

                ConfigurationSection monstersSection = handler.getSection();
                Set<String> monsters = handler.getAll();

                if (monstersSection == null || monsters == null) {
                    player.sendActionBar(TextAPI.parse("§c§lEI! §cOcorreu um erro ao encontrar os monstros na configuração."));
                    return;
                }

                if (!monsters.contains(eggType)) {
                    player.sendActionBar("§c§lEI! §cEste monstro não existe.");
                    return;
                }

                Block clickedBlock = event.getClickedBlock();
                if (clickedBlock == null) return;

                Location spawnLocation = calculateSpawnLocation(clickedBlock, event.getBlockFace());

                Vector direction = player.getLocation().toVector().subtract(spawnLocation.toVector());
                spawnLocation.setDirection(direction);

                String type = handler.getType(eggType);
                String displayName = handler.getDisplayName(eggType);
                Integer life = handler.getLife(eggType);
                String value = handler.getSkinValue(eggType);
                String signature = handler.getSkinSignature(eggType);

                packetsHandler.spawnMonster(
                        player,
                        type,
                        spawnLocation,
                        displayName,
                        life,
                        value,
                        signature
                );

                List<String> messages = Arrays.asList(
                        "",
                        "§b§l Você chocou um monstro!",
                        "§f O monstro " + displayName + "§f está a solta.",
                        "§7 [Use o radar e descubra sua localização!]",
                        ""
                );

                messages.forEach(player::sendMessage);
                manageStack(player);

                event.setCancelled(true);
            }
        }
    }

    private Location calculateSpawnLocation(Block block, BlockFace face) {
        Location location = block.getLocation();

        location.add(0.5, 0, 0.5);

        switch (face) {
            case UP:
                location.add(0, 1, 0);
                break;
            case NORTH:
                location.add(0, 0, -1);
                break;
            case SOUTH:
                location.add(0, 0, 1);
                break;
            case WEST:
                location.add(-1, 0, 0);
                break;
            case EAST:
                location.add(1, 0, 0);
                break;
            case DOWN:
                location.add(0, -1, 0);
                break;
        }

        return location;
    }

    private void manageStack(Player player) {
        ItemStack currentItem = player.getInventory().getItemInMainHand();
        if (currentItem.getAmount() > 1) {
            currentItem.setAmount(currentItem.getAmount() - 1);
        } else {
            player.getInventory().setItemInMainHand(null);
        }
    }
}
