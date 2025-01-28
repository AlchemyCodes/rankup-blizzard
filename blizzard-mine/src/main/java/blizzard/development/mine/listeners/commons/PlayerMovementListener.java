package blizzard.development.mine.listeners.commons;

import blizzard.development.mine.managers.mine.BlockManager;
import io.papermc.paper.event.player.PlayerFailMoveEvent;
import org.bukkit.Location;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class PlayerMovementListener implements Listener {

    @EventHandler
    public void onFailMove(PlayerFailMoveEvent event) {
        if (event.getFailReason() != PlayerFailMoveEvent.FailReason.CLIPPED_INTO_BLOCK) return;

        Location eventFrom = event.getFrom();
        Location eventTo = event.getTo();

        BlockManager regionManager = BlockManager.getInstance();

        if (regionManager.hasBlock(eventTo.getBlockX(), eventTo.getBlockY(), eventTo.getBlockZ())
                || regionManager.hasBlock(eventFrom.getBlockX(), eventFrom.getBlockY(), eventFrom.getBlockZ())
        ) {
            event.setAllowed(true);
        }
    }
}