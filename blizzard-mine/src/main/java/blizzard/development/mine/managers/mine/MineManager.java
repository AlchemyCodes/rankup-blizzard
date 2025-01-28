package blizzard.development.mine.managers.mine;

import blizzard.development.core.Main;
import blizzard.development.mine.database.cache.methods.PlayerCacheMethods;
import blizzard.development.mine.managers.events.AvalancheManager;
import blizzard.development.mine.mine.enums.LocationEnum;
import blizzard.development.mine.utils.PluginImpl;
import blizzard.development.mine.utils.apis.Cuboid;
import blizzard.development.mine.utils.locations.LocationUtils;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;

public class MineManager {
    private static final MineManager instance = new MineManager();
    private final BlockManager regionManager = BlockManager.getInstance();

    public static MineManager getInstance() {
        return instance;
    }

    public void transformArea(Player player) {
        Location centerLocation = LocationUtils.getLocation(LocationEnum.CENTER.getName());
        int centerChunkX = centerLocation.getBlockX() >> 4;
        int centerChunkZ = centerLocation.getBlockZ() >> 4;
        int startChunkX = centerChunkX - 1;
        int startChunkZ = centerChunkZ - 1;

        Material material = AvalancheManager.isAvalancheActive
                ? Material.SNOW_BLOCK
                : Material.getMaterial(PlayerCacheMethods.getInstance().getAreaBlock(player));

        processChunkBatch(player, centerLocation, startChunkX, startChunkZ, material, 0);
    }

    private void processChunkBatch(Player player, Location centerLocation, int startChunkX, int startChunkZ, Material material, int batchIndex) {
        if (batchIndex >= 4) return;

        int xOffset = batchIndex / 2;
        int zOffset = batchIndex % 2;
        int currentChunkX = startChunkX + xOffset;
        int currentChunkZ = startChunkZ + zOffset;

        Location point1 = new Location(
                centerLocation.getWorld(),
                currentChunkX << 4,
                centerLocation.getBlockY(),
                currentChunkZ << 4
        );

        Location point2 = point1.clone().add(
                15,
                PluginImpl.getInstance().Config.getInt("mine.y-blocks"),
                15
        );

        Cuboid cuboid = new Cuboid(point1, point2);

        BlockManager.MineRegion existingRegion = regionManager.findRegionAt(point1);
        if (existingRegion != null) {
            regionManager.removeRegion(existingRegion);
        }

        Bukkit.getScheduler().runTaskLater(Main.getInstance(), () -> {
            regionManager.addRegion(player, cuboid, material);

            processChunkBatch(player, centerLocation, startChunkX, startChunkZ, material, batchIndex + 1);
        }, 1L);
    }

    public void clearPlayerRegions(Player player) {
        regionManager.getPlayerRegions(player).forEach(regionManager::removeRegion);
    }

    public boolean isInMiningArea(Location location) {
        return regionManager.findRegionAt(location) != null;
    }

    public Material getBlockTypeAt(Location location) {
        BlockManager.MineRegion region = regionManager.findRegionAt(location);
        return region != null ? region.material() : null;
    }
}