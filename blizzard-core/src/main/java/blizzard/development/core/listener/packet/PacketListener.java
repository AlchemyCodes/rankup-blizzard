package blizzard.development.core.listener.packet;

import blizzard.development.core.Main;
import blizzard.development.core.managers.GeneratorManager;
import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketEvent;
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

        Player player = event.getPlayer();

        if (!GeneratorManager.isPacketGenerator(blockX, blockY, blockZ)) {
            return;
        }


        if (!GeneratorManager.isGeneratorReady(player)) {
            return;
        }

        event.setCancelled(true);
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

        final var blockPosition = packet.getBlockPositionModifier().read(0);
        final var blockX = blockPosition.getX();
        final var blockY = blockPosition.getY();
        final var blockZ = blockPosition.getZ();

        if(!GeneratorManager.isPacketGenerator(blockX, blockY, blockZ)) {
            return;
        }

        event.setCancelled(true);

        final var ackPacket = ProtocolLibrary.getProtocolManager().createPacket(PacketType.Play.Server.BLOCK_CHANGED_ACK);
        ackPacket.getIntegers().write(0, packet.getIntegers().read(0));
        ProtocolLibrary.getProtocolManager().sendServerPacket(event.getPlayer(), ackPacket);
    }
}
