package blizzard.development.plantations.listeners;

import blizzard.development.plantations.Main;
import blizzard.development.plantations.database.dao.PlayerDAO;
import blizzard.development.plantations.listeners.geral.PlayerListener;
import blizzard.development.plantations.listeners.packets.PacketListener;
import blizzard.development.plantations.listeners.packets.plantation.PlantationBreakListener;
import blizzard.development.plantations.listeners.plantation.PlantationDisplayListener;
import blizzard.development.plantations.listeners.plantation.PlantationListener;
import blizzard.development.plantations.listeners.plantation.ToolInteractListener;
import blizzard.development.plantations.listeners.visuals.VisualApplyListener;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;
import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;

import java.util.Arrays;

public class ListenerRegistry {

    private final PlayerDAO playerDAO;

    public ListenerRegistry(PlayerDAO playerDAO) {
        this.playerDAO = playerDAO;
    }

    public void register() {
        PluginManager pluginManager = Bukkit.getPluginManager();

        Arrays.asList(
                new PlayerListener(playerDAO),
                new PlantationDisplayListener(),
                new ToolInteractListener(),
                new PlantationListener(),
                new VisualApplyListener()
        ).forEach(listener -> pluginManager.registerEvents(listener, Main.getInstance()));
    }

    public void registerPacket() {
        ProtocolManager manager = ProtocolLibrary.getProtocolManager();

        Arrays.asList(
            new PlantationBreakListener(),
            new PacketListener()
        ).forEach(manager::addPacketListener);
    }
}
