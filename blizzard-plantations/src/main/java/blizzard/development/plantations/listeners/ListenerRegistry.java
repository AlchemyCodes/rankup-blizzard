package blizzard.development.plantations.listeners;

import blizzard.development.plantations.Main;
import blizzard.development.plantations.database.dao.PlayerDAO;
import blizzard.development.plantations.listeners.geral.PlayerListener;
import blizzard.development.plantations.listeners.packets.BlockPlowListener;
import blizzard.development.plantations.listeners.packets.PacketListener;
import blizzard.development.plantations.listeners.plantation.PlantationBlockListener;
import blizzard.development.plantations.listeners.plantation.PlantationBreakListener;
import blizzard.development.plantations.listeners.plantation.PlantationPlaceListener;
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
//                new PlantationBreakListener(),
                new PlayerListener(playerDAO),
//                new PlantationBlockListener(),
                new PacketListener()
        ).forEach(listener -> pluginManager.registerEvents(listener, Main.getInstance()));
    }

    public void registerPacket() {
        ProtocolManager manager = ProtocolLibrary.getProtocolManager();

        Arrays.asList(
            new PacketListener(),
            new BlockPlowListener(),
            new blizzard.development.plantations.listeners.packets.plantation.PlantationPlaceListener()
        ).forEach(manager::addPacketListener);
    }
}
