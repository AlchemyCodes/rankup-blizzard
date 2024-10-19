package blizzard.development.bosses.utils.packets.npcs;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolManager;
import com.comphenix.protocol.events.PacketContainer;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.UUID;

public class NPC {
    private ProtocolManager protocolManager;
    private UUID uuid;
    public int entityId;
    private Location location;

    public NPC(ProtocolManager protocolManager, UUID uuid) {
        this.protocolManager = protocolManager;
        this.uuid = uuid;
        this.entityId = (int) Math.round(Math.random() * Integer.MAX_VALUE);
    }

    public void spawn(Player player, String npcName, String textureValue, String textureSignature, Location location) {
        NPCInfo updateInfo = new NPCInfo(protocolManager, uuid);
        NPCSpawn spawnEntity = new NPCSpawn(protocolManager, uuid, entityId);
        updateInfo.playerInfoUpdate(player, npcName, textureValue, textureSignature);
        spawnEntity.spawnEntity(player, location);
        this.location = location;
    }

    public void move(Player player, Location targetLocation) {
        NPCSpawn spawnEntity = new NPCSpawn(protocolManager, uuid, entityId);
        spawnEntity.moveEntity(player, targetLocation);
        this.location = targetLocation;
    }

    public void look(Player player, float yaw, float pitch) {
        NPCSpawn npcUtils = new NPCSpawn(protocolManager, uuid, entityId);
        npcUtils.lookEntity(player, yaw, pitch);
    }

    public void activateSecondLayer(Player player) {
        NPCSpawn npcUtils = new NPCSpawn(protocolManager, uuid, entityId);
        npcUtils.activateSecondLayer(player);
    }

    public void remove(Player player) {
        PacketContainer destroyPacket = protocolManager.createPacket(PacketType.Play.Server.ENTITY_DESTROY);
        destroyPacket.getIntegerArrays().write(0, new int[]{entityId});
        protocolManager.sendServerPacket(player, destroyPacket);
    }

    public Location getLocation() {
        return this.location;
    }
}
