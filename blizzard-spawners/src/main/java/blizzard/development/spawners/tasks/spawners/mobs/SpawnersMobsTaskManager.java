package blizzard.development.spawners.tasks.spawners.mobs;

import blizzard.development.spawners.database.storage.SpawnersData;
import blizzard.development.spawners.utils.LocationUtil;
import blizzard.development.spawners.utils.PluginImpl;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.scheduler.BukkitTask;

import java.util.HashMap;
import java.util.Map;

public class SpawnersMobsTaskManager {
    private static SpawnersMobsTaskManager instance;
    private final Map<String, SpawnersMobsTask> spawnerTasks;

    private SpawnersMobsTaskManager() {
        this.spawnerTasks = new HashMap<>();
    }

    public void startTask(SpawnersData spawnerData) {
        stopTask(spawnerData.getId());
        cleanupOldMobs(spawnerData);

        SpawnersMobsTask task = new SpawnersMobsTask(spawnerData);
        double interval = spawnerData.getSpeedLevel() * 20L;

        BukkitTask bukkitTask = task.runTaskTimer(PluginImpl.getInstance().plugin, 1L, (long) interval);
        task.setBukkitTask(bukkitTask);
        spawnerTasks.put(spawnerData.getId(), task);
    }

    public void syncMobAmount(String spawnerId, double newAmount) {
        SpawnersMobsTask task = spawnerTasks.get(spawnerId);
        if (task != null) {
            task.setCurrentMobAmount(newAmount);
        }
    }

    private void cleanupOldMobs(SpawnersData spawnerData) {
        Location location = LocationUtil.deserializeLocation(spawnerData.getMobLocation());
        if (location != null && location.getWorld() != null) {
            location.getWorld().getNearbyEntities(location, 1, 1, 1).stream()
                    .filter(entity -> entity.hasMetadata("blizzard_spawners-mob"))
                    .forEach(Entity::remove);
        }
    }

    public void stopTask(String spawnerId) {
        SpawnersMobsTask task = spawnerTasks.remove(spawnerId);
        if (task != null) {
            task.cancel();
        }
    }

    public void stopAllTasks() {
        spawnerTasks.values().forEach(SpawnersMobsTask::cancel);
        spawnerTasks.clear();
        removeAllMobs();
    }

    private void removeAllMobs() {
        for (World world : PluginImpl.getInstance().plugin.getServer().getWorlds()) {
            world.getEntities().stream()
                    .filter(entity -> entity.hasMetadata("blizzard_spawners-mob"))
                    .forEach(Entity::remove);
        }
    }

    public static SpawnersMobsTaskManager getInstance() {
        if (instance == null) {
            instance = new SpawnersMobsTaskManager();
        }
        return instance;
    }
}
