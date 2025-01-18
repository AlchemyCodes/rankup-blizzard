package blizzard.development.mine.listeners.packets.mine;

import blizzard.development.mine.managers.mine.BlockManager;
import blizzard.development.mine.mine.events.mine.MineBlockBreakEvent;
import blizzard.development.mine.utils.PluginImpl;
import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.events.PacketEvent;
import com.comphenix.protocol.wrappers.BlockPosition;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

public class MineBlockBreakPacketListener extends PacketAdapter{

    public MineBlockBreakPacketListener() {
        super(PacketAdapter.params(
            PluginImpl.getInstance().plugin,
            PacketType.Play.Client.BLOCK_DIG
        ).optionAsync());
    }

    @Override
    public void onPacketReceiving(PacketEvent event) {
        final var packet = event.getPacket();
        final var action = packet.getPlayerDigTypes().readSafely(0);

        if (action == null || switch (action) {
            case START_DESTROY_BLOCK, STOP_DESTROY_BLOCK, ABORT_DESTROY_BLOCK -> false;
            default -> true;
        }) return;

        Player player = event.getPlayer();

        BlockPosition blockPosition = packet.getBlockPositionModifier().read(0);

        if (blockPosition == null) return;

        int blockX = blockPosition.getX();
        int blockY = blockPosition.getY();
        int blockZ = blockPosition.getZ();

        if (!BlockManager.getInstance().isBlock(blockX, blockY, blockZ)) return;

        event.setCancelled(true);

        Block block = player.getWorld().getBlockAt(blockX, blockY, blockZ);

        MineBlockBreakEvent mineEvent = new MineBlockBreakEvent(player, block);
        mineEvent.callEvent();

        ProtocolManager protocolManager = ProtocolLibrary.getProtocolManager();
        PacketContainer ackPacket = protocolManager.createPacket(PacketType.Play.Server.BLOCK_CHANGED_ACK);

        ackPacket.getIntegers().write(0, packet.getIntegers().read(0));
        protocolManager.sendServerPacket(event.getPlayer(), ackPacket);
    }
}
