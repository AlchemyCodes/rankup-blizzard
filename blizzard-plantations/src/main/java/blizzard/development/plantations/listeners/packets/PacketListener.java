package blizzard.development.plantations.listeners.packets;

import blizzard.development.plantations.Main;
import com.github.retrooper.packetevents.PacketEvents;
import com.github.retrooper.packetevents.event.*;
import com.github.retrooper.packetevents.protocol.packettype.PacketType;
import com.github.retrooper.packetevents.protocol.world.states.WrappedBlockState;
import com.github.retrooper.packetevents.util.Vector3i;
import com.github.retrooper.packetevents.wrapper.play.client.WrapperPlayClientPlayerBlockPlacement;
import com.github.retrooper.packetevents.wrapper.play.server.WrapperPlayServerBlockChange;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.data.Ageable;
import org.bukkit.block.data.BlockData;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class PacketListener extends PacketListenerAbstract implements com.github.retrooper.packetevents.event.PacketListener {

    public PacketListener(PacketListenerPriority priority) {
        super(priority);
    }

    @Override
    public void onPacketReceive(PacketReceiveEvent event) {
        if (event.getPacketType() == PacketType.Play.Client.PLAYER_BLOCK_PLACEMENT) {
            WrapperPlayClientPlayerBlockPlacement packet = new WrapperPlayClientPlayerBlockPlacement(event);

            Main.getInstance().getServer().getScheduler().runTask(Main.getInstance(), () -> {
                Player player = event.getPlayer();

                ItemStack item = player.getInventory().getItemInMainHand();

                if (item.getType() != Material.COAL) {
                    return;
                }

                BlockData blockData = Material.WHEAT.createBlockData();

                if (blockData instanceof Ageable) {
                    ((Ageable) blockData).setAge(0);
                } else {
                    return;
                }

                Vector3i blockPosition = new Vector3i(
                    packet.getBlockPosition().getX(),
                    packet.getBlockPosition().getY() + 1,
                    packet.getBlockPosition().getZ()
                );

                WrappedBlockState wheatState = WrappedBlockState.getByString("minecraft:wheat[age=0]");

                WrapperPlayServerBlockChange blockChangePacket = new WrapperPlayServerBlockChange(blockPosition, wheatState.getGlobalId());
                PacketEvents.getAPI().getPlayerManager().sendPacket(player, blockChangePacket);

                growth(player, blockPosition);
            });
        }
    }

    private void growth(Player player, Vector3i position) {

        for (int age = 1; age <= 7; age++) {
            final int currentAge = age;


            Main.getInstance().getServer().getScheduler().runTaskLater(Main.getInstance(), () -> {
                if (player.isOnline()) {
                    WrappedBlockState wheatState = WrappedBlockState.getByString("minecraft:wheat[age=" + currentAge + "]");

                    WrapperPlayServerBlockChange blockChangePacket = new WrapperPlayServerBlockChange(position, wheatState.getGlobalId());
                    PacketEvents.getAPI().getPlayerManager().sendPacket(player, blockChangePacket);
                }
            }, age * 20L);
        }
    }
}
