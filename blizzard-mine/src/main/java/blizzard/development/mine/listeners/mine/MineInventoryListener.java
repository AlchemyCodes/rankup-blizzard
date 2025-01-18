package blizzard.development.mine.listeners.mine;

import blizzard.development.mine.database.cache.methods.PlayerCacheMethods;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.event.inventory.InventoryInteractEvent;
import org.bukkit.event.player.PlayerDropItemEvent;

public class MineInventoryListener implements Listener {

    private final PlayerCacheMethods cacheMethods = PlayerCacheMethods.getInstance();

    @EventHandler
    public void onDropItem(PlayerDropItemEvent event) {
        Player player = event.getPlayer();

        if (cacheMethods.isInMine(player)) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();

        if (cacheMethods.isInMine(player)) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onInventoryDrag(InventoryDragEvent event) {
        Player player = (Player) event.getWhoClicked();

        if (cacheMethods.isInMine(player)) {
            event.setCancelled(true);
        }
    }
}
