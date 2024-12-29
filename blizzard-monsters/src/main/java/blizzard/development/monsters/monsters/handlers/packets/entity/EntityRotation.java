package blizzard.development.monsters.monsters.handlers.packets.entity;

import blizzard.development.monsters.listeners.ListenerRegistry;
import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolManager;
import com.comphenix.protocol.events.PacketContainer;
import org.bukkit.Location;
import org.bukkit.entity.Player;

public class EntityRotation {
    private static EntityRotation instance;

    public void updateRotation(Player player, Location location, ProtocolManager protocolManager) {
        PacketContainer rotateHead = protocolManager.createPacket(PacketType.Play.Server.ENTITY_HEAD_ROTATION);
        PacketContainer rotateBody = protocolManager.createPacket(PacketType.Play.Server.ENTITY_LOOK);

        rotateHead.getIntegers()
                .write(0, -1);
        rotateBody.getIntegers()
                .write(0, -1);
        location.setDirection(player.getLocation().subtract(location).toVector());

        float yaw = location.getYaw();
        float pitch = location.getPitch();

        rotateHead.getBytes()
                .write(0, (byte) ((yaw % 360) * 256 / 360));
        rotateBody.getBytes()
                .write(0, (byte) ((yaw % 360) * 256 / 360))
                .write(1, (byte) ((pitch % 360) * 256 / 360));

        protocolManager.sendServerPacket(player, rotateHead);
        protocolManager.sendServerPacket(player, rotateBody);
    }

    public static EntityRotation getInstance() {
        if (instance == null) instance = new EntityRotation();
        return instance;
    }
}
