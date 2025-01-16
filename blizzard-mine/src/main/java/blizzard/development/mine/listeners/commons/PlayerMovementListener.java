package blizzard.development.mine.listeners.commons;

import blizzard.development.mine.managers.BlockManager;
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

        BlockManager blockManager = BlockManager.getInstance();

        if (blockManager.isBlock(eventTo.getBlockX(), eventTo.getBlockY(), eventTo.getBlockZ()
        ) || blockManager.isBlock(eventFrom.getBlockX(), eventFrom.getBlockY(), eventFrom.getBlockZ())
        ) event.setAllowed(true);
    }
}