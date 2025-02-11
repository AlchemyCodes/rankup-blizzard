package blizzard.development.core.listener;

import blizzard.development.core.Main;
import blizzard.development.core.database.dao.PlayersDAO;
import blizzard.development.core.listener.clothing.ClothingActivatorInteractEvent;
import blizzard.development.core.listener.clothing.ClothingInventoryEvent;

import java.util.Arrays;

import blizzard.development.core.listener.generator.GeneratorInteractEvent;
import com.comphenix.protocol.ProtocolManager;
import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;

public class ListenerRegistry {
    private final PlayersDAO playersDAO;
    private final ProtocolManager protocolManager;

    public ListenerRegistry(PlayersDAO playersDAO, ProtocolManager protocolManager) {
        this.playersDAO = playersDAO;
        this.protocolManager = protocolManager;
    }

    public void register() {
        PluginManager pluginManager = Bukkit.getPluginManager();

        Arrays.asList(
                new TrafficListener(this.playersDAO),
                new ClothingActivatorInteractEvent(),
                new ClothingInventoryEvent(),
                new GeneratorInteractEvent()
        ).forEach(listener -> pluginManager.registerEvents(listener, Main.getInstance()));
    }
}
