package blizzard.development.excavation.listeners;

import blizzard.development.excavation.Main;
import blizzard.development.excavation.database.dao.ExcavatorDAO;
import blizzard.development.excavation.database.dao.PlayerDAO;
import blizzard.development.excavation.listeners.excavation.ExcavationListener;
import blizzard.development.excavation.listeners.player.PlayerListener;
import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;

import java.util.Arrays;

public class ListenerRegistry {

    private final ExcavatorDAO excavatorDAO;
    private final PlayerDAO playerDAO;

    public ListenerRegistry(ExcavatorDAO excavatorDAO, PlayerDAO playerDAO) {
        this.excavatorDAO = excavatorDAO;
        this.playerDAO = playerDAO;
    }

    public void register() {
        PluginManager pluginManager = Bukkit.getPluginManager();

        Arrays.asList(
                new PlayerListener(excavatorDAO, playerDAO),
                new ExcavationListener()
        ).forEach(listener -> pluginManager.registerEvents(listener, Main.getInstance()));
    }
}
