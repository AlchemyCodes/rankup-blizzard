package blizzard.development.mine.listeners;

import blizzard.development.mine.database.dao.PlayerDAO;
import blizzard.development.mine.listeners.geral.PlayerListener;
import blizzard.development.mine.listeners.mine.MineBlockBreakListener;
import blizzard.development.mine.listeners.packets.mine.MineBlockBreakPacketListener;
import blizzard.development.mine.listeners.packets.mine.MineBlockInteractPacketListener;
import blizzard.development.mine.utils.PluginImpl;
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
            new MineBlockBreakListener()
        ).forEach(listener -> pluginManager.registerEvents(listener, PluginImpl.getInstance().plugin));
    }

    public void registerPacket() {
        ProtocolManager manager = ProtocolLibrary.getProtocolManager();

        Arrays.asList(
            new MineBlockBreakPacketListener(),
                new MineBlockInteractPacketListener()
        ).forEach(manager::addPacketListener);
    }
}
