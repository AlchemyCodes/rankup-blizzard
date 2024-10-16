package blizzard.development.crates.listeners;

import blizzard.development.crates.Main;
import blizzard.development.crates.listeners.crates.CrateInteractEvent;
import blizzard.development.crates.listeners.crates.CratePlaceEvent;
import blizzard.development.crates.listeners.keys.*;
import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;

import java.util.Arrays;

public class ListenerRegistry {

    public void register() {
        PluginManager pluginManager = Bukkit.getPluginManager();

        Arrays.asList(
                new CratePlaceEvent(),
                new CrateInteractEvent(),
                new CommonKeyInteractEvent(),
                new RareKeyInteractEvent(),
                new MysticKeyInteractEvent(),
                new LegendaryKeyInteractEvent(),
                new BlizzardKeyInteractEvent()
        ).forEach(listener -> pluginManager.registerEvents(listener, Main.getInstance()));
    }
}
