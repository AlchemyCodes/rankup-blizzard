package blizzard.development.core.listener.packets;

import blizzard.development.core.managers.CampfireManager;
import com.github.retrooper.packetevents.event.PacketListenerAbstract;
import com.github.retrooper.packetevents.event.PacketListenerPriority;
import com.github.retrooper.packetevents.event.PacketSendEvent;
import com.github.retrooper.packetevents.protocol.packettype.PacketType;
import com.github.retrooper.packetevents.wrapper.play.server.WrapperPlayServerBlockChange;
import io.papermc.paper.math.BlockPosition;
import org.bukkit.Location;
import org.bukkit.entity.Player;

public class PacketListener extends PacketListenerAbstract {

    public PacketListener(PacketListenerPriority priority) {
        super(priority);
    }

    @Override
    public void onPacketSend(PacketSendEvent event) {
        if (event.getPacketType() == PacketType.Play.Server.BLOCK_CHANGE) {
            WrapperPlayServerBlockChange packet = new WrapperPlayServerBlockChange(event);

            int x = packet.getBlockPosition().getX();
            int y = packet.getBlockPosition().getY();
            int z = packet.getBlockPosition().getZ();

            Player player = event.getPlayer();

            Location blockLocation = new Location(player.getWorld(), x, y, z);

            if (isCampfireBlock(blockLocation)) {
                player.sendMessage("interação cancelada");
                event.setCancelled(true);
            }
        }
    }

    private boolean isCampfireBlock(Location location) {
        return CampfireManager.isCampfireAtLocation(location);
    }
}
