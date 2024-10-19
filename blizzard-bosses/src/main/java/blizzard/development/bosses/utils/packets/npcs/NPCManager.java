package blizzard.development.bosses.utils.packets.npcs;

import com.comphenix.protocol.ProtocolManager;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class NPCManager {
    public static Map<UUID, NPC> npcMap = new HashMap<>();
    private ProtocolManager protocolManager;

    public NPCManager(ProtocolManager protocolManager) {
        this.protocolManager = protocolManager;
    }

    public NPC createNPC(String npcName, String textureValue, String textureSignature, Player player, Location location) {
        UUID uuid = UUID.randomUUID();
        NPC npc = new NPC(protocolManager, uuid);
        npc.spawn(player, npcName, textureValue, textureSignature, location);
        npcMap.put(uuid, npc);
        return npc;
    }

    public void removeNPC(UUID uuid, Player player) {
        NPC npc = npcMap.get(uuid);
        if (npc != null) {
            npc.remove(player);
            npcMap.remove(uuid);
        }
    }

    public NPC getNPC(UUID uuid) {
        return npcMap.get(uuid);
    }
}


