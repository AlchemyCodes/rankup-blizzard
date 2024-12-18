package blizzard.development.plantations.listeners.packets;

import blizzard.development.plantations.Main;
import blizzard.development.plantations.database.cache.methods.PlayerCacheMethod;
import blizzard.development.plantations.managers.BlockManager;
import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketEvent;

import com.comphenix.protocol.wrappers.WrappedBlockData;
import org.bukkit.Material;
import org.bukkit.event.Listener;

public class PacketListener extends PacketAdapter implements Listener {


    public PacketListener() {
        super(PacketAdapter.params(
            Main.getInstance(),
            PacketType.Play.Server.BLOCK_CHANGE
        ).optionAsync());
    }

    public PlayerCacheMethod playerCacheMethod = PlayerCacheMethod.getInstance();

    @Override
    public void onPacketSending(PacketEvent event) {
        final var packet = event.getPacket();

        final var blockPosition = packet.getBlockPositionModifier().read(0);
        final var blockX = blockPosition.getX();
        final var blockY = blockPosition.getY();
        final var blockZ = blockPosition.getZ();


//        if (BlockManager.isPlantation(blockX, blockY, blockZ)) {
//            packet.getBlockData().write(0, WrappedBlockData.createData(Material.WHEAT));
//        }

    }

//    @Override
//    public void onPacketSending(PacketEvent event) {
//        PacketContainer packet = event.getPacket();
//        Player player = event.getPlayer();
//
//        if (player == null || !playerCacheMethod.isInPlantation(player)) {
//            return;
//        }
//
//        Optional<Object> packetMeta = packet.getMeta("farmland");
//        Optional<Object> packetCropsMeta = packet.getMeta("crops");
//
//        Block block = player.getTargetBlockExact(4);
//        if (block == null) return;
//
//        if (!packetMeta.isPresent()) {
//            packet.getBlockData().write(0, WrappedBlockData.createData(Material.FARMLAND));
//            packet.getBlockPositionModifier().write(0, new BlockPosition(
//                block.getLocation().getBlockX(),
//                block.getLocation().getBlockY(),
//                block.getLocation().getBlockZ()
//            ));
//
//        }
//
//        if (!packetCropsMeta.isPresent()) {
//            packet.getBlockData().write(0, WrappedBlockData.createData(Material.CARROTS));
//            packet.getBlockPositionModifier().write(0, new BlockPosition(
//                block.getLocation().getBlockX(),
//                block.getLocation().getBlockY() + 1,
//                block.getLocation().getBlockZ()
//            ));
//        }
//
//    }
}
