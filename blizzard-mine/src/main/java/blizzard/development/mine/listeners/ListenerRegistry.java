package blizzard.development.mine.listeners;

import blizzard.development.mine.database.dao.BoosterDAO;
import blizzard.development.mine.database.dao.PlayerDAO;
import blizzard.development.mine.database.dao.ToolDAO;
import blizzard.development.mine.listeners.booster.BoosterListener;
import blizzard.development.mine.listeners.commons.PlayerTrafficListener;
import blizzard.development.mine.listeners.mine.*;
import blizzard.development.mine.listeners.npc.NPCInteractListener;
import blizzard.development.mine.listeners.npc.NPCRotationListener;
import blizzard.development.mine.listeners.packets.mine.MineBlockBreakPacketListener;
import blizzard.development.mine.listeners.packets.mine.MineBlockInteractPacketListener;
import blizzard.development.mine.listeners.packets.npc.NPCInteractPacketListener;
import blizzard.development.mine.utils.PluginImpl;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;
import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;

import java.util.Arrays;

public class ListenerRegistry {

    private final PlayerDAO playerDAO;
    private final ToolDAO toolDAO;
    private final BoosterDAO boosterDAO;

    public ListenerRegistry(PlayerDAO playerDAO, ToolDAO toolDAO, BoosterDAO boosterDAO) {
        this.playerDAO = playerDAO;
        this.toolDAO = toolDAO;
        this.boosterDAO = boosterDAO;
    }

    public void register() {
        PluginManager pluginManager = Bukkit.getPluginManager();

        Arrays.asList(
                // mine
                new PlayerTrafficListener(playerDAO, toolDAO),
                new MineBlockBreakListener(),
                new MineGeneralListener(),
                new MineChatListener(),
                new MineInventoryListener(),
                new MineInteractListener(),
                new MineExtractorListener(),
                new MineJumpPadListener(),
                // npc
                new NPCInteractListener(),
                new NPCRotationListener(),
                // booster
                new BoosterListener()
        ).forEach(listener -> pluginManager.registerEvents(listener, PluginImpl.getInstance().plugin));
    }

    public void registerPacket() {
        ProtocolManager protocolManager = ProtocolLibrary.getProtocolManager();

        Arrays.asList(
                new MineBlockBreakPacketListener(),
                new MineBlockInteractPacketListener(),
                new NPCInteractPacketListener()
        ).forEach(protocolManager::addPacketListener);
    }
}
