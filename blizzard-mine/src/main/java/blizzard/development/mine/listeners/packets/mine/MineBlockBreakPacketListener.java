package blizzard.development.mine.listeners.packets.mine;

import blizzard.development.mine.listeners.mine.MineBlockBreakListener;
import blizzard.development.mine.managers.BlockManager;
import blizzard.development.mine.mine.events.MineBlockBreakEvent;
import blizzard.development.mine.utils.PluginImpl;
import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketEvent;
import com.comphenix.protocol.wrappers.BlockPosition;
import com.comphenix.protocol.wrappers.MovingObjectPositionBlock;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;

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
        }) {
            return;
        }

        final var player = event.getPlayer();
        final BlockPosition blockPosition = packet.getBlockPositionModifier().read(0);

        if (blockPosition == null) {
            return;
        }

        final var blockX = blockPosition.getX();
        final var blockY = blockPosition.getY();
        final var blockZ = blockPosition.getZ();

        event.setCancelled(true);

        Block block = player.getWorld().getBlockAt(blockX, blockY, blockZ);

        final var mineEvent = new MineBlockBreakEvent(player, block);
        mineEvent.callEvent();

        final var ackPacket = ProtocolLibrary.getProtocolManager().createPacket(PacketType.Play.Server.BLOCK_CHANGED_ACK);
        ackPacket.getIntegers().write(0, packet.getIntegers().read(0));
        ProtocolLibrary.getProtocolManager().sendServerPacket(event.getPlayer(), ackPacket);
    }
}
