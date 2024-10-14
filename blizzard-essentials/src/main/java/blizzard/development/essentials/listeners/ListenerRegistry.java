package blizzard.development.essentials.listeners;

import blizzard.development.essentials.Main;
import blizzard.development.essentials.listeners.player.PlayerEvents;
import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;

import java.util.Arrays;

public class ListenerRegistry {

    public void register() {
        PluginManager manager = Bukkit.getPluginManager();
        Arrays.asList(
                new PlayerEvents()
        ).forEach(listener -> manager.registerEvents(listener, Main.getInstance()));
    }

}
