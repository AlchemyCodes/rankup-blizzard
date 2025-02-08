package blizzard.development.mine.listeners.mine;

import blizzard.development.mine.database.cache.methods.PlayerCacheMethods;
import blizzard.development.mine.utils.PluginImpl;
import com.nickuc.chat.api.events.PublicMessageEvent;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

import java.util.List;
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


    @EventHandler
    public void onCommandProcess(PlayerCommandPreprocessEvent event) {
        List<String> commands = PluginImpl.getInstance().Config.getConfig().getStringList("mine.command-whitelist");

        String command = event.getMessage().split(" ")[0];

        Player player = event.getPlayer();

        if (!commands.contains(command)
                && PlayerCacheMethods.getInstance().isInMine(player)
                && !player.hasPermission("blizzard.mine.command-bypass")
        ) {
            player.sendActionBar("§c§lEI! §cVocê só pode executar esse comando fora da mina §7(/mina sair)§c.");
            event.setCancelled(true);
        }
    }
}
