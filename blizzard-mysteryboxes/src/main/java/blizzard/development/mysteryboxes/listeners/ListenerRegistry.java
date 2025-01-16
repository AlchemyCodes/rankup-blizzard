package blizzard.development.mysteryboxes.listeners;

import blizzard.development.mysteryboxes.listeners.mysterybox.PlayerUseMysteryBoxListener;
import blizzard.development.mysteryboxes.utils.PluginImpl;
import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;

import java.util.Arrays;

public class ListenerRegistry {

    public void register() {
        PluginManager pluginManager = Bukkit.getPluginManager();

        Arrays.asList(
            new PlayerUseMysteryBoxListener()
        ).forEach(listener -> pluginManager.registerEvents(listener, PluginImpl.getInstance().plugin));
    }
}
