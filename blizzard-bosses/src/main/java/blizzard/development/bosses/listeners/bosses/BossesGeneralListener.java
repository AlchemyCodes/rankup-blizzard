package blizzard.development.bosses.listeners.bosses;

import blizzard.development.bosses.methods.GeneralMethods;
import blizzard.development.bosses.utils.PluginImpl;
import blizzard.development.bosses.utils.items.apis.TextAPI;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerChangedWorldEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.ArrayList;

public class BossesGeneralListener implements Listener {

    @EventHandler
    public void onPlayerChangeWorld(PlayerChangedWorldEvent event) {
        Player player = event.getPlayer();
        if (GeneralMethods.getPlayerWorldState(player)) {
            GeneralMethods.removePlayerFromWorld(player);
        }
    }

    @EventHandler
    public void onPlayerQuitEvent(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        if (GeneralMethods.getPlayerWorldState(player)) {
            GeneralMethods.removePlayerFromWorld(player);
        }
    }

    @EventHandler
    public void onPlayerCommand(PlayerCommandPreprocessEvent event) {
        Player player = event.getPlayer();
        String message = event.getMessage();

        if (GeneralMethods.getPlayerWorldState(player) && !player.hasPermission("blizzard.bosses.admin")) {
            ArrayList<String> commands = new ArrayList<>(PluginImpl.getInstance().Config.getConfig().getStringList("bosses.commands-blacklist"));

            for (String command : commands) {
                if (message.equals(command)) {
                    player.sendActionBar(TextAPI.parse("§c§lEI! §cVocê não pode usar isso na área de bosses. §7(/bosses sair)"));
                    event.setCancelled(true);
                }
            }
        }
    }
}
