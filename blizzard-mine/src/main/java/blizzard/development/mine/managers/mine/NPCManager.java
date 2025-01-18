package blizzard.development.mine.managers.mine;

import blizzard.development.mine.utils.packets.NPCPacketUtils;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class NPCManager {
    private static final NPCManager instance = new NPCManager();

    public static NPCManager getInstance() {
        return instance;
    }

    private final Map<Player, Integer> npcId = new HashMap<>();
    private final Map<Player, UUID> npcUUID = new HashMap<>();

    private final ProtocolManager protocolManager = ProtocolLibrary.getProtocolManager();

    public UUID spawnNPC(Player player, Location location) {
        UUID uuid = UUID.randomUUID();
        Integer id = (int) Math.round(Math.random() * Integer.MAX_VALUE);

        NPCPacketUtils packetUtils = NPCPacketUtils.getInstance();

        packetUtils.NPCInfoUpdate(player, uuid, protocolManager);

        packetUtils.spawnNPC(player, location, uuid, id, protocolManager);

        packetUtils.NPCRotationUpdate(player, location, id, protocolManager);

        putNPCId(player, id);
        putNPCUUID(player, uuid);

        return uuid;
    }


    public void putNPCId(Player player, Integer id) {
        npcId.remove(player);
        npcId.put(player, id);
    }

    public Integer getNPCId(Player player) {
        return npcId.getOrDefault(player, null);
    }

    public void putNPCUUID(Player player, UUID uuid) {
        npcUUID.remove(player);
        npcUUID.put(player, uuid);
    }

    public UUID getNPCUUID(Player player) {
        return npcUUID.getOrDefault(player, null);
    }
}
