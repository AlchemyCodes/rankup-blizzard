package blizzard.development.plantations.listeners.packets;

import blizzard.development.plantations.Main;
import blizzard.development.plantations.database.cache.methods.PlayerCacheMethod;
import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.events.PacketEvent;
import com.comphenix.protocol.wrappers.BlockPosition;
import com.comphenix.protocol.wrappers.WrappedBlockData;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class BlockPlowListener extends PacketAdapter {

    public BlockPlowListener() {
        super(PacketAdapter.params(
            Main.getInstance(),
            PacketType.Play.Client.USE_ITEM
        ).optionAsync());
    }

    private final PlayerCacheMethod playerCacheMethod = new PlayerCacheMethod();

    @Override
    public void onPacketReceiving(PacketEvent event) {
        Player player = event.getPlayer();
        ItemStack mainHandItem = player.getInventory().getItemInMainHand();

        if (playerCacheMethod.isInPlantation(player)) {
            if (mainHandItem.getType() == Material.SLIME_BALL) {
                validatePacket(player);
                event.setCancelled(true);
            }
        }
    }

    private void validatePacket(Player player) {
        Block block = player.getTargetBlockExact(4);

        if (block != null) {
            sendPacket(player, block);
            player.sendMessage("lasanha a bolonhesa == deu certo");
        } else {
            player.sendMessage("bloco nao esta no alcance");
        }
    }

    private void sendPacket(Player player, Block block) {
        try {
            PacketContainer packet = new PacketContainer(PacketType.Play.Server.BLOCK_CHANGE);

            packet.getBlockData().write(0, WrappedBlockData.createData(Material.FARMLAND));
            packet.getBlockPositionModifier().write(0, new BlockPosition(
                block.getLocation().getBlockX(),
                block.getLocation().getBlockY(),
                block.getLocation().getBlockZ()
            ));

            packet.setMeta("farmland", true);

            ProtocolLibrary.getProtocolManager().sendServerPacket(player, packet);
        } catch (Exception e) {
            e.printStackTrace();
            player.sendMessage("Falhou sendpéquet");
        }
    }
}