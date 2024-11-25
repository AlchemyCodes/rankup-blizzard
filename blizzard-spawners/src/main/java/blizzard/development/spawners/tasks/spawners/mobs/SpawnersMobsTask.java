package blizzard.development.spawners.tasks.spawners.mobs;

import blizzard.development.spawners.database.cache.setters.SpawnersCacheSetters;
import blizzard.development.spawners.database.storage.SpawnersData;
import blizzard.development.spawners.handlers.spawners.SpawnersHandler;
import blizzard.development.spawners.utils.LocationUtil;
import blizzard.development.spawners.utils.NumberFormat;
import blizzard.development.spawners.utils.PluginImpl;
import blizzard.development.spawners.utils.SpawnersUtils;
import blizzard.development.spawners.utils.items.TextAPI;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.entity.LivingEntity;
import org.bukkit.scheduler.BukkitTask;

public class SpawnersMobsTask extends BukkitRunnable {
    private final SpawnersData spawnerData;
    private Entity displayMob;
    private double currentMobAmount;
    private BukkitTask bukkitTask;

    public SpawnersMobsTask(SpawnersData spawnerData) {
        this.spawnerData = spawnerData;
        this.currentMobAmount = spawnerData.getMobAmount();
        spawnMob();
    }

    public void setBukkitTask(BukkitTask bukkitTask) {
        this.bukkitTask = bukkitTask;
    }

    public void setCurrentMobAmount(double currentMobAmount) {
        this.currentMobAmount = currentMobAmount;
    }

    private void spawnMob() {
        Location mobLocation = LocationUtil.deserializeLocation(spawnerData.getMobLocation());
        SpawnersHandler handler = SpawnersHandler.getInstance();
        if (mobLocation != null) {
            if (displayMob != null && displayMob.isValid()) {
                displayMob.remove();
            }
            handler.spawnStaticMob(SpawnersUtils.getInstance().getSpawnerFromName(spawnerData.getType()), currentMobAmount, mobLocation);
            this.displayMob = findDisplayMob(mobLocation);
        }
    }

    @Override
    public void run() {
        Location mobLocation = LocationUtil.deserializeLocation(spawnerData.getMobLocation());
        if (mobLocation == null) {
            this.cancel();
            return;
        }

        if (displayMob == null || !displayMob.isValid() || !displayMob.hasMetadata("blizzard_spawners-mob")) {
            spawnMob();
            if (displayMob == null) {
                return;
            }
        }

        currentMobAmount += spawnerData.getAmount();

        if (displayMob instanceof LivingEntity) {
            displayMob.customName(TextAPI.parse(
                    "§7" + SpawnersUtils.getInstance().getMobNameByEntity(displayMob.getType()) +
                            " (x" + NumberFormat.getInstance().formatNumber(currentMobAmount) + ")"
            ));
            displayMob.setMetadata("blizzard_spawners-mob", new FixedMetadataValue(PluginImpl.getInstance().plugin, spawnerData.getType()));
            displayMob.setMetadata("blizzard_spawners-id", new FixedMetadataValue(PluginImpl.getInstance().plugin, spawnerData.getId()));
            SpawnersCacheSetters.getInstance().setSpawnerMobAmout(spawnerData.getId(), currentMobAmount);
        }
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
