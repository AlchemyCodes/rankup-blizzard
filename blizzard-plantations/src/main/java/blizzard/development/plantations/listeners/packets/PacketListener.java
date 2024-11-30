package blizzard.development.plantations.listeners.packets;

import blizzard.development.plantations.Main;
import blizzard.development.plantations.api.CuboidAPI;
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
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;

import java.util.Iterator;
import java.util.Optional;


public class PacketListener extends PacketAdapter implements Listener {


    public PacketListener() {
        super(PacketAdapter.params(
            Main.getInstance(),
            PacketType.Play.Server.BLOCK_CHANGE
        ).optionAsync());
    }

    public PlayerCacheMethod playerCacheMethod = new PlayerCacheMethod();

    @Override
    public void onPacketSending(PacketEvent event) {
        PacketContainer packet = event.getPacket();

        Optional<Object> packetMeta = packet.getMeta("farmland");
        Optional<Object> packetCropsMeta = packet.getMeta("crops");

        if (playerCacheMethod.isInPlantation(event.getPlayer()) && packet.getType() == PacketType.Play.Server.BLOCK_CHANGE) {
            if (packetMeta.isPresent()) {
                packet.getBlockData().write(0, WrappedBlockData.createData(Material.FARMLAND));
            }

            if (packetCropsMeta.isPresent()) {
                packet.getBlockData().write(0, WrappedBlockData.createData(Material.WHEAT));
            }
        }

    }


    @EventHandler
    public void onInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        Block block = event.getClickedBlock();
        Action action = event.getAction();
        EquipmentSlot equipmentSlot = event.getHand();

        if (block == null) return;

        if (action == Action.RIGHT_CLICK_BLOCK) {
            if (equipmentSlot == EquipmentSlot.HAND) {
                if (block.getType() == Material.CRYING_OBSIDIAN) {
                    sendPacket(player, block);
                }
            }
        }

    }

    @EventHandler
    public void onChat(AsyncPlayerChatEvent event) {
        Player player = event.getPlayer();
        String message = event.getMessage();


        if (message.equalsIgnoreCase("swagviper")) {

            CuboidAPI cuboidAPI = new CuboidAPI(
                player.getLocation().add(10, 10, 10),
                player.getLocation().add(-10, 0, -10)
            );

            Iterator<Block> block = cuboidAPI.blockList();

            while (block.hasNext()) {
                sendPacket(player, block.next());
            }

        }
    }

    private void sendPacket(Player player, Block block) {
        PacketContainer packet = new PacketContainer(PacketType.Play.Server.BLOCK_CHANGE);
        packet.getBlockData().write(0, WrappedBlockData.createData(Material.DIAMOND_BLOCK));
        packet.getBlockPositionModifier().write(0, new BlockPosition(
            block.getLocation().getBlockX(),
            block.getLocation().getBlockY(),
            block.getLocation().getBlockZ()
        ));

        packet.setMeta("farmland", true);

        ProtocolLibrary.getProtocolManager().sendServerPacket(player, packet);
    }
}
