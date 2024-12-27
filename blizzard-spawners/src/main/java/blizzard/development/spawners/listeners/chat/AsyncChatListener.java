package blizzard.development.spawners.listeners.chat;

import blizzard.development.spawners.listeners.chat.slaughterhouses.SlaughterhousesFriendsListener;
import blizzard.development.spawners.listeners.chat.spawners.SpawnerFriendsListener;
import blizzard.development.spawners.listeners.chat.spawners.SpawnerPurchaseListener;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class AsyncChatListener implements Listener {

    @EventHandler(priority = EventPriority.HIGH, ignoreCancelled = true)
    public void onAsyncPlayerChat(AsyncPlayerChatEvent event) {
        Player player = event.getPlayer();
        if (shouldCancelChat(player)) {
            event.setCancelled(true);
        }
    }

    private boolean shouldCancelChat(Player player) {
        return SpawnerFriendsListener.pendingInvites.containsKey(player)
                || SpawnerPurchaseListener.pendingPurchases.containsKey(player)
                || SlaughterhousesFriendsListener.pendingInvites.containsKey(player);
    }
}
