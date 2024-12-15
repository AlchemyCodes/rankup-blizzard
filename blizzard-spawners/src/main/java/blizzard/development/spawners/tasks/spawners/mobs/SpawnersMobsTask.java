package blizzard.development.spawners.tasks.spawners.mobs;

import blizzard.development.spawners.database.cache.setters.SpawnersCacheSetters;
import blizzard.development.spawners.database.storage.SpawnersData;
import blizzard.development.spawners.utils.LocationUtil;
import blizzard.development.spawners.utils.NumberFormat;
import blizzard.development.spawners.utils.PluginImpl;
import blizzard.development.spawners.utils.SpawnersUtils;
import blizzard.development.spawners.utils.items.TextAPI;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import java.util.List;

public class SpawnersMobsTask extends BukkitRunnable {
    private final SpawnersData spawnerData;
    private LivingEntity displayMob;
    private double currentMobAmount;
    private BukkitTask bukkitTask;
    private final SpawnersUtils spawnersUtils;

    public SpawnersMobsTask(SpawnersData spawnerData) {
        this.spawnerData = spawnerData;
        this.currentMobAmount = spawnerData.getMobAmount();
        this.spawnersUtils = SpawnersUtils.getInstance();
        setupInitialMob();
    }

    private void setupInitialMob() {
        Location mobLocation = LocationUtil.deserializeLocation(spawnerData.getMobLocation());
        if (mobLocation == null || !mobLocation.getChunk().isLoaded()) {
            return;
        }
        removeExistingSpawnerMobs(mobLocation);
        createOrUpdateDisplayMob(mobLocation);
    }

    private void createOrUpdateDisplayMob(Location mobLocation) {
        EntityType entityType = spawnersUtils.getEntityTypeFromSpawner(
                spawnersUtils.getSpawnerFromName(spawnerData.getType())
        );

        List<Entity> existingMobs = mobLocation.getWorld().getNearbyEntities(mobLocation, 1, 1, 1).stream()
                .filter(entity ->
                        entity.hasMetadata("blizzard_spawners-mob") &&
                                entity.getType() == entityType
                )
                .toList();

        if (!existingMobs.isEmpty()) {
            this.displayMob = (LivingEntity) existingMobs.get(0);
        } else {
            this.displayMob = (LivingEntity) mobLocation.getWorld().spawnEntity(mobLocation, entityType);
        }

        configureMob(mobLocation);
    }

    private void configureMob(Location mobLocation) {
        if (displayMob == null) return;

        displayMob.setAI(false);
        displayMob.setGravity(false);
        displayMob.teleport(mobLocation);
        updateMobDisplay();
    }

    private void updateMobDisplay() {
        if (displayMob == null) return;

        displayMob.customName(TextAPI.parse(
                "§7" + spawnersUtils.getMobNameByEntity(displayMob.getType()) +
                        " (x" + NumberFormat.getInstance().formatNumber(currentMobAmount) + ")"
        ));
        displayMob.setCustomNameVisible(true);

        displayMob.setMetadata("blizzard_spawners-mob",
                new FixedMetadataValue(PluginImpl.getInstance().plugin, spawnerData.getType()));

        displayMob.setMetadata("blizzard_spawners-id",
                new FixedMetadataValue(PluginImpl.getInstance().plugin, spawnerData.getId()));
    }

    private void removeExistingSpawnerMobs(Location location) {
        location.getWorld().getNearbyEntities(location, 1, 1, 1).stream()
                .filter(entity -> entity.hasMetadata("blizzard_spawners-mob"))
                .forEach(Entity::remove);
    }

    public void setBukkitTask(BukkitTask bukkitTask) {
        this.bukkitTask = bukkitTask;
    }

    public void setCurrentMobAmount(double currentMobAmount) {
        this.currentMobAmount = currentMobAmount;
    }

    @Override
    public void run() {
        Location mobLocation = LocationUtil.deserializeLocation(spawnerData.getMobLocation());
        if (mobLocation == null || !mobLocation.getChunk().isLoaded()) {
            return;
        }

        if (displayMob == null || !displayMob.isValid()) {
            setupInitialMob();
            return;
        }

        currentMobAmount += spawnerData.getAmount();

        updateMobDisplay();

        SpawnersCacheSetters.getInstance().setSpawnerMobAmout(spawnerData.getId(), currentMobAmount);
    }

    @Override
    public void cancel() {
        super.cancel();
        if (displayMob != null) {
            displayMob.remove();
        }
        if (bukkitTask != null) {
            bukkitTask.cancel();
        }
    }
}