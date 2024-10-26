package blizzard.development.spawners.listeners;

import blizzard.development.spawners.database.dao.PlayersDAO;
import blizzard.development.spawners.listeners.commons.PlayersJoinListener;
import blizzard.development.spawners.listeners.commons.PlayersQuitListener;
import blizzard.development.spawners.utils.PluginImpl;
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
