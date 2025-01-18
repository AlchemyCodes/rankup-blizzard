package blizzard.development.mine.utils.packets;

import blizzard.development.core.Main;
import blizzard.development.mine.managers.mine.BlockManager;
import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.wrappers.BlockPosition;
import com.comphenix.protocol.wrappers.WrappedBlockData;
import com.google.common.primitives.Shorts;
import blizzard.development.mine.utils.apis.Cuboid;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

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
}