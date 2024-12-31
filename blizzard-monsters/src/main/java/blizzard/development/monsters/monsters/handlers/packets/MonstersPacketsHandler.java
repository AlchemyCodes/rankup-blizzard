package blizzard.development.monsters.monsters.handlers.packets;

import blizzard.development.monsters.builders.hologram.HologramBuilder;
import blizzard.development.monsters.database.cache.managers.MonstersCacheManager;
import blizzard.development.monsters.database.storage.MonstersData;
import blizzard.development.monsters.monsters.handlers.monsters.MonstersHandler;
import blizzard.development.monsters.monsters.handlers.packets.entity.EntitySpawn;
import blizzard.development.monsters.monsters.handlers.packets.entity.EntityUpdate;
import blizzard.development.monsters.monsters.handlers.world.MonstersWorldHandler;
import blizzard.development.monsters.utils.LocationUtils;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.UUID;

public class MonstersPacketsHandler {
    private static MonstersPacketsHandler instance;

    private final ProtocolManager protocolManager = ProtocolLibrary.getProtocolManager();

    public void spawnMonster(Player player, String type, Location location, String displayName, Integer life, String value, String signature) {
        UUID uuid = UUID.randomUUID();

        EntityUpdate updateInfo = EntityUpdate.getInstance();
        EntitySpawn spawnEntity = EntitySpawn.getInstance();

        updateInfo.playerInfoUpdate(player, uuid, value, signature, protocolManager);
        spawnEntity.spawnEntity(player, location, uuid, protocolManager);
        HologramBuilder.getInstance().createHologram(player, uuid, location.add(0, 2.5 ,0), displayName, life);

        String serializedLocation = LocationUtils.getInstance().getSerializedLocation(location);

        MonstersHandler.getInstance().createData(
                player,
                uuid.toString(),
                type,
                serializedLocation,
                life
        );
    }

//    public void removeMonster(Player player, UUID uuid) {
//        EntityUpdate updateInfo = EntityUpdate.getInstance();
//        EntitySpawn spawnEntity = EntitySpawn.getInstance();
//
//        updateInfo.removePlayerInfo(player, uuid, protocolManager);
//
//        spawnEntity.destroyEntity(player, uuid, protocolManager);
//
//        HologramBuilder.getInstance().removeHologram(uuid);
//    }

    public void removeAllMonsters() {
        for (Player player : Bukkit.getOnlinePlayers()) {
            if (MonstersWorldHandler.getInstance().containsPlayer(player)) {
                for (MonstersData monstersData : MonstersCacheManager.getInstance().monstersCache.values()) {
                    HologramBuilder.getInstance().removeHologram(UUID.fromString(monstersData.getId()));
                }
            }
        }
    }

    public static MonstersPacketsHandler getInstance() {
        if (instance == null) instance = new MonstersPacketsHandler();
        return instance;
    }
}