package blizzard.development.spawners.builders.slaughterhouses;

import blizzard.development.spawners.database.cache.managers.SlaughterhouseCacheManager;
import blizzard.development.spawners.database.cache.managers.SpawnersCacheManager;
import blizzard.development.spawners.database.storage.SlaughterhouseData;
import blizzard.development.spawners.database.storage.SpawnersData;
import blizzard.development.spawners.handlers.enums.slaughterhouses.States;
import blizzard.development.spawners.handlers.slaughterhouse.SlaughterhouseHandler;
import blizzard.development.spawners.managers.slaughterhouses.SlaughterhouseManager;
import blizzard.development.spawners.utils.LocationUtil;
import blizzard.development.spawners.utils.NumberFormat;
import blizzard.development.spawners.utils.SlaughterhousesUtils;
import blizzard.development.spawners.utils.SpawnersUtils;
import eu.decentsoftware.holograms.api.DHAPI;
import eu.decentsoftware.holograms.api.holograms.Hologram;
import org.bukkit.Location;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class DisplayBuilder {
    private static final Map<Location, Hologram> slaughterhouseHolograms = new HashMap<>();
    private static final SlaughterhouseCacheManager cache = SlaughterhouseCacheManager.getInstance();

    public static void createSlaughterhouseDisplay(Location location, Integer level, String state, String name) {
        Location hologramLocation = location.clone().add(0.5, 2.0, 0.5);

        final String id = UUID.randomUUID().toString().substring(0, 8);

        if (slaughterhouseHolograms.containsKey(hologramLocation)) {
            return;
        }

        String[] displayLines = {
                SlaughterhouseHandler.getInstance().getDisplayName(level),
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

        slaughterhouseHolograms.put(hologramLocation, hologram);
    }

    public static void createAllSlaughterhouseDisplay() {
        for (SlaughterhouseData slaughterhouseData : cache.slaughterhouseCache.values()) {
            if (slaughterhouseData != null) {
                Location location = LocationUtil.deserializeLocation(slaughterhouseData.getLocation());
                if (location == null) return;
                if (location.getWorld() != null) {
                    createSlaughterhouseDisplay(
                            location,
                            Integer.parseInt(slaughterhouseData.getTier()),
                            SlaughterhousesUtils.getInstance().getSlaughterhouseState(States.valueOf(slaughterhouseData.getState().toUpperCase())),
                            slaughterhouseData.getNickname()
                    );
                    System.out.println("foi");
                }
            }
        }
    }

    public static void removeSlaughterhouseDisplay(Location location) {
        Location hologramLocation = location.clone().add(0.5, 2.0, 0.5);

        Hologram hologram = slaughterhouseHolograms.remove(hologramLocation);
        if (hologram != null) {
            DHAPI.removeHologram(hologram.getName());
        }
    }

    public static void removeAllSlaughterhouseDisplay() {
        for (SlaughterhouseData slaughterhouseData : cache.slaughterhouseCache.values()) {
            if (slaughterhouseData != null) {
                Location location = LocationUtil.deserializeLocation(slaughterhouseData.getLocation());
                if (location == null) return;
                if (location.getWorld() != null) {
                    removeSlaughterhouseDisplay(location);
                }
            }
        }
    }
}
