package blizzard.development.clans.managers;

import blizzard.development.clans.listeners.clans.ClansBankListener;
import blizzard.development.clans.listeners.clans.ClansChangesListener;
import blizzard.development.clans.listeners.clans.ClansCreationListener;
import blizzard.development.clans.listeners.clans.ClansInviteListener;
import com.nickuc.chat.api.events.PublicMessageEvent;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class ChatManager implements Listener {

    @EventHandler(priority = EventPriority.HIGH, ignoreCancelled = true)
    public void onPublicMessage(PublicMessageEvent event) {
        Player player = event.getSender();
        if (shouldCancelChat(player)) {
            event.setCancelled(true);
        }
    }

    @EventHandler(priority = EventPriority.HIGH, ignoreCancelled = true)
    public void onAsyncPlayerChat(AsyncPlayerChatEvent event) {
        Player player = event.getPlayer();
        if (shouldCancelChat(player)) {
            event.setCancelled(true);
        }
    }

    private boolean shouldCancelChat(Player player) {
        return ClansBankListener.pendingTransactions.containsKey(player) ||
                ClansChangesListener.pendingChanges.containsKey(player) ||
                ClansCreationListener.pendingCreates.containsKey(player) ||
                ClansInviteListener.pendingInvites.containsKey(player);
    }
}