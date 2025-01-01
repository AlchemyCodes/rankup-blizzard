package blizzard.development.monsters.monsters.handlers.packets.entity;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolManager;
import com.comphenix.protocol.events.PacketContainer;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

public class EntityRotation {
    private static EntityRotation instance;

    public void updateRotation(Player player, Location entityLocation, int entityId, ProtocolManager protocolManager) {
        PacketContainer rotateHead = protocolManager.createPacket(PacketType.Play.Server.ENTITY_HEAD_ROTATION);
        PacketContainer rotateBody = protocolManager.createPacket(PacketType.Play.Server.ENTITY_LOOK);

        rotateHead.getIntegers().write(0, entityId);
        rotateBody.getIntegers().write(0, entityId);

        Vector direction = player.getEyeLocation().toVector().subtract(entityLocation.toVector());

        double dx = direction.getX();
        double dy = direction.getY();
        double dz = direction.getZ();

        double yaw = Math.atan2(dz, dx);
        yaw = yaw * (180 / Math.PI);
        yaw = yaw - 90;

        double pitch = Math.atan2(dy, Math.sqrt(dx * dx + dz * dz));
        pitch = -pitch * (180 / Math.PI);

        byte yawByte = (byte) ((yaw % 360) * 256 / 360);
        byte pitchByte = (byte) ((pitch % 360) * 256 / 360);

        rotateHead.getBytes().write(0, yawByte);
        rotateBody.getBytes()
                .write(0, yawByte)
                .write(1, pitchByte);

        protocolManager.sendServerPacket(player, rotateHead);
        protocolManager.sendServerPacket(player, rotateBody);
    }

    public static EntityRotation getInstance() {
        if (instance == null) instance = new EntityRotation();
        return instance;
    }
}
