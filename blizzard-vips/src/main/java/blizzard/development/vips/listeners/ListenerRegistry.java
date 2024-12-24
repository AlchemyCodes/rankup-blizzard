package blizzard.development.vips.listeners;

import blizzard.development.vips.Main;
import blizzard.development.vips.database.dao.PlayersDAO;
import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;

import java.util.Arrays;

public class ListenerRegistry {

    public void register() {
        PluginManager pluginManager = Bukkit.getPluginManager();

        PlayersDAO playersDAO = new PlayersDAO();

        Arrays.asList(
                new TrafficListener(playersDAO)
        ).forEach(listener -> pluginManager.registerEvents(listener, Main.getInstance()));
    }
}
