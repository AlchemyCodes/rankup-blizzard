package blizzard.development.bosses.utils.packets;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;
import com.comphenix.protocol.events.PacketContainer;
import org.bukkit.Location;
import org.bukkit.entity.Player;

public class MoveEntityMethod {

    public static void moveEntity(Player player, Location location, int entityId) {
        ProtocolManager protocolManager = ProtocolLibrary.getProtocolManager();
        PacketContainer teleportPacket = protocolManager.createPacket(PacketType.Play.Server.ENTITY_TELEPORT);
        teleportPacket.getIntegers().write(0, entityId);
        teleportPacket.getDoubles()
                .write(0, location.getX())
                .write(1, location.getY())
                .write(2, location.getZ());

        try {
            protocolManager.sendServerPacket(player, teleportPacket);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
