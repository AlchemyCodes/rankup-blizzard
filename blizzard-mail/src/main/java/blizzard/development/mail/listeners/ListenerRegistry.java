package blizzard.development.mail.listeners;

import blizzard.development.mail.Main;
import blizzard.development.mail.database.dao.PlayerDao;
import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;

import java.util.Arrays;

public class ListenerRegistry {

    public void register() {
        PluginManager pluginManager = Bukkit.getPluginManager();
        PlayerDao playerDao = new PlayerDao();

        Arrays.asList(
            new TrafficListener(playerDao)
        ).forEach(listener -> pluginManager.registerEvents(listener, Main.getInstance()));
    }
}
