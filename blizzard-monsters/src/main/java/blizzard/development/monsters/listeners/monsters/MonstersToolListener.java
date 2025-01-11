package blizzard.development.monsters.listeners.monsters;

import blizzard.development.monsters.builders.ItemBuilder;
import blizzard.development.monsters.inventories.tools.RadarInventory;
import blizzard.development.monsters.monsters.managers.world.MonstersWorldManager;
import blizzard.development.monsters.utils.PluginImpl;
import blizzard.development.monsters.utils.items.TextAPI;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

public class MonstersToolListener implements Listener {

    @EventHandler
    public void onDropItem(PlayerDropItemEvent event) {
        Player player = event.getPlayer();

        if (MonstersWorldManager.getInstance().containsPlayer(player)) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onRadarInteract(PlayerInteractEvent event) {
        if (event.getAction().isRightClick()) {
            Player player = event.getPlayer();

            ItemStack item = player.getInventory().getItemInMainHand();

            if (ItemBuilder.hasPersistentData(PluginImpl.getInstance().plugin, item, "blizzard.monsters.compass")) {
                if (MonstersWorldManager.getInstance().containsPlayer(player)) {
                    RadarInventory.getInstance().open(player, 1);
                } else {
                    player.sendActionBar(TextAPI.parse("§c§lEI! §cVocê só pode interagir com isso no mundo de monstros."));
                }
                event.setCancelled(true);
            }
        }
    }
}
