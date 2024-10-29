package blizzard.development.plantations.listeners;

import blizzard.development.plantations.Main;
import blizzard.development.plantations.database.dao.PlayerDAO;
import blizzard.development.plantations.listeners.geral.PlayerListener;
import blizzard.development.plantations.listeners.plantation.PlantationBreakListener;
import blizzard.development.plantations.listeners.plantation.PlantationPlaceListener;
import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;

import java.util.Arrays;

public class ListenerRegistry {

    private final PlayerDAO playerDAO;

    public ListenerRegistry(PlayerDAO playerDAO) {
        this.playerDAO = playerDAO;
    }

    public void register() {
        PluginManager pluginManager = Bukkit.getPluginManager();

        Arrays.asList(
                new PlantationBreakListener(),
                new PlantationPlaceListener(),
                new PlayerListener(playerDAO)
        ).forEach(listener -> pluginManager.registerEvents(listener, Main.getInstance()));
    }
}
