package blizzard.development.events.listeners;

import blizzard.development.events.Main;
import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;

import java.util.Arrays;

public class ListenerRegistry {

    public void register() {
        PluginManager pluginManager = Bukkit.getPluginManager();

        Arrays.asList(
            new SumoListener()
        ).forEach(listener -> pluginManager.registerEvents(listener, Main.getInstance()));
    }
}
