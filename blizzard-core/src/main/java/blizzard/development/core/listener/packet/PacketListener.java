package blizzard.development.core.listener.packet;

import blizzard.development.core.Main;
import blizzard.development.core.managers.CampfireManager;
import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketEvent;
import com.comphenix.protocol.wrappers.WrappedBlockData;
import org.bukkit.Material;
import org.bukkit.entity.Player;

public class PacketListener extends PacketAdapter {

    public PacketListener() {
        super(PacketAdapter.params(
                Main.getInstance(),
                PacketType.Play.Server.BLOCK_CHANGE,
                PacketType.Play.Client.BLOCK_DIG
        ).optionAsync());
    }

    @Override
    public void onPacketSending(PacketEvent event) {
        final var packet = event.getPacket();

        final var blockPosition = packet.getBlockPositionModifier().read(0);
        final var blockX = blockPosition.getX();
        final var blockY = blockPosition.getY();
        final var blockZ = blockPosition.getZ();

        if (!CampfireManager.isPacketCampfire(blockX, blockY, blockZ)) {
           return;
        }

        final var material = Material.CAMPFIRE;
        packet.getBlockData().write(0, WrappedBlockData.createData(material));
    }




    @Override
    public void onPacketReceiving(PacketEvent event) {
        final var packet = event.getPacket();
        final var action = packet.getPlayerDigTypes().read(0);
        switch (action) {
            case START_DESTROY_BLOCK, STOP_DESTROY_BLOCK, ABORT_DESTROY_BLOCK -> {}
            default -> {
                return;
            }
        }

        Player player = event.getPlayer();

        final var blockPosition = packet.getBlockPositionModifier().read(0);
        final var blockX = blockPosition.getX();
        final var blockY = blockPosition.getY();
        final var blockZ = blockPosition.getZ();

        if(!CampfireManager.isPacketCampfire(blockX, blockY, blockZ)) {
            return;
        }

        CampfireManager.removeCampfire(player);

//        event.setCancelled(true);
//        player.sendMessage("evento cancelado");
//
//        final var ackPacket = ProtocolLibrary.getProtocolManager().createPacket(PacketType.Play.Server.BLOCK_CHANGED_ACK);
//        ackPacket.getIntegers().write(0, packet.getIntegers().read(0));
//        ProtocolLibrary.getProtocolManager().sendServerPacket(event.getPlayer(), ackPacket);
    }
}
