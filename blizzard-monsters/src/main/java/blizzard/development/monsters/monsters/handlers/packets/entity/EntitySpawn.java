package blizzard.development.monsters.monsters.handlers.packets.entity;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolManager;
import com.comphenix.protocol.events.PacketContainer;
import org.bukkit.Location;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;

import java.util.Collections;
import java.util.UUID;

public class EntitySpawn {
    private static EntitySpawn instance;

    public void spawnEntity(Player player, Location location, UUID uuid, Integer id, ProtocolManager protocolManager) {
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

        npc.getBytes()
                .write(0, (byte) (0))
                .write(1, (byte) (0));

        protocolManager.sendServerPacket(player, npc);
    }

    public void destroyEntity(Player player, UUID uuid, ProtocolManager protocolManager) {
        PacketContainer packet = protocolManager.createPacket(PacketType.Play.Server.ENTITY_DESTROY);
        packet.getIntLists().write(0, Collections.singletonList((int) Math.round(Math.random() * Integer.MAX_VALUE)));

        protocolManager.sendServerPacket(player, packet);
    }

    public static EntitySpawn getInstance() {
        if (instance == null) instance = new EntitySpawn();
        return instance;
    }
}
