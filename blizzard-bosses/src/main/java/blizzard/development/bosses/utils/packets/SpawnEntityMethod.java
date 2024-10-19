package blizzard.development.bosses.utils.packets;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.wrappers.WrappedChatComponent;
import com.comphenix.protocol.wrappers.WrappedDataValue;
import com.comphenix.protocol.wrappers.WrappedDataWatcher;
import com.google.common.collect.Lists;
import org.bukkit.Location;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class SpawnEntityMethod {

    public static void spawnNormalEntity(Player player, Location location, Integer entityId, EntityType entityType) {
        ProtocolManager protocolManager = ProtocolLibrary.getProtocolManager();
        PacketContainer packetContainer = protocolManager.createPacket(PacketType.Play.Server.SPAWN_ENTITY);

        packetContainer.getIntegers()
                .write(0, entityId); // R: (int) Math.round(Math.random() * Integer.MAX_VALUE)
        packetContainer.getUUIDs()
                .write(0, UUID.randomUUID());
        packetContainer.getDoubles()
                .write(0, location.getX())
                .write(1, location.getY())
                .write(2, location.getZ());

        packetContainer.getEntityTypeModifier().write(0, entityType);

        PacketContainer lookPacket = protocolManager.createPacket(PacketType.Play.Server.ENTITY_LOOK);
        lookPacket.getIntegers().write(0, entityId);
        lookPacket.getBytes()
                .write(0, (byte) ((location.getYaw() % 360) * 256 / 360))
                .write(1, (byte) ((location.getPitch() % 360) * 256 / 360));
        lookPacket.getBooleans().write(0, true);

        PacketContainer headRotationPacket = protocolManager.createPacket(PacketType.Play.Server.ENTITY_HEAD_ROTATION);
        headRotationPacket.getIntegers().write(0, entityId);
        headRotationPacket.getBytes().write(0, (byte) ((location.getYaw() % 360) * 256 / 360));

        protocolManager.sendServerPacket(player, packetContainer);
        protocolManager.sendServerPacket(player, lookPacket);
        protocolManager.sendServerPacket(player, headRotationPacket);
    }

    public static void spawnEntityWithCustomName(Player player, Location location, Integer entityId, EntityType entityType, String displayName) {
        ProtocolManager protocolManager = ProtocolLibrary.getProtocolManager();
        PacketContainer packetContainer = protocolManager.createPacket(PacketType.Play.Server.SPAWN_ENTITY);

        packetContainer.getIntegers()
                .write(0, entityId); // R: (int) Math.round(Math.random() * Integer.MAX_VALUE)
        packetContainer.getUUIDs()
                .write(0, UUID.randomUUID());
        packetContainer.getDoubles()
                .write(0, location.getX())
                .write(1, location.getY())
                .write(2, location.getZ());

        packetContainer.getEntityTypeModifier().write(0, entityType);

        PacketContainer metaPacket = protocolManager.createPacket(PacketType.Play.Server.ENTITY_METADATA);
        metaPacket.getIntegers().write(0, entityId);
        WrappedDataWatcher.Serializer booleanType = WrappedDataWatcher.Registry.get(Boolean.class);

        Optional<?> opt = Optional
                .of(WrappedChatComponent
                        .fromChatMessage(displayName)[0].getHandle());
        List<WrappedDataValue> values = Lists.newArrayList(
                new WrappedDataValue(2, WrappedDataWatcher.Registry.getChatComponentSerializer(true), opt)
        );
        metaPacket.getDataValueCollectionModifier().write(0, values);

        PacketContainer lookPacket = protocolManager.createPacket(PacketType.Play.Server.ENTITY_LOOK);
        lookPacket.getIntegers().write(0, entityId);
        lookPacket.getBytes()
                .write(0, (byte) ((location.getYaw() % 360) * 256 / 360))
                .write(1, (byte) ((location.getPitch() % 360) * 256 / 360));
        lookPacket.getBooleans().write(0, true);

        PacketContainer headRotationPacket = protocolManager.createPacket(PacketType.Play.Server.ENTITY_HEAD_ROTATION);
        headRotationPacket.getIntegers().write(0, entityId);
        headRotationPacket.getBytes().write(0, (byte) ((location.getYaw() % 360) * 256 / 360));

        protocolManager.sendServerPacket(player, packetContainer);
        protocolManager.sendServerPacket(player, lookPacket);
        protocolManager.sendServerPacket(player, metaPacket);
        protocolManager.sendServerPacket(player, headRotationPacket);
    }

    public static void spawnEntityGlowing(Player player, Location location, Integer entityId, EntityType entityType) {
        ProtocolManager protocolManager = ProtocolLibrary.getProtocolManager();
        PacketContainer packetContainer = protocolManager.createPacket(PacketType.Play.Server.SPAWN_ENTITY);

        packetContainer.getIntegers()
                .write(0, entityId); // R: (int) Math.round(Math.random() * Integer.MAX_VALUE)
        packetContainer.getUUIDs()
                .write(0, UUID.randomUUID());
        packetContainer.getDoubles()
                .write(0, location.getX())
                .write(1, location.getY())
                .write(2, location.getZ());

        packetContainer.getEntityTypeModifier().write(0, entityType);

        PacketContainer metaPacket = protocolManager.createPacket(PacketType.Play.Server.ENTITY_METADATA);
        metaPacket.getIntegers().write(0, entityId);

        int index = 0;
        byte value = 0x40;
        WrappedDataWatcher.Serializer byteSerializer = WrappedDataWatcher.Registry.get(Byte.class);
        WrappedDataWatcher.WrappedDataWatcherObject glowEffect = new WrappedDataWatcher.WrappedDataWatcherObject(index, byteSerializer);

        List<WrappedDataValue> values = Lists.newArrayList(
                new WrappedDataValue(0, glowEffect.getSerializer(), value)
        );
        metaPacket.getDataValueCollectionModifier().write(0, values);

        PacketContainer lookPacket = protocolManager.createPacket(PacketType.Play.Server.ENTITY_LOOK);
        lookPacket.getIntegers().write(0, entityId);
        lookPacket.getBytes()
                .write(0, (byte) ((location.getYaw() % 360) * 256 / 360))
                .write(1, (byte) ((location.getPitch() % 360) * 256 / 360));
        lookPacket.getBooleans().write(0, true);

        PacketContainer headRotationPacket = protocolManager.createPacket(PacketType.Play.Server.ENTITY_HEAD_ROTATION);
        headRotationPacket.getIntegers().write(0, entityId);
        headRotationPacket.getBytes().write(0, (byte) ((location.getYaw() % 360) * 256 / 360));

        protocolManager.sendServerPacket(player, packetContainer);
        protocolManager.sendServerPacket(player, lookPacket);
        protocolManager.sendServerPacket(player, headRotationPacket);
        protocolManager.sendServerPacket(player, metaPacket);
    }

    public static void spawnEntityGlowingWithCustomName(Player player, Location location, Integer entityId, EntityType entityType, String displayName) {
        ProtocolManager protocolManager = ProtocolLibrary.getProtocolManager();
        PacketContainer packetContainer = protocolManager.createPacket(PacketType.Play.Server.SPAWN_ENTITY);

        packetContainer.getIntegers()
                .write(0, entityId); // R: (int) Math.round(Math.random() * Integer.MAX_VALUE)
        packetContainer.getUUIDs()
                .write(0, UUID.randomUUID());
        packetContainer.getDoubles()
                .write(0, location.getX())
                .write(1, location.getY())
                .write(2, location.getZ());

        packetContainer.getEntityTypeModifier().write(0, entityType);

        PacketContainer metaPacket = protocolManager.createPacket(PacketType.Play.Server.ENTITY_METADATA);
        metaPacket.getIntegers().write(0, entityId);

        int index = 0;
        byte value = 0x40;
        WrappedDataWatcher.Serializer byteSerializer = WrappedDataWatcher.Registry.get(Byte.class);
        WrappedDataWatcher.WrappedDataWatcherObject glowEffect = new WrappedDataWatcher.WrappedDataWatcherObject(index, byteSerializer);

        Optional<?> opt = Optional
                .of(WrappedChatComponent
                        .fromChatMessage(displayName)[0].getHandle());
        List<WrappedDataValue> values = Lists.newArrayList(
                new WrappedDataValue(0, glowEffect.getSerializer(), value),
                new WrappedDataValue(2, WrappedDataWatcher.Registry.getChatComponentSerializer(true), opt)
        );
        metaPacket.getDataValueCollectionModifier().write(0, values);

        PacketContainer lookPacket = protocolManager.createPacket(PacketType.Play.Server.ENTITY_LOOK);
        lookPacket.getIntegers().write(0, entityId);
        lookPacket.getBytes()
                .write(0, (byte) ((location.getYaw() % 360) * 256 / 360))
                .write(1, (byte) ((location.getPitch() % 360) * 256 / 360));
        lookPacket.getBooleans().write(0, true);

        PacketContainer headRotationPacket = protocolManager.createPacket(PacketType.Play.Server.ENTITY_HEAD_ROTATION);
        headRotationPacket.getIntegers().write(0, entityId);
        headRotationPacket.getBytes().write(0, (byte) ((location.getYaw() % 360) * 256 / 360));

        protocolManager.sendServerPacket(player, packetContainer);
        protocolManager.sendServerPacket(player, lookPacket);
        protocolManager.sendServerPacket(player, headRotationPacket);
        protocolManager.sendServerPacket(player, metaPacket);
    }




}
