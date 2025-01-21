package blizzard.development.mine.utils.packets;

import blizzard.development.core.Main;
import blizzard.development.mine.managers.mine.BlockManager;
import blizzard.development.mine.utils.PluginImpl;
import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.wrappers.BlockPosition;
import com.comphenix.protocol.wrappers.WrappedBlockData;
import com.google.common.primitives.Shorts;
import blizzard.development.mine.utils.apis.Cuboid;
import it.unimi.dsi.fastutil.ints.IntArrayList;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class MinePacketUtils {

    private static final MinePacketUtils instance = new MinePacketUtils();

    public static MinePacketUtils getInstance() {
        return instance;
    }

    private final ProtocolManager protocolManager = ProtocolLibrary.getProtocolManager();

    private static final int BATCH_SIZE = 100;
    private static final int SECTION_SIZE = 16;

    private short getPackedPosition(int x, int y, int z) {
        return (short) ((x & 15) << 8 | (z & 15) << 4 | y & 15);
    }

    public void sendMultiBlockPacket(Player player, Cuboid cuboid, Material material) {
        try {
            int chunkX = cuboid.getPoint1().getBlockX() >> 4;
            int chunkZ = cuboid.getPoint1().getBlockZ() >> 4;
            int minSectionY = cuboid.getPoint1().getBlockY() >> 4;
            int maxSectionY = cuboid.getPoint2().getBlockY() >> 4;

            processChunkSections(player, cuboid, material, chunkX, chunkZ, minSectionY, maxSectionY);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error when send packet: " + e.getMessage());
        }
    }

    private void processChunkSections(Player player, Cuboid cuboid, Material material, int chunkX, int chunkZ, int minSectionY, int maxSectionY) {
        WrappedBlockData blockData = WrappedBlockData.createData(material);

        for (int sectionY = minSectionY; sectionY <= maxSectionY; sectionY++) {
            List<Short> positions = new ArrayList<>();
            List<BlockPosition> blockPositions = new ArrayList<>();

            int minY = Math.max(cuboid.getPoint1().getBlockY(), sectionY << 4);
            int maxY = Math.min(cuboid.getPoint2().getBlockY(), (sectionY << 4) + 15);

            fillSectionPositions(positions, blockPositions, chunkX, sectionY, chunkZ, minY, maxY);

            if (!positions.isEmpty()) {
                sendSectionPacket(player, positions, blockData, chunkX, sectionY, chunkZ);
                registerBlocks(player, blockPositions);
            }

            if (sectionY < maxSectionY) {
                int finalSectionY = sectionY;
                Bukkit.getScheduler().runTaskLater(Main.getInstance(), () -> processChunkSections(player, cuboid, material, chunkX, chunkZ, finalSectionY + 1, maxSectionY), 1L);
                break;
            }
        }
    }

    private void fillSectionPositions(List<Short> positions, List<BlockPosition> blockPositions,
                                      int chunkX, int ignoredSectionY, int chunkZ, int minY, int maxY) {
        for (int x = 0; x < SECTION_SIZE; x++) {
            for (int y = minY; y <= maxY; y++) {
                for (int z = 0; z < SECTION_SIZE; z++) {
                    positions.add(getPackedPosition(x, y % SECTION_SIZE, z));

                    int absoluteX = (chunkX << 4) + x;
                    int absoluteZ = (chunkZ << 4) + z;
                    blockPositions.add(new BlockPosition(absoluteX, y, absoluteZ));
                }
            }
        }
    }

    private void sendSectionPacket(Player player, List<Short> positions, WrappedBlockData blockData,
                                   int chunkX, int sectionY, int chunkZ) {
        PacketContainer packet = protocolManager.createPacket(PacketType.Play.Server.MULTI_BLOCK_CHANGE);
        packet.getSectionPositions().write(0, new BlockPosition(chunkX, sectionY, chunkZ));
        packet.getShortArrays().write(0, Shorts.toArray(positions));

        WrappedBlockData[] blockDataArray = new WrappedBlockData[positions.size()];
        java.util.Arrays.fill(blockDataArray, blockData);
        packet.getBlockDataArrays().write(0, blockDataArray);

        protocolManager.sendServerPacket(player, packet);
    }

    private void registerBlocks(Player player, List<BlockPosition> positions) {
        for (int i = 0; i < positions.size(); i += BATCH_SIZE) {
            final int startIndex = i;
            final int endIndex = Math.min(startIndex + BATCH_SIZE, positions.size());

            Bukkit.getScheduler().runTaskLater(Main.getInstance(), () -> {
                for (int j = startIndex; j < endIndex; j++) {
                    BlockManager.getInstance().placeBlock(player, positions.get(j));
                }
            }, i / BATCH_SIZE);
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
            System.out.println("Error when send packet: " + e.getMessage());
        }
    }

    public void sendEntityPacket(Location location, Player player, EntityType entityType, int entityId) {
        ProtocolManager protocolManager = ProtocolLibrary.getProtocolManager();

        PacketContainer spawnPacket = protocolManager.createPacket(PacketType.Play.Server.SPAWN_ENTITY);
        spawnPacket.getIntegers()
            .write(0, entityId);
        spawnPacket.getUUIDs()
            .write(0, UUID.randomUUID());
        spawnPacket.getDoubles()
            .write(0, location.getX())
            .write(1, location.getY())
            .write(2, location.getZ());
        spawnPacket.getEntityTypeModifier()
            .write(0, entityType);

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
        ProtocolManager protocolManager = ProtocolLibrary.getProtocolManager();

        PacketContainer packetContainer = protocolManager.createPacket(PacketType.Play.Server.ENTITY_TELEPORT);

        packetContainer.getIntegers().write(0, entityId);
        packetContainer.getDoubles()
            .write(0, newLocation.getX()) // X
            .write(1, newLocation.getY()) // Y
            .write(2, newLocation.getZ()); // Z

        packetContainer.getBytes()
            .write(0, (byte) ((newLocation.getYaw() % 360) * 256 / 360))
            .write(1, (byte) ((newLocation.getPitch() % 360) * 256 / 360));


            protocolManager.sendServerPacket(player, packetContainer);
    }


    public void removeEntity(Player player, Integer entityId) {
        ProtocolManager protocolManager = ProtocolLibrary.getProtocolManager();
        final PacketContainer destroyEntity = new PacketContainer(PacketType.Play.Server.ENTITY_DESTROY);
        destroyEntity.getIntLists().write(0, new IntArrayList(new int[]{entityId}));
        try {
            protocolManager.sendServerPacket(player, destroyEntity);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}