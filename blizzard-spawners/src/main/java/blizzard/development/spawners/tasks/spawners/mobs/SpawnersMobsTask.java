package blizzard.development.spawners.tasks.spawners.mobs;

import blizzard.development.spawners.database.cache.managers.SpawnersCacheManager;
import blizzard.development.spawners.database.storage.SpawnersData;
import blizzard.development.spawners.handlers.StaticMobs;
import blizzard.development.spawners.utils.LocationUtil;
import blizzard.development.spawners.utils.NumberFormat;
import blizzard.development.spawners.utils.items.TextAPI;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.entity.LivingEntity;

public class SpawnersMobsTask extends BukkitRunnable {
    private final SpawnersData spawnerData;
    private Entity displayMob;
    private double currentMobAmount;

    public SpawnersMobsTask(SpawnersData spawnerData) {
        this.spawnerData = spawnerData;
        this.currentMobAmount = spawnerData.getMob_amount();

        Location mobLocation = LocationUtil.deserializeLocation(spawnerData.getLocation());
        if (mobLocation != null) {
            this.displayMob = findDisplayMob(mobLocation);
            if (this.displayMob == null) {
                this.displayMob = findDisplayMob(mobLocation);
            }
        }
    }

    @Override
    public void run() {
        if (displayMob == null || !displayMob.isValid()) {
            this.cancel();
            return;
        }

        currentMobAmount += spawnerData.getAmount();

        if (displayMob instanceof LivingEntity) {
            displayMob.customName(TextAPI.parse(
                    "§7" + StaticMobs.getMobNameByEntity(displayMob.getType()) +
                            " (x" + NumberFormat.getInstance().formatNumber(currentMobAmount) + ")"
            ));
        }

        spawnerData.setMob_amount(currentMobAmount);
        SpawnersCacheManager.getInstance().cacheSpawnerData(spawnerData.getId(), spawnerData);
    }

    private Entity findDisplayMob(Location location) {
        return location.getWorld().getNearbyEntities(location, 1, 1, 1).stream()
                .filter(entity -> entity.hasMetadata("blizzard_spawners-mob"))
                .findFirst()
                .orElse(null);
    }

    public void cancel() {
        super.cancel();
        if (displayMob != null) {
            displayMob.remove();
        }
    }
}