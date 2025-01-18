package blizzard.development.mine.listeners.mine;

import blizzard.development.mine.database.cache.methods.PlayerCacheMethods;
import com.nickuc.chat.api.events.PublicMessageEvent;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import java.util.Set;

public class MineChatListener implements Listener {

    // descomentar essa porra dps


//    @EventHandler
//    public void onPublicMessage(PublicMessageEvent event) {
//        Player sender = event.getSender();
//
//        if (event.getChannel().getName().equalsIgnoreCase("local")) {
//            PlayerCacheMethods cacheMethods = PlayerCacheMethods.getInstance();
//
//            Set<Player> recipients = event.getRecipients();
//            recipients.removeIf(player -> cacheMethods.isInMine(sender));
//        }
//    }
}
