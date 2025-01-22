package blizzard.development.farm.listeners;

import blizzard.development.farm.listeners.commons.PlayerListener;
import blizzard.development.farm.listeners.storage.CactusDropListener;
import blizzard.development.farm.utils.PluginImpl;
import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;

import java.util.Arrays;

public class ListenerRegistry {

    public void register() {
        PluginManager pluginManager = Bukkit.getPluginManager();

        Arrays.asList(
            new CactusDropListener(),
            new PlayerListener()
        ).forEach(listener -> pluginManager.registerEvents(listener, PluginImpl.getInstance().plugin));
    }
}
