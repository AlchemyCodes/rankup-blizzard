package blizzard.development.plantations.listeners;

import blizzard.development.plantations.Main;
import blizzard.development.plantations.listeners.plantation.PlantationListener;
import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;

import java.util.Arrays;

public class ListenerRegistry {

    public void register() {
        PluginManager pluginManager = Bukkit.getPluginManager();

        Arrays.asList(
                new PlantationListener()
        ).forEach(listener -> pluginManager.registerEvents(listener, Main.getInstance()));
    }
}
