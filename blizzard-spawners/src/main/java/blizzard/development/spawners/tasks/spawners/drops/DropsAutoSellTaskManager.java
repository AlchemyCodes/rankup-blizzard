package blizzard.development.spawners.tasks.spawners.drops;

import blizzard.development.spawners.database.storage.SpawnersData;
import blizzard.development.spawners.utils.PluginImpl;
import org.bukkit.scheduler.BukkitTask;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class DropsAutoSellTaskManager {
    private static DropsAutoSellTaskManager instance;
    private final Map<String, BukkitTask> taskMap;

    private DropsAutoSellTaskManager() {
        this.taskMap = new ConcurrentHashMap<>();
    }

    public synchronized void startTask(SpawnersData spawnerData) {
        String spawnerId = spawnerData.getId();
        stopTask(spawnerId);

        DropsAutoSellTask task = new DropsAutoSellTask(spawnerData);
        long interval = PluginImpl.getInstance().Config.getInt("spawners.auto-sell-cooldown") * 20L;

        BukkitTask bukkitTask = task.runTaskTimer(
                PluginImpl.getInstance().plugin,
                interval,
                interval
        );

        taskMap.put(spawnerId, bukkitTask);
    }

    public synchronized void stopTask(String spawnerId) {
        BukkitTask task = taskMap.remove(spawnerId);
        if (task != null) {
            task.cancel();
        }
    }

    public synchronized void stopAllTasks() {
        taskMap.values().forEach(BukkitTask::cancel);
        taskMap.clear();
    }

    public static synchronized DropsAutoSellTaskManager getInstance() {
        if (instance == null) {
            instance = new DropsAutoSellTaskManager();
        }
        return instance;
    }
}