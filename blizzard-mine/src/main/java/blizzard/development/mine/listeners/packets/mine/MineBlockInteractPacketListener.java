package blizzard.development.mine.listeners.packets.mine;

import blizzard.development.mine.database.cache.methods.PlayerCacheMethod;
import blizzard.development.mine.managers.BlockManager;
import blizzard.development.mine.utils.PluginImpl;
import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketEvent;
import com.comphenix.protocol.wrappers.BlockPosition;
import com.comphenix.protocol.wrappers.MovingObjectPositionBlock;
import com.comphenix.protocol.wrappers.WrappedBlockData;
import org.bukkit.Material;
import org.jetbrains.annotations.NotNull;

import java.util.EnumMap;

public class MineBlockInteractPacketListener extends PacketAdapter {

    private final EnumMap<Material, WrappedBlockData> WRAPPED_BLOCK_DATA_CACHE = new EnumMap<>(Material.class);

    public MineBlockInteractPacketListener() {
        super(PacketAdapter.params(
                PluginImpl.getInstance().plugin,
                PacketType.Play.Client.USE_ITEM
        ).optionAsync());
    }

    @Override
    public void onPacketReceiving(PacketEvent event) {
        final var player = event.getPlayer();
        final var packet = event.getPacket();

        MovingObjectPositionBlock movePos = packet.getMovingBlockPositions().read(0);
        if (movePos == null) {
            return;
        }

        BlockPosition blockPosition = movePos.getBlockPosition();
        if (blockPosition == null) {
            return;
        }

        final var blockX = blockPosition.getX();
        final var blockY = blockPosition.getY();
        final var blockZ = blockPosition.getZ();

        if (!BlockManager.isBlock(blockX, blockY, blockZ)) {
            return;
        }

        event.setCancelled(true);

        Material material = Material.getMaterial(PlayerCacheMethod.getInstance().getAreaBlock(player));
        if (material != null && packet.getBlockData().size() > 0) {
            packet.getBlockData().write(0, getBlockData(material));
        }
    }

    public WrappedBlockData getBlockData(@NotNull Material material) {
        return WRAPPED_BLOCK_DATA_CACHE.computeIfAbsent(material, WrappedBlockData::createData);
    }
}