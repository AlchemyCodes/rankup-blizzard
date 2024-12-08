package blizzard.development.spawners.builders;

import blizzard.development.spawners.database.cache.managers.SpawnersCacheManager;
import blizzard.development.spawners.database.storage.SpawnersData;
import blizzard.development.spawners.handlers.enums.States;
import blizzard.development.spawners.utils.LocationUtil;
import blizzard.development.spawners.utils.SpawnersUtils;
import eu.decentsoftware.holograms.api.DHAPI;
import eu.decentsoftware.holograms.api.holograms.Hologram;
import org.bukkit.Location;
import blizzard.development.spawners.handlers.enums.Spawners;
import blizzard.development.spawners.utils.NumberFormat;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class DisplayBuilder {
    private static final Map<Location, Hologram> holograms = new HashMap<>();
    private static final SpawnersCacheManager cache = SpawnersCacheManager.getInstance();

    public static void createSpawnerDisplay(Location location, String spawnerType, Double amount, String state, String name) {
        Location hologramLocation = location.clone().add(0.5, 2.4, 0.5);

        final String id = UUID.randomUUID().toString().substring(0, 5);

        if (holograms.containsKey(hologramLocation)) {
            removeSpawnerDisplay(hologramLocation);
        }

        String formattedAmount = NumberFormat.getInstance().formatNumber(amount);

        String spawner;
        if (spawnerType.equals(Spawners.PIG.getType())) {
            spawner = "§dGerador de §lPorco";
        } else if (spawnerType.equals(Spawners.COW.getType())) {
            spawner = "§8Gerador de §lVaca";
        } else if (spawnerType.equals(Spawners.MOOSHROOM.getType())) {
            spawner = "§cGerador de §lCoguvaca";
        } else if (spawnerType.equals(Spawners.SHEEP.getType())) {
            spawner = "§fGerador de §lOvelha";
        } else if (spawnerType.equals(Spawners.ZOMBIE.getType())) {
            spawner = "§2Gerador de §lZumbi";
        } else {
            spawner = spawnerType;
        }

        String[] displayLines = {
                spawner,
                "§7Quantidade: §f§l" + formattedAmount + "§fx",
                "§7Dono: §f" + name,
                state
        };

        Hologram hologram = DHAPI.createHologram(
                "holo_" + id,
                hologramLocation
        );


        for (String line : displayLines) {
            DHAPI.addHologramLine(hologram, line);
        }

        holograms.put(hologramLocation, hologram);
    }

    public static void createAllSpawnerDisplay() {
        for (SpawnersData spawnerData : cache.spawnersCache.values()) {
            if (spawnerData != null) {
                Location location = LocationUtil.deserializeLocation(spawnerData.getLocation());
                if (location == null) return;
                if (location.getWorld() != null) {
                    createSpawnerDisplay(
                            location,
                            SpawnersUtils.getInstance().getSpawnerFromName(spawnerData.getType()).getType(),
                            spawnerData.getAmount(),
                            SpawnersUtils.getInstance().getSpawnerState(States.valueOf(spawnerData.getState().toUpperCase())),
                            spawnerData.getNickname()
                    );
                }
            }
        }
    }

    public static void removeSpawnerDisplay(Location location) {
        Location hologramLocation = location.clone().add(0.5, 2.4, 0.5);

        Hologram hologram = holograms.remove(hologramLocation);
        if (hologram != null) {
            DHAPI.removeHologram(hologram.getName());
        }
    }

    public static void removeAllSpawnerDisplay() {
        for (SpawnersData spawnerData : cache.spawnersCache.values()) {
            if (spawnerData != null) {
                Location location = LocationUtil.deserializeLocation(spawnerData.getLocation());
                if (location == null) return;
                if (location.getWorld() != null) {
                    removeSpawnerDisplay(location);
                }
            }
        }
    }
}
