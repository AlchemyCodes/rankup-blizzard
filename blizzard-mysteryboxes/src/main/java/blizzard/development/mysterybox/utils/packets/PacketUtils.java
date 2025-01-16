package blizzard.development.mysterybox.utils.packets;

public class PacketUtils {

//    private static final PacketUtils instance = new PacketUtils();
//    public static PacketUtils getInstance() {
//        return instance;
//    }
//
//    public void sendPacket(Player player, Block block, Material material) {
//        try {
//            PacketContainer packet = new PacketContainer(PacketType.Play.Server.BLOCK_CHANGE);
//
//            BlockData blockData = Bukkit.createBlockData(material);
//            WrappedBlockData wrappedBlockData = WrappedBlockData.createData(blockData);
//
//            BlockPosition blockPosition = new BlockPosition(
//                block.getX(),
//                block.getY(),
//                block.getZ()
//            );
//
//            packet.getBlockData().write(0, wrappedBlockData);
//            packet.getBlockPositionModifier().write(0, blockPosition);
//
//            BlockManager.placePlantation(player, blockPosition);
//
//            ProtocolLibrary.getProtocolManager().sendServerPacket(player, packet);
//        } catch (Exception e) {
//            e.printStackTrace();
//            System.out.println("Erro ao enviar pacote: " + e.getMessage());
//        }
//    }
}
