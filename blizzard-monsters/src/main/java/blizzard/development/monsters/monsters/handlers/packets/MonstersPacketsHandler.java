package blizzard.development.monsters.monsters.handlers.packets;

import blizzard.development.monsters.monsters.handlers.packets.entity.EntitySpawn;
import blizzard.development.monsters.monsters.handlers.packets.entity.EntityUpdate;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.UUID;

public class MonstersPacketsHandler {
    private static MonstersPacketsHandler instance;

    private final ProtocolManager protocolManager = ProtocolLibrary.getProtocolManager();
    private final UUID uuid = UUID.randomUUID();

    public void spawnMonster(Player player, Location location) {
        EntityUpdate updateInfo = EntityUpdate.getInstance();
        EntitySpawn spawnEntity = EntitySpawn.getInstance();

        updateInfo.playerInfoUpdate(player, uuid, protocolManager);
        spawnEntity.spawnEntity(player, location, uuid, protocolManager);
    }

    public static MonstersPacketsHandler getInstance() {
        if (instance == null) instance = new MonstersPacketsHandler();
        return instance;
    }
}