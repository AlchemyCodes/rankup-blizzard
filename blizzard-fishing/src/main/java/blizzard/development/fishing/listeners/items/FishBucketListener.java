package blizzard.development.fishing.listeners.items;

import blizzard.development.fishing.handlers.FishBucketHandler;
import blizzard.development.fishing.handlers.FishingNetHandler;
import blizzard.development.fishing.handlers.FishingRodHandler;
import blizzard.development.fishing.inventories.items.FishBucketInventory;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

public class FishBucketListener implements Listener {

    @EventHandler
    public void onInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();

        if (!(FishBucketHandler.isBucket(player))) return;

        if (event.getItem() == null) return;

        FishBucketInventory.openBucket(player);
        event.setCancelled(true);
        player.sendMessage("é balde");
    }
}
