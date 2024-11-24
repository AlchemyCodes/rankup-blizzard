package blizzard.development.fishing.listeners;

import blizzard.development.fishing.Main;
import blizzard.development.fishing.database.dao.PlayersDAO;
import blizzard.development.fishing.database.dao.RodsDAO;
import blizzard.development.fishing.listeners.fishing.FishingListener;
import blizzard.development.fishing.listeners.items.FishBucketListener;
import blizzard.development.fishing.listeners.items.FishingNetListener;
import blizzard.development.fishing.listeners.items.FishingRodListener;
import blizzard.development.fishing.listeners.items.FurnaceListener;
import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;

import java.util.Arrays;

public class ListenerRegistry {

    public void register() {
        PluginManager pluginManager = Bukkit.getPluginManager();

        PlayersDAO playersDAO = new PlayersDAO();
        RodsDAO rodsDAO = new RodsDAO();

        Arrays.asList(
                new FishingListener(),
                new TrafficListener(playersDAO, rodsDAO),
                new FishBucketListener(),
                new FishingNetListener(),
                new FishingRodListener(),
                new FurnaceListener()
        ).forEach(listener -> pluginManager.registerEvents(listener, Main.getInstance()));
    }
}
