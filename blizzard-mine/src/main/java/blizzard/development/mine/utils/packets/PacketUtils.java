package blizzard.development.mine.utils.packets;

import blizzard.development.mine.managers.BlockManager;
import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.wrappers.BlockPosition;
import com.comphenix.protocol.wrappers.WrappedBlockData;
import com.google.common.primitives.Shorts;
import blizzard.development.mine.utils.apis.Cuboid;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class PacketUtils {
    private static final PacketUtils instance = new PacketUtils();

    public static PacketUtils getInstance() {
        return instance;
    }

    private short getPackedPosition(int x, int y, int z) {
        return (short) ((x & 15) << 8 | (z & 15) << 4 | y & 15);
    }

    private final ProtocolManager protocolManager = ProtocolLibrary.getProtocolManager();

    public void sendMultiBlockPacket(Player player, Cuboid cuboid, Material material) {
        try {
            // ALINHAR COM O CHUNK DDIVIDINDO POR 4
            int chunkX = cuboid.getPoint1().getBlockX() >> 4;
            int chunkZ = cuboid.getPoint1().getBlockZ() >> 4;

            // LISTAS
            List<Short> positions = new ArrayList<>();
            List<WrappedBlockData> blockDataList = new ArrayList<>();
            List<BlockPosition> blockPositions = new ArrayList<>();

            // CALCULAR CHUNK VERTICALMENTE
            int minSectionY = cuboid.getPoint1().getBlockY() >> 4;
            int maxSectionY = cuboid.getPoint2().getBlockY() >> 4;

            // CALCULAR AS SEÇÕES Y MÍNIMA E MÁXIMA BASEADO NO CUBOID
            for (int sectionY = minSectionY; sectionY <= maxSectionY; sectionY++) {
                positions.clear();
                blockDataList.clear();
                blockPositions.clear();

                // CALCULAR O LIMITE DE Y
                int minY = Math.max(cuboid.getPoint1().getBlockY(), sectionY << 4); // COMEÇO
                int maxY = Math.min(cuboid.getPoint2().getBlockY(), (sectionY << 4) + 15); // FINAL

                // LOOP SOBRE AS COORDENADAS DENTRO DO CHUNK
                for (int x = 0; x < 16; x++) { // De 0 a 15 (16 blocos)
                    for (int y = minY; y <= maxY; y++) {
                        for (int z = 0; z < 16; z++) { // De 0 a 15 (16 blocos)
                            // ADICIONAR A POSIÇÃO COMPACTADA DO BLOCO À LISTA
                            positions.add(getPackedPosition(x, y % 16, z));
                            blockDataList.add(WrappedBlockData.createData(material)); // esse é o codigo do multi? ss

                            // CALCULAR POSIÇÃO DO BLOCO
                            int absoluteX = (chunkX << 4) + x;
                            int absoluteZ = (chunkZ << 4) + z;
                            BlockPosition blockPos = new BlockPosition(absoluteX, y, absoluteZ);
                            blockPositions.add(blockPos);
                        }
                    }
                }

                if (!positions.isEmpty()) {
                    PacketContainer packet = protocolManager
                            .createPacket(PacketType.Play.Server.MULTI_BLOCK_CHANGE);

                    // DEFINIR A POSIÇÃO DA SEÇÃO DO CHUNK NO PACKET
                    packet.getSectionPositions()
                            .write(0, new BlockPosition(chunkX, sectionY, chunkZ));

                    // DEFINIR AS POSIÇÕES DOS BLOCOS NO PACKET
                    packet.getShortArrays()
                            .write(0, Shorts.toArray(positions));

                    // DEFINIR OS DADOS DOS BLOCOS NO PACKET
                    packet.getBlockDataArrays()
                            .write(0, blockDataList.toArray(new WrappedBlockData[0]));

                    // REGISTRAR OS BLOCOS

                    for (BlockPosition pos : blockPositions) {
                        BlockManager.getInstance().placeBlock(player, pos);
                    }

                    // EENVIAR PACKET
                    protocolManager.sendServerPacket(player, packet);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error when send packet: " + e.getMessage());
        }
    }

    public void sendAirBlock(Player player, org.bukkit.block.Block block) {
        try {
            PacketContainer packet = protocolManager
                    .createPacket(PacketType.Play.Server.BLOCK_CHANGE);

            BlockPosition blockPosition = new BlockPosition(
                    block.getX(),
                    block.getY(),
                    block.getZ()
            );

            WrappedBlockData airBlockData = WrappedBlockData.createData(Material.AIR);

            packet.getBlockPositionModifier().write(0, blockPosition);
            packet.getBlockData().write(0, airBlockData);

            protocolManager.sendServerPacket(player, packet);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error when send packet: " + e.getMessage());
        }
    }
}