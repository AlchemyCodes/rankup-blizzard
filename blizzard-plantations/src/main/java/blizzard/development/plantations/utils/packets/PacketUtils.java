package blizzard.development.plantations.utils.packets;

import blizzard.development.plantations.managers.BlockManager;
import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.wrappers.BlockPosition;
import com.comphenix.protocol.wrappers.WrappedBlockData;
import it.unimi.dsi.fastutil.ints.IntArrayList;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.data.BlockData;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;

import java.util.UUID;

public class PacketUtils {

    private static final PacketUtils instance = new PacketUtils();

    public void sendPacket(Player player, Block block) {
        try {
            Block blockAbove = block.getRelative(0, 1, 0);
            if (blockAbove.getType() != Material.AIR) {
                return;
            }

            PacketContainer packet = new PacketContainer(PacketType.Play.Server.BLOCK_CHANGE);

            BlockData blockData = Bukkit.createBlockData("minecraft:potatoes");
            WrappedBlockData wrappedBlockData = WrappedBlockData.createData(blockData);

            BlockPosition blockPosition = new BlockPosition(
                block.getX(),
                block.getY() + 1,
                block.getZ()
            );

            packet.getBlockData().write(0, wrappedBlockData);
            packet.getBlockPositionModifier().write(0, blockPosition);

            BlockManager.placePlantation(player, blockPosition);

            ProtocolLibrary.getProtocolManager().sendServerPacket(player, packet);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Erro ao enviar pacote: " + e.getMessage());
        }
    }

    public void sendPacket(Player player, Block block, Material material) {
        try {
            Block blockAbove = block.getRelative(0, 1, 0);
            if (blockAbove.getType() != Material.AIR) {
                return;
            }

            PacketContainer packet = new PacketContainer(PacketType.Play.Server.BLOCK_CHANGE);

            BlockData blockData = Bukkit.createBlockData(material);
            WrappedBlockData wrappedBlockData = WrappedBlockData.createData(blockData);

            BlockPosition blockPosition = new BlockPosition(
                block.getX(),
                block.getY() + 1,
                block.getZ()
            );

            packet.getBlockData().write(0, wrappedBlockData);
            packet.getBlockPositionModifier().write(0, blockPosition);

            BlockManager.placePlantation(player, blockPosition);

            ProtocolLibrary.getProtocolManager().sendServerPacket(player, packet);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Erro ao enviar pacote: " + e.getMessage());
        }
    }

    public void sendEntityPacket(Location location, Player player) {
        ProtocolManager protocolManager = ProtocolLibrary.getProtocolManager();
        PacketContainer packetContainer = protocolManager.createPacket(PacketType.Play.Server.SPAWN_ENTITY);

        packetContainer.getIntegers()
            .write(0, (int) Math.round(Math.random() * Integer.MAX_VALUE));
        packetContainer.getUUIDs()
            .write(0, UUID.randomUUID());
        packetContainer.getDoubles()
            .write(0, location.getX())
            .write(1, location.getY())
            .write(2, location.getZ());

        packetContainer.getEntityTypeModifier().write(0, EntityType.PRIMED_TNT);

        PacketContainer lookPacket = protocolManager.createPacket(PacketType.Play.Server.ENTITY_LOOK);
        lookPacket.getIntegers().write(0, 1);
        lookPacket.getBytes()
            .write(0, (byte) ((location.getYaw() % 360) * 256 / 360))
            .write(1, (byte) ((location.getPitch() % 360) * 256 / 360));
        lookPacket.getBooleans().write(0, true);

        PacketContainer headRotationPacket = protocolManager.createPacket(PacketType.Play.Server.ENTITY_HEAD_ROTATION);
        headRotationPacket.getIntegers().write(0, 1);
        headRotationPacket.getBytes().write(0, (byte) ((location.getYaw() % 360) * 256 / 360));

        protocolManager.sendServerPacket(player, packetContainer);
        protocolManager.sendServerPacket(player, lookPacket);
        protocolManager.sendServerPacket(player, headRotationPacket);
    }

    public void sendEntityPacket(Location location, Player player, EntityType entityType) {
        ProtocolManager protocolManager = ProtocolLibrary.getProtocolManager();
        PacketContainer packetContainer = protocolManager.createPacket(PacketType.Play.Server.SPAWN_ENTITY);

        packetContainer.getIntegers()
            .write(0, (int) Math.round(Math.random() * Integer.MAX_VALUE));
        packetContainer.getUUIDs()
            .write(0, UUID.randomUUID());
        packetContainer.getDoubles()
            .write(0, location.getX())
            .write(1, location.getY())
            .write(2, location.getZ());

        packetContainer.getEntityTypeModifier().write(0, entityType);

        PacketContainer lookPacket = protocolManager.createPacket(PacketType.Play.Server.ENTITY_LOOK);
        lookPacket.getIntegers().write(0, 1);
        lookPacket.getBytes()
            .write(0, (byte) ((location.getYaw() % 360) * 256 / 360))
            .write(1, (byte) ((location.getPitch() % 360) * 256 / 360));
        lookPacket.getBooleans().write(0, true);

        PacketContainer headRotationPacket = protocolManager.createPacket(PacketType.Play.Server.ENTITY_HEAD_ROTATION);
        headRotationPacket.getIntegers().write(0, 1);
        headRotationPacket.getBytes().write(0, (byte) ((location.getYaw() % 360) * 256 / 360));

        protocolManager.sendServerPacket(player, packetContainer);
        protocolManager.sendServerPacket(player, lookPacket);
        protocolManager.sendServerPacket(player, headRotationPacket);
    }

    public void sendWeatherPacket(Player player) {
        PacketContainer rainPacket = ProtocolLibrary.getProtocolManager().createPacket(PacketType.Play.Server.GAME_STATE_CHANGE);

        rainPacket.getIntegers().write(0, 0);
        rainPacket.getFloat().write(0, 1F);

        ProtocolLibrary.getProtocolManager().sendServerPacket(player, rainPacket);
    }

    public static void removeEntity(Player player, Integer entityId) {
        ProtocolManager protocolManager = ProtocolLibrary.getProtocolManager();
        final PacketContainer destroyEntity = new PacketContainer(PacketType.Play.Server.ENTITY_DESTROY);
        destroyEntity.getIntLists().write(0, new IntArrayList(new int[]{entityId}));
        try {
            protocolManager.sendServerPacket(player, destroyEntity);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static PacketUtils getInstance() {
        return instance;
    }
}
