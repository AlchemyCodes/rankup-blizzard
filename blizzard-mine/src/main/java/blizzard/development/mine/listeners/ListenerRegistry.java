package blizzard.development.mine.listeners;

import blizzard.development.mine.listeners.geral.PlayerListener;
import blizzard.development.mine.utils.PluginImpl;
import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;

import java.util.Arrays;

public class ListenerRegistry {

    public void register() {
        PluginManager pluginManager = Bukkit.getPluginManager();

        Arrays.asList(
            new PlayerListener()
        ).forEach(listener -> pluginManager.registerEvents(listener, PluginImpl.getInstance().plugin));
    }
}
