package blizzard.development.time.listeners;

import blizzard.development.time.database.dao.PlayersDAO;
import blizzard.development.time.listeners.commons.PlayersJoinListener;
import blizzard.development.time.listeners.commons.PlayersQuitListener;
import blizzard.development.time.utils.PluginImpl;
import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;

import java.util.Arrays;

public class ListenerRegistry {
    private static ListenerRegistry instance;

    public void register() {
        PluginManager pluginManager = Bukkit.getPluginManager();
        PlayersDAO playersDAO = new PlayersDAO();

        Arrays.asList(
                new PlayersJoinListener(playersDAO),
                new PlayersQuitListener(playersDAO)
        ).forEach(listener -> pluginManager.registerEvents(listener, PluginImpl.getInstance().plugin));
    }

    public static ListenerRegistry getInstance() {
        if (instance == null) instance = new ListenerRegistry();
        return instance;
    }
}
