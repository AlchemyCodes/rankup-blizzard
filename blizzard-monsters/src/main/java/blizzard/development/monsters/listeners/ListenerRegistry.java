package blizzard.development.monsters.listeners;

import blizzard.development.monsters.database.dao.PlayersDAO;
import blizzard.development.monsters.listeners.commons.PlayersJoinListener;
import blizzard.development.monsters.listeners.commons.PlayersQuitListener;
import blizzard.development.monsters.listeners.eggs.MonstersEggListener;
import blizzard.development.monsters.listeners.monsters.MonstersToolListener;
import blizzard.development.monsters.listeners.monsters.MonstersWorldListener;
import blizzard.development.monsters.listeners.packets.PlayersPacketMoveListener;
import blizzard.development.monsters.utils.PluginImpl;
import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;

import java.util.Arrays;

public class ListenerRegistry {
    private static ListenerRegistry instance;

    public void register() {
        PluginManager pluginManager = Bukkit.getPluginManager();

        PlayersDAO playersDAO = new PlayersDAO();

        Arrays.asList(
                // commons
                new PlayersJoinListener(playersDAO),
                new PlayersQuitListener(playersDAO),
                // monsters
                new MonstersWorldListener(),
                // eggs
                new MonstersEggListener(),
                // tools
                new MonstersToolListener(),
                // packets
                new PlayersPacketMoveListener()
        ).forEach(listener -> pluginManager.registerEvents(listener, PluginImpl.getInstance().plugin));
    }

    public static ListenerRegistry getInstance() {
        if (instance == null) instance = new ListenerRegistry();
        return instance;
    }
}
