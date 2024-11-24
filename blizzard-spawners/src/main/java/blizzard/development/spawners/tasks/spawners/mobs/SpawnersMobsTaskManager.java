
package blizzard.development.spawners.tasks.spawners.mobs;

import blizzard.development.spawners.database.storage.SpawnersData;
import blizzard.development.spawners.utils.PluginImpl;
import org.bukkit.scheduler.BukkitTask;

import java.util.HashMap;
import java.util.Map;

public class SpawnersMobsTaskManager {
    private static SpawnersMobsTaskManager instance;
    private final Map<String, BukkitTask> spawnerTasks;

    private SpawnersMobsTaskManager() {
        this.spawnerTasks = new HashMap<>();
    }

    public void startTask(SpawnersData spawnerData) {
        stopTask(spawnerData.getId());

        SpawnersMobsTask task = new SpawnersMobsTask(spawnerData);
        long interval = spawnerData.getSpeed_level() * 20L;

        BukkitTask bukkitTask = task.runTaskTimer(PluginImpl.getInstance().plugin, 1L, interval);
        spawnerTasks.put(spawnerData.getId(), bukkitTask);
    }

    public void stopTask(String spawnerId) {
        BukkitTask task = spawnerTasks.remove(spawnerId);
        if (task != null) {
            task.cancel();
        }
    }

    public void stopAllTasks() {
        spawnerTasks.values().forEach(BukkitTask::cancel);
        spawnerTasks.clear();
    }

    public static SpawnersMobsTaskManager getInstance() {
        if (instance == null) {
            instance = new SpawnersMobsTaskManager();
        }
        return instance;
    }
}