package blizzard.development.mine.utils.packets;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolManager;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.wrappers.*;
import org.bukkit.Location;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import com.destroystokyo.paper.profile.PlayerProfile;
import com.destroystokyo.paper.profile.ProfileProperty;
import org.bukkit.util.Vector;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

public class NPCPacketUtils {
    private static final NPCPacketUtils instance = new NPCPacketUtils();

    public static NPCPacketUtils getInstance() {
        return instance;
    }

    public void spawnNPC(Player player, Location location, UUID uuid, Integer id, ProtocolManager protocolManager) {
        PacketContainer npc = protocolManager.createPacket(PacketType.Play.Server.SPAWN_ENTITY);

        npc.getIntegers()
                .write(0, id);

        npc.getUUIDs()
                .write(0, uuid);

        npc.getEntityTypeModifier()
                .writeSafely(0, EntityType.PLAYER);

        npc.getDoubles()
                .write(0, location.getX())
                .write(1, location.getY())
                .write(2, location.getZ());

        byte yaw = (byte) ((int) (location.getYaw() * 256.0F / 360.0F));
        byte pitch = (byte) ((int) (location.getPitch() * 256.0F / 360.0F));

        npc.getBytes()
                .write(0, yaw)
                .write(1, pitch);

        protocolManager.sendServerPacket(player, npc);
    }

    public void NPCInfoUpdate(Player player, UUID uuid, ProtocolManager protocolManager) {
        PacketContainer npc = protocolManager.createPacket(PacketType.Play.Server.PLAYER_INFO);
        Set<EnumWrappers.PlayerInfoAction> playerInfoActionSet = new HashSet<>();
        WrappedGameProfile wrappedGameProfile = new WrappedGameProfile(uuid, null);
        PlayerProfile profile = player.getPlayerProfile();
        ProfileProperty textures = profile.getProperties().stream()
                .filter(property -> property.getName().equals("textures"))
                .findFirst()
                .orElseThrow();

        WrappedSignedProperty property = new WrappedSignedProperty(
                "textures", textures.getValue(), textures.getSignature());

        wrappedGameProfile.getProperties().clear();
        wrappedGameProfile.getProperties()
                .put("textures", property);

        PlayerInfoData playerInfoData = new PlayerInfoData(
                wrappedGameProfile,
                0,
                EnumWrappers.NativeGameMode.CREATIVE,
                WrappedChatComponent.fromText("name")
        );

        List<PlayerInfoData> playerInfoDataList = List.of(playerInfoData);
        playerInfoActionSet.add(EnumWrappers.PlayerInfoAction.ADD_PLAYER);

        npc.getPlayerInfoActions()
                .write(0, playerInfoActionSet);
        npc.getPlayerInfoDataLists().write(1, playerInfoDataList);

        protocolManager.sendServerPacket(player, npc);
    }

    public void NPCRotationUpdate(Player player, Location npcLocation, int entityId, ProtocolManager protocolManager) {
        PacketContainer rotateHead = protocolManager.createPacket(PacketType.Play.Server.ENTITY_HEAD_ROTATION);
        PacketContainer rotateBody = protocolManager.createPacket(PacketType.Play.Server.ENTITY_LOOK);

        rotateHead.getIntegers().write(0, entityId);
        rotateBody.getIntegers().write(0, entityId);

        Vector direction = player.getLocation().toVector().subtract(npcLocation.toVector());
        direction.setY(0);
        direction = direction.normalize();

        double dx = direction.getX();
        double dz = direction.getZ();

        double yaw = Math.toDegrees(Math.atan2(-dx, dz));
        yaw = (yaw < 0) ? yaw + 360 : yaw;

        byte yawByte = (byte) ((yaw * 256.0F) / 360.0F);
        byte pitchByte = 0;

        rotateHead.getBytes().write(0, yawByte);
        rotateBody.getBytes()
                .write(0, yawByte)
                .write(1, pitchByte);

        protocolManager.sendServerPacket(player, rotateHead);
        protocolManager.sendServerPacket(player, rotateBody);
    }
}