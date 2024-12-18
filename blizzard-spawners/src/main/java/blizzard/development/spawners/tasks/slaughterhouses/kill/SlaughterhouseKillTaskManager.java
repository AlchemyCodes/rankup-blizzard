package blizzard.development.spawners.tasks.slaughterhouses.kill;

import blizzard.development.spawners.database.storage.SlaughterhouseData;
import blizzard.development.spawners.handlers.slaughterhouse.SlaughterhouseHandler;
import blizzard.development.spawners.utils.PluginImpl;
import org.bukkit.scheduler.BukkitTask;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class SlaughterhouseKillTaskManager {
    private static SlaughterhouseKillTaskManager instance;
    private final Map<String, SlaughterhouseKillTask> taskMap;

    private SlaughterhouseKillTaskManager() {
        this.taskMap = new ConcurrentHashMap<>();
    }

    public synchronized void startTask(SlaughterhouseData slaughterhouseData) {
        if (slaughterhouseData == null || slaughterhouseData.getId() == null || slaughterhouseData.getId().isEmpty()) {
            return;
        }

        stopTask(slaughterhouseData.getId());

        SlaughterhouseKillTask task = new SlaughterhouseKillTask(slaughterhouseData);

        final SlaughterhouseHandler handler = SlaughterhouseHandler.getInstance();

        long interval = handler.getKillCooldown(Integer.parseInt(slaughterhouseData.getTier())) * 20L;
        if (interval <= 0) {
            return;
        }

        BukkitTask bukkitTask = task.runTaskTimer(
                PluginImpl.getInstance().plugin,
                interval,
                interval
        );
        task.setBukkitTask(bukkitTask);

        taskMap.put(slaughterhouseData.getId(), task);
    }

    public void stopTask(String slaughterhouseId) {
        if (slaughterhouseId == null || slaughterhouseId.isEmpty()) {
            return;
        }

        SlaughterhouseKillTask task = taskMap.remove(slaughterhouseId);
        if (task != null && !task.isCancelled()) {
            try {
                task.cancel();
            } catch (IllegalStateException ignored) {}
        }
    }

    public void stopAllTasks() {
        if (!taskMap.isEmpty()) {
            taskMap.values().forEach(task -> {
                if (task != null && !task.isCancelled()) {
                    try {
                        task.cancel();
                    } catch (IllegalStateException ignored) {}
                }
            });
            taskMap.clear();
        }
    }

    public static synchronized SlaughterhouseKillTaskManager getInstance() {
        if (instance == null) {
            instance = new SlaughterhouseKillTaskManager();
        }
        return instance;
    }
}
