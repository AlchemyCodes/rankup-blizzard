package blizzard.development.mine.utils.packets;

import blizzard.development.currencies.utils.PluginImpl;
import blizzard.development.mine.managers.mine.BlockManager;
import blizzard.development.mine.utils.apis.Cuboid;
import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.wrappers.BlockPosition;
import com.comphenix.protocol.wrappers.WrappedBlockData;
import com.google.common.primitives.Shorts;
import it.unimi.dsi.fastutil.ints.IntArrayList;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.*;

public class MinePacketUtils {
    private static final MinePacketUtils instance = new MinePacketUtils();
    private final ProtocolManager protocolManager = ProtocolLibrary.getProtocolManager();
    private static final int SECTION_SIZE = 16;

    public static MinePacketUtils getInstance() {
        return instance;
    }

    public void sendRegionPacket(Player player, BlockManager.MineRegion region) {
        Cuboid cuboid = region.region();
        Material material = region.material();

        int minChunkX = cuboid.getPoint1().getBlockX() >> 4;
        int maxChunkX = cuboid.getPoint2().getBlockX() >> 4;
        int minChunkZ = cuboid.getPoint1().getBlockZ() >> 4;
        int maxChunkZ = cuboid.getPoint2().getBlockZ() >> 4;

        for (int chunkX = minChunkX; chunkX <= maxChunkX; chunkX++) {
            for (int chunkZ = minChunkZ; chunkZ <= maxChunkZ; chunkZ++) {
                sendChunkRegionPacket(player, cuboid, material, chunkX, chunkZ);
            }
        }
    }

    private void sendChunkRegionPacket(Player player, Cuboid cuboid, Material material, int chunkX, int chunkZ) {
        int minSectionY = cuboid.getPoint1().getBlockY() >> 4;
        int maxSectionY = cuboid.getPoint2().getBlockY() >> 4;

        for (int sectionY = minSectionY; sectionY <= maxSectionY; sectionY++) {
            List<Short> positions = new ArrayList<>();

            int minY = Math.max(cuboid.getPoint1().getBlockY(), sectionY << 4);
            int maxY = Math.min(cuboid.getPoint2().getBlockY(), (sectionY << 4) + 15);

            for (int x = 0; x < SECTION_SIZE; x++) {
                int worldX = (chunkX << 4) + x;
                if (worldX < cuboid.getPoint1().getBlockX() || worldX > cuboid.getPoint2().getBlockX())
                    continue;

                for (int z = 0; z < SECTION_SIZE; z++) {
                    int worldZ = (chunkZ << 4) + z;
                    if (worldZ < cuboid.getPoint1().getBlockZ() || worldZ > cuboid.getPoint2().getBlockZ())
                        continue;

                    for (int y = minY; y <= maxY; y++) {
                        positions.add(getPackedPosition(x, y & 15, z));
                    }
                }
            }

            if (!positions.isEmpty()) {
                sendSectionPacket(player, positions, WrappedBlockData.createData(material), chunkX, sectionY, chunkZ);
            }
        }
    }

    private void sendSectionPacket(Player player, List<Short> positions, WrappedBlockData blockData, int chunkX, int sectionY, int chunkZ) {
        PacketContainer packet = protocolManager.createPacket(PacketType.Play.Server.MULTI_BLOCK_CHANGE);
        packet.getSectionPositions().write(0, new BlockPosition(chunkX, sectionY, chunkZ));
        packet.getShortArrays().write(0, Shorts.toArray(positions));

        WrappedBlockData[] blockDataArray = new WrappedBlockData[positions.size()];
        Arrays.fill(blockDataArray, blockData);
        packet.getBlockDataArrays().write(0, blockDataArray);

        protocolManager.sendServerPacket(player, packet);
    }

    public void sendMultiBlockChange(Player player, Map<BlockPosition, WrappedBlockData> blockChanges) {
        try {
            PacketContainer packet = protocolManager.createPacket(PacketType.Play.Server.MULTI_BLOCK_CHANGE);

            BlockPosition firstPos = blockChanges.keySet().iterator().next();
            int chunkX = firstPos.getX() >> 4;
            int chunkZ = firstPos.getZ() >> 4;
            int sectionY = firstPos.getY() >> 4;

            packet.getSectionPositions().write(0, new BlockPosition(chunkX, sectionY, chunkZ));

            List<Short> positions = new ArrayList<>();
            List<WrappedBlockData> blockDataList = new ArrayList<>();

            for (Map.Entry<BlockPosition, WrappedBlockData> entry : blockChanges.entrySet()) {
                BlockPosition pos = entry.getKey();
                positions.add(getPackedPosition(pos.getX() & 15, pos.getY() & 15, pos.getZ() & 15));
                blockDataList.add(entry.getValue());
            }

            packet.getShortArrays().write(0, Shorts.toArray(positions));
            packet.getBlockDataArrays().write(0, blockDataList.toArray(new WrappedBlockData[0]));

            protocolManager.sendServerPacket(player, packet);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void sendAirBlock(Player player, Block block) {
        try {
            PacketContainer packet = protocolManager.createPacket(PacketType.Play.Server.BLOCK_CHANGE);
            BlockPosition blockPosition = new BlockPosition(block.getX(), block.getY(), block.getZ());

            packet.getBlockPositionModifier().write(0, blockPosition);
            packet.getBlockData().write(0, WrappedBlockData.createData(Material.AIR));

            protocolManager.sendServerPacket(player, packet);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void sendEntityPacket(Location location, Player player, EntityType entityType, int entityId) {
        PacketContainer spawnPacket = protocolManager.createPacket(PacketType.Play.Server.SPAWN_ENTITY);
        spawnPacket.getIntegers().write(0, entityId);
        spawnPacket.getUUIDs().write(0, UUID.randomUUID());
        spawnPacket.getDoubles()
                .write(0, location.getX())
                .write(1, location.getY())
                .write(2, location.getZ());
        spawnPacket.getEntityTypeModifier().write(0, entityType);

        protocolManager.sendServerPacket(player, spawnPacket);

        new BukkitRunnable() {
            double velocityY = -0.5;
            double currentY = location.getY();

            @Override
            public void run() {
                currentY += velocityY;
                velocityY -= 0.08;

                if (currentY <= location.getY() - 20) {
                    this.cancel();
                    return;
                }

                PacketContainer movePacket = protocolManager.createPacket(PacketType.Play.Server.ENTITY_TELEPORT);
                movePacket.getIntegers().write(0, entityId);
                movePacket.getDoubles()
                        .write(0, location.getX())
                        .write(1, currentY)
                        .write(2, location.getZ());

                PacketContainer velocityPacket = protocolManager.createPacket(PacketType.Play.Server.ENTITY_VELOCITY);
                velocityPacket.getIntegers().write(0, entityId);
                velocityPacket.getIntegers()
                        .write(1, 0)
                        .write(2, (int) (velocityY * 8000))
                        .write(3, 0);

                protocolManager.sendServerPacket(player, movePacket);
                protocolManager.sendServerPacket(player, velocityPacket);
            }
        }.runTaskTimer(PluginImpl.getInstance().plugin, 0L, 1L);
    }

    public void updateEntityPosition(Player player, int entityId, Location newLocation) {
        PacketContainer packetContainer = protocolManager.createPacket(PacketType.Play.Server.ENTITY_TELEPORT);

        packetContainer.getIntegers().write(0, entityId);
        packetContainer.getDoubles()
                .write(0, newLocation.getX())
                .write(1, newLocation.getY())
                .write(2, newLocation.getZ());

        packetContainer.getBytes()
                .write(0, (byte) ((newLocation.getYaw() % 360) * 256 / 360))
                .write(1, (byte) ((newLocation.getPitch() % 360) * 256 / 360));

        protocolManager.sendServerPacket(player, packetContainer);
    }

    public void removeEntity(Player player, Integer entityId) {
        PacketContainer destroyEntity = new PacketContainer(PacketType.Play.Server.ENTITY_DESTROY);
        destroyEntity.getIntLists().write(0, new IntArrayList(new int[]{entityId}));
        try {
            protocolManager.sendServerPacket(player, destroyEntity);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private short getPackedPosition(int x, int y, int z) {
        return (short) ((x & 15) << 8 | (z & 15) << 4 | y & 15);
    }
}