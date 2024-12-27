package blizzard.development.spawners.tasks.spawners.drops;

import blizzard.development.spawners.database.storage.SpawnersData;
import blizzard.development.spawners.utils.PluginImpl;
import org.bukkit.scheduler.BukkitTask;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class DropsAutoSellTaskManager {
    private static DropsAutoSellTaskManager instance;
    private final Map<String, DropsAutoSellTask> taskMap;

    private DropsAutoSellTaskManager() {
        this.taskMap = new ConcurrentHashMap<>();
    }

    public synchronized void startTask(SpawnersData spawnerData) {
        if (spawnerData == null || spawnerData.getId() == null || spawnerData.getId().isEmpty()) {
            return;
        }

        stopTask(spawnerData.getId());

        DropsAutoSellTask task = new DropsAutoSellTask(spawnerData);

        long interval = PluginImpl.getInstance().Config.getInt("spawners.auto-sell-cooldown") * 20L;

        BukkitTask bukkitTask = task.runTaskTimer(
                PluginImpl.getInstance().plugin,
                interval,
                interval
        );
        task.setBukkitTask(bukkitTask);

        taskMap.put(spawnerData.getId(), task);
    }

    public void stopTask(String spawnerId) {
        if (spawnerId == null || spawnerId.isEmpty()) {
            return;
        }

        DropsAutoSellTask task = taskMap.remove(spawnerId);

        if (task != null && !task.isCancelled()) {
            try {
                task.cancel();
            } catch (IllegalStateException ignored) {
            }
        }
    }

    public void stopAllTasks() {
        if (!taskMap.isEmpty()) {
            taskMap.values().forEach(task -> {
                if (task != null && !task.isCancelled()) {
                    try {
                        task.cancel();
                    } catch (IllegalStateException ignored) {
                    }
                }
            });
            taskMap.clear();
        }
    }

    public static synchronized DropsAutoSellTaskManager getInstance() {
        if (instance == null) {
            instance = new DropsAutoSellTaskManager();
        }
        return instance;
    }
}
