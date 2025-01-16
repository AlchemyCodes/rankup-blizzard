package blizzard.development.mine.listeners.geral;

import blizzard.development.mine.managers.BlockManager;
import io.papermc.paper.event.player.PlayerFailMoveEvent;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerVelocityEvent;

public class MovementListener implements Listener {

    @EventHandler
    public void onFailMove(PlayerFailMoveEvent event) {
        if (event.getFailReason() != PlayerFailMoveEvent.FailReason.CLIPPED_INTO_BLOCK) {
            return;
        }

        final var eventFrom = event.getFrom();
        final var eventTo = event.getTo();

        if (BlockManager.isBlock(eventTo.getBlockX(), eventTo.getBlockY(), eventTo.getBlockZ()) ||
                BlockManager.isBlock(eventFrom.getBlockX(), eventFrom.getBlockY(), eventFrom.getBlockZ())) {
            event.setAllowed(true);
        }
    }
}