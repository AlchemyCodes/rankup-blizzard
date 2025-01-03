package blizzard.development.monsters.monsters.handlers.packets;

import blizzard.development.monsters.builders.hologram.HologramBuilder;
import blizzard.development.monsters.database.cache.managers.MonstersCacheManager;
import blizzard.development.monsters.database.storage.MonstersData;
import blizzard.development.monsters.monsters.enums.Locations;
import blizzard.development.monsters.monsters.handlers.monsters.MonstersHandler;
import blizzard.development.monsters.monsters.handlers.packets.entity.EntitySpawn;
import blizzard.development.monsters.monsters.handlers.packets.entity.EntityUpdate;
import blizzard.development.monsters.monsters.handlers.world.MonstersWorldHandler;
import blizzard.development.monsters.utils.LocationUtils;
import blizzard.development.monsters.utils.PluginImpl;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.UUID;

public class MonstersPacketsHandler {
    private static MonstersPacketsHandler instance;

    private final ProtocolManager protocolManager = ProtocolLibrary.getProtocolManager();

    public void spawnMonster(Player player, String type, Location location, String displayName, Integer life, String value, String signature) {
        UUID uuid = UUID.randomUUID();
        Integer id = (int) Math.round(Math.random() * Integer.MAX_VALUE);

        EntityUpdate updateInfo = EntityUpdate.getInstance();
        EntitySpawn spawnEntity = EntitySpawn.getInstance();

        updateInfo.playerInfoUpdate(player, uuid, value, signature, protocolManager);
        spawnEntity.spawnEntity(player, location, uuid, id, protocolManager);
        HologramBuilder.getInstance().createHologram(player, uuid, location.add(0, 2.5 ,0), displayName, life);

        String serializedLocation = LocationUtils.getInstance().getSerializedLocation(location);

        MonstersHandler monstersHandler = MonstersHandler.getInstance();

        monstersHandler.createData(
                player,
                uuid.toString(),
                id.toString(),
                type,
                serializedLocation,
                life
        );

        monstersHandler.addMonster(player, List.of(uuid.toString()));
    }

    public static MonstersPacketsHandler getInstance() {
        if (instance == null) instance = new MonstersPacketsHandler();
        return instance;
    }
}