package blizzard.development.plantations.listeners.packets;

import blizzard.development.plantations.Main;
import blizzard.development.plantations.database.cache.methods.PlayerCacheMethod;
import blizzard.development.plantations.managers.BlockManager;
import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketEvent;

import com.comphenix.protocol.wrappers.WrappedBlockData;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.data.Ageable;
import org.bukkit.block.data.BlockData;
import org.bukkit.entity.Player;
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
        Player player = event.getPlayer();

        if (packet.getMeta("manual_update").isPresent()) {
            return;
        }

        final var blockPosition = packet.getBlockPositionModifier().read(0);
        final var blockX = blockPosition.getX();
        final var blockY = blockPosition.getY();
        final var blockZ = blockPosition.getZ();

        if (BlockManager.isPlantation(blockX, blockY, blockZ)) {
            BlockData blockData = Bukkit.createBlockData(Material.getMaterial(playerCacheMethod.getAreaPlantation(player)));
            if (blockData instanceof Ageable) {
                Ageable ageable = (Ageable) blockData;
                ageable.setAge(ageable.getMaximumAge());
                packet.getBlockData().write(0, WrappedBlockData.createData(ageable));
            }
        }
    }
}
