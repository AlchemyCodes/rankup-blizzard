package blizzard.development.monsters.monsters.handlers.packets.entity;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolManager;
import com.comphenix.protocol.events.PacketContainer;
import org.bukkit.Location;
import org.bukkit.entity.Player;

public class EntityRotation {
    private static EntityRotation instance;

    public void updateRotation(Player player, Location location, int entityId, ProtocolManager protocolManager) {
        PacketContainer rotateHead = protocolManager.createPacket(PacketType.Play.Server.ENTITY_HEAD_ROTATION);
        PacketContainer rotateBody = protocolManager.createPacket(PacketType.Play.Server.ENTITY_LOOK);

        Location entityLoc = location.clone();
        Location playerLoc = player.getLocation();

        double dx = playerLoc.getX() - entityLoc.getX();
        double dz = playerLoc.getZ() - entityLoc.getZ();

        double yaw = Math.atan2(dz, dx);
        yaw = yaw * (180 / Math.PI);
        yaw -= 90;

        while (yaw < 0) yaw += 360;
        while (yaw > 360) yaw -= 360;

        double dy = playerLoc.getY() + player.getEyeHeight() - (entityLoc.getY() + 1.6);
        double distance = Math.sqrt(dx * dx + dz * dz);
        double pitch = Math.atan2(dy, distance);
        pitch = pitch * (180 / Math.PI);

        rotateHead.getIntegers().write(0, entityId);
        rotateBody.getIntegers().write(0, entityId);

        byte yawByte = (byte) ((yaw % 360) * 256 / 360);
        byte pitchByte = (byte) ((pitch % 360) * 256 / 360);

        rotateHead.getBytes().write(0, yawByte);
        rotateBody.getBytes()
                .write(0, yawByte)
                .write(1, pitchByte);

        rotateBody.getBooleans().write(0, true);

        try {
            protocolManager.sendServerPacket(player, rotateBody);
            protocolManager.sendServerPacket(player, rotateHead);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static EntityRotation getInstance() {
        if (instance == null) instance = new EntityRotation();
        return instance;
    }
}
