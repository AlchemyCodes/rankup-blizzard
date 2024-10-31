package blizzard.development.fishing.listeners;

import blizzard.development.fishing.Main;
import blizzard.development.fishing.listeners.fishing.FishingListener;
import blizzard.development.fishing.listeners.items.FishBucketListener;
import blizzard.development.fishing.listeners.items.FishingNetListener;
import blizzard.development.fishing.listeners.items.FishingRodListener;
import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;

import java.util.Arrays;

public class ListenerRegistry {

    public void register() {
        PluginManager pluginManager = Bukkit.getPluginManager();

        Arrays.asList(
                new FishingListener(),
                new TrafficListener(),
                new FishBucketListener(),
                new FishingNetListener(),
                new FishingRodListener()
        ).forEach(listener -> pluginManager.registerEvents(listener, Main.getInstance()));
    }
}
