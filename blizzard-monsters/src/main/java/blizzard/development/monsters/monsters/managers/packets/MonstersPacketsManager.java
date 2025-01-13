package blizzard.development.monsters.monsters.managers.packets;

import blizzard.development.monsters.builders.hologram.HologramBuilder;
import blizzard.development.monsters.monsters.holograms.MonsterNameHologram;
import blizzard.development.monsters.monsters.managers.monsters.MonstersGeneralManager;
import blizzard.development.monsters.monsters.managers.packets.entity.EntitySpawn;
import blizzard.development.monsters.monsters.managers.packets.entity.EntityUpdate;
import blizzard.development.monsters.utils.LocationUtils;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.UUID;

public class MonstersPacketsManager {
    private static MonstersPacketsManager instance;

    private final ProtocolManager protocolManager = ProtocolLibrary.getProtocolManager();

    public void spawnMonster(Player player, String type, Location location, String displayName, Integer life, String value, String signature) {
        UUID uuid = UUID.randomUUID();
        Integer id = (int) Math.round(Math.random() * Integer.MAX_VALUE);

        EntityUpdate updateInfo = EntityUpdate.getInstance();
        EntitySpawn spawnEntity = EntitySpawn.getInstance();

        updateInfo.playerInfoUpdate(player, uuid, value, signature, protocolManager);
        spawnEntity.spawnEntity(player, location, uuid, id, protocolManager);

        HologramBuilder.getInstance().createHologram(
                player,
                uuid,
                location.add(0, 2.5 ,0),
                MonsterNameHologram.getInstance().getLines(displayName, life)
                );

        String serializedLocation = LocationUtils.getInstance().getSerializedLocation(location);

        MonstersGeneralManager monstersManager = MonstersGeneralManager.getInstance();

        monstersManager.createData(
                player,
                uuid.toString(),
                id.toString(),
                type,
                serializedLocation,
                life
        );

        monstersManager.addMonster(player, List.of(uuid.toString()));
    }

    public static MonstersPacketsManager getInstance() {
        if (instance == null) instance = new MonstersPacketsManager();
        return instance;
    }
}