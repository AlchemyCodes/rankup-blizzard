package blizzard.development.bosses.utils.packets.npcs;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolManager;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.wrappers.WrappedDataValue;
import com.comphenix.protocol.wrappers.WrappedDataWatcher;
import com.google.common.collect.Lists;
import org.bukkit.Location;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.UUID;

public class NPCSpawn {
    private ProtocolManager protocolManager;
    private UUID uuid;
    private final int entityId;

    public NPCSpawn(
            ProtocolManager protocolManager,
            UUID uuid, int entityId) {
        this.protocolManager = protocolManager;
        this.uuid = uuid;
        this.entityId = entityId;
    }

    public void spawnEntity(Player player, Location location) {
        PacketContainer npc = protocolManager.createPacket(PacketType.Play.Server.SPAWN_ENTITY);

        npc.getIntegers()
                .write(0, entityId)
                .writeSafely(1, 122);
        npc.getUUIDs()
                .write(0, uuid);
        npc.getEntityTypeModifier()
                .writeSafely(0, EntityType.PLAYER);
        npc.getDoubles()
                .write(0, location.getX())
                .write(1, location.getY())
                .write(2, location.getZ());
        npc.getBytes()
                .write(0, (byte) (0))
                .write(1, (byte) (0));
        protocolManager.sendServerPacket(player, npc);
    }

    public void moveEntity(Player player, Location location) {
        PacketContainer teleportPacket = protocolManager.createPacket(PacketType.Play.Server.ENTITY_TELEPORT);
        teleportPacket.getIntegers().write(0, entityId);
        teleportPacket.getDoubles().write(0, location.getX());
        teleportPacket.getDoubles().write(1, location.getY());
        teleportPacket.getDoubles().write(2, location.getZ());
        teleportPacket.getBytes().write(0, (byte) (location.getYaw() * 256 / 360));
        teleportPacket.getBytes().write(1, (byte) (location.getPitch() * 256 / 360));
        try {
            protocolManager.sendServerPacket(player, teleportPacket);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void lookEntity(Player player, float yaw, float pitch) {
        PacketContainer entityLookPacket = protocolManager.createPacket(PacketType.Play.Server.ENTITY_LOOK);
        entityLookPacket.getIntegers().write(0, entityId);
        entityLookPacket.getBytes().write(0, (byte) (yaw * 256 / 360));
        entityLookPacket.getBytes().write(1, (byte) (pitch * 256 / 360));
        entityLookPacket.getBooleans().write(0, true);

        PacketContainer headRotationPacket = protocolManager.createPacket(PacketType.Play.Server.ENTITY_HEAD_ROTATION);
        headRotationPacket.getIntegers().write(0, entityId);
        headRotationPacket.getBytes().write(0, (byte) (yaw * 256 / 360));

        try {
            protocolManager.sendServerPacket(player, entityLookPacket);
            protocolManager.sendServerPacket(player, headRotationPacket);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void activateSecondLayer(Player player) {
        PacketContainer metaPacket = protocolManager.createPacket(PacketType.Play.Server.ENTITY_METADATA);
        metaPacket.getIntegers().write(0, entityId);

        byte value = 0x7F;
        WrappedDataWatcher.Serializer byteSerializer = WrappedDataWatcher.Registry.get(Byte.class);
        WrappedDataWatcher.WrappedDataWatcherObject secondLayer = new WrappedDataWatcher.WrappedDataWatcherObject(17, byteSerializer);
        List<WrappedDataValue> values = Lists.newArrayList(
                new WrappedDataValue(17, secondLayer.getSerializer(), value)
        );
        metaPacket.getDataValueCollectionModifier().write(0, values);

        try {
            protocolManager.sendServerPacket(player, metaPacket);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}