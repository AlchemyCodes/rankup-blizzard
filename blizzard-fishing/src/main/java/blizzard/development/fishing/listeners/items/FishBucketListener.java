package blizzard.development.fishing.listeners.items;

import blizzard.development.fishing.handlers.FishBucketHandler;
import blizzard.development.fishing.handlers.FishingNetHandler;
import blizzard.development.fishing.handlers.FishingRodHandler;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

public class FishBucketListener implements Listener {

    @EventHandler
    public void onInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        Action action = event.getAction();

        if (!(FishBucketHandler.isBucket(player))) return;

        if (event.getItem() == null) return;

        if (!(action == Action.RIGHT_CLICK_BLOCK || action == Action.RIGHT_CLICK_AIR)) {
            return;
        }

        event.setCancelled(true);
        player.sendMessage("é balde");
    }
}
