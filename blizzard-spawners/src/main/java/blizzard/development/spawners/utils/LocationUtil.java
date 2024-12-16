package blizzard.development.spawners.utils;

import blizzard.development.spawners.database.cache.managers.SlaughterhouseCacheManager;
import blizzard.development.spawners.database.cache.managers.SpawnersCacheManager;
import blizzard.development.spawners.database.storage.SlaughterhouseData;
import blizzard.development.spawners.database.storage.SpawnersData;
import blizzard.development.spawners.utils.items.TextAPI;
import com.plotsquared.core.PlotSquared;
import com.plotsquared.core.plot.Plot;
import com.plotsquared.core.plot.PlotArea;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

import java.util.*;

public class LocationUtil {

    public static String getSerializedLocation(Location location) {
        return location.getWorld().getName() + ";" + location.getBlockX() + ";" + location.getBlockY() + ";" + location.getBlockZ() + ";" + location.getYaw() + ";" + location.getPitch();
    }

    public static Location deserializeLocation(String serializedLocation) {
        try {
            String[] parts = serializedLocation.split(";");
            if (parts.length != 6) {
                throw new IllegalArgumentException();
            }

            World world = PluginImpl.getInstance().plugin.getServer().getWorld(parts[0]);
            if (world == null) {
                throw new IllegalArgumentException();
            }

            return new Location(
                    world,
                    Double.parseDouble(parts[1]),
                    Double.parseDouble(parts[2]),
                    Double.parseDouble(parts[3]),
                    Float.parseFloat(parts[4]),
                    Float.parseFloat(parts[5])
            );
        } catch (Exception e) {
            return null;
        }
    }

    public static Boolean hasNearbySpawners(Location location, int radius) {
        int x = location.getBlockX();
        int y = location.getBlockY();
        int z = location.getBlockZ();

        for (int i = -radius; i <= radius; i++) {
            for (int j = -radius; j <= radius; j++) {
                for (int k = -radius; k <= radius; k++) {
                    Location checkLoc = new Location(location.getWorld(), x + i, y + j, z + k);

                    if (i == 0 && j == 0 && k == 0) continue;

                    if (checkLoc.getBlock().getType() == Material.SPAWNER &&
                            checkLoc.distance(location) <= radius) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public static List<SpawnersData> getNearbySpawners(Location location, int radius) {
        final SpawnersCacheManager cache = SpawnersCacheManager.getInstance();
        List<SpawnersData> spawnersData = new ArrayList<>();

        int x = location.getBlockX();
        int y = location.getBlockY();
        int z = location.getBlockZ();

        for (int i = -radius; i <= radius; i++) {
            for (int j = -radius; j <= radius; j++) {
                for (int k = -radius; k <= radius; k++) {
                    Location checkLoc = new Location(location.getWorld(), x + i, y + j, z + k);

                    if (i == 0 && j == 0 && k == 0) continue;

                    if (checkLoc.getBlock().getType() == Material.SPAWNER && checkLoc.distance(location) <= radius) {
                        String serializedLocation = LocationUtil.getSerializedLocation(checkLoc);

                        for (SpawnersData spawner : cache.spawnersCache.values()) {
                            if (spawner.getLocation().equals(serializedLocation)) {
                                spawnersData.add(spawner);
                                break;
                            }
                        }
                    }
                }
            }
        }

        spawnersData.sort((data1, data2) -> {
            Location loc1 = deserializeLocation(data1.getLocation());
            Location loc2 = deserializeLocation(data2.getLocation());
            return Double.compare(loc1.distance(location), loc2.distance(location));
        });

        return spawnersData;
    }

    public static Boolean hasNearbySlaughterhouses(Location location, int radius) {
        SlaughterhouseCacheManager cache = SlaughterhouseCacheManager.getInstance();
        Collection<SlaughterhouseData> slaughterhouses = cache.slaughterhouseCache.values();

        for (SlaughterhouseData slaughterhouse : slaughterhouses) {
            Location slaughterhouseLocation = deserializeLocation(slaughterhouse.getLocation());
            if (slaughterhouseLocation == null) continue;

            if (slaughterhouseLocation.getWorld().equals(location.getWorld()) &&
                    slaughterhouseLocation.distance(location) <= radius) {
                return true;
            }
        }
        return false;
    }


    public static Boolean terrainVerify(Player player, Block block) {
        com.plotsquared.core.location.Location blockLocation = getPlotLocation(block);

        UUID playerUUID = player.getUniqueId();
        PlotArea plotArea = PlotSquared.get().getPlotAreaManager().getPlotArea(blockLocation);

        if (plotArea == null) {
            player.sendActionBar(TextAPI.parse("§c§lEI! §cVocê não pode colocar um spawner nesse local."));
            return false;
        }

        Plot plot = plotArea.getPlot(blockLocation);

        if (plot == null) {
            player.sendActionBar(TextAPI.parse("§c§lEI! §cVocê não pode colocar um spawner nesse local."));
            return false;
        }

        if (!(Objects.equals(plot.getOwner(), playerUUID) || plot.isAdded(playerUUID) || player.hasPermission("blizzard.spawners.admin"))) {
            player.sendActionBar(TextAPI.parse("§c§lEI! §cVocê não tem permissão para colocar um spawner nesse local."));
            return false;
        }
        return true;
    }

    public static Boolean interactVerify(Player player, Block block) {
        com.plotsquared.core.location.Location blockLocation = getPlotLocation(block);

        UUID playerUUID = player.getUniqueId();
        PlotArea plotArea = PlotSquared.get().getPlotAreaManager().getPlotArea(blockLocation);

        if (plotArea == null) {
            player.sendActionBar(TextAPI.parse("§c§lEI! §cVocê não pode interagir com esse spawner."));
            return false;
        }

        Plot plot = plotArea.getPlot(blockLocation);

        if (plot == null) {
            player.sendActionBar(TextAPI.parse("§c§lEI! §cVocê não pode interagir com esse spawner."));
            return false;
        }

        if (!(Objects.equals(plot.getOwner(), playerUUID) || plot.isAdded(playerUUID) || player.hasPermission("blizzard.spawners.admin"))) {
            player.sendActionBar(TextAPI.parse("§c§lEI! §cVocê não pode interagir com esse spawner."));
            return false;
        }
        return true;
    }

    public static com.plotsquared.core.location.Location getPlotLocation(Block block) {
        return com.plotsquared.core.location.Location.at(
                block.getLocation().getWorld().getName(),
                (int) block.getLocation().getX(),
                (int) block.getLocation().getY(),
                (int) block.getLocation().getZ()
        );
    }
}
