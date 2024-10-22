package blizzard.development.core.listener;

import blizzard.development.core.Main;
import blizzard.development.core.database.dao.PlayersDAO;
import blizzard.development.core.listener.campfire.CampfirePlace;
import blizzard.development.core.listener.clothing.ClothingActivatorInteractEvent;
import blizzard.development.core.listener.clothing.ClothingInventoryEvent;
import blizzard.development.core.listener.geral.TrafficListener;
import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;

import java.util.Arrays;

public class ListenerRegistry {

    private final PlayersDAO playersDAO;

    public ListenerRegistry(PlayersDAO playersDAO) {
        this.playersDAO = playersDAO;
    }

    public void register() {
        PluginManager pluginManager = Bukkit.getPluginManager();

        Arrays.asList(
                new TrafficListener(playersDAO),
                new ClothingActivatorInteractEvent(),
                new ClothingInventoryEvent(),
                new CampfirePlace()
        ).forEach(listener -> pluginManager.registerEvents(listener, Main.getInstance()));
    }
}
