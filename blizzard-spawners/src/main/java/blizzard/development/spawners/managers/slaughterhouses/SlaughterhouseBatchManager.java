package blizzard.development.spawners.managers.slaughterhouses;

import blizzard.development.spawners.database.storage.SlaughterhouseData;
import blizzard.development.spawners.handlers.enums.slaughterhouses.States;
import blizzard.development.spawners.tasks.slaughterhouses.kill.SlaughterhouseKillTaskManager;
import blizzard.development.spawners.utils.PluginImpl;
import org.bukkit.Bukkit;

import java.util.List;
import java.util.logging.Logger;

public class SlaughterhouseBatchManager {
    private static SlaughterhouseBatchManager instance;
    private static final Logger LOGGER = Logger.getLogger(SlaughterhouseBatchManager.class.getName());

    private SlaughterhouseBatchManager() {}

    public void startTasksInBatches(List<SlaughterhouseData> slaughterhouses, int batchSize, long delayBetweenBatches) {
        for (int i = 0; i < slaughterhouses.size(); i += batchSize) {
            int end = Math.min(i + batchSize, slaughterhouses.size());
            List<SlaughterhouseData> batch = slaughterhouses.subList(i, end);

            Bukkit.getScheduler().runTaskLater(PluginImpl.getInstance().plugin, () -> {
                for (SlaughterhouseData slaughterhouse : batch) {
                    try {
                        if (slaughterhouse.getState().equals(States.ON.getState())) {
                            SlaughterhouseKillTaskManager.getInstance().startTask(slaughterhouse);
                        }
                    } catch (Exception e) {
                        LOGGER.warning("Erro ao processar abatedouro " + slaughterhouse.getId() + ": " + e.getMessage());
                    }
                }
            }, (i / batchSize) * delayBetweenBatches);
        }
    }

    public void processSlaughterhousesDefault(List<SlaughterhouseData> slaughterhouses) {
        startTasksInBatches(slaughterhouses, 50, 10L);
    }

    public static synchronized SlaughterhouseBatchManager getInstance() {
        if (instance == null) {
            instance = new SlaughterhouseBatchManager();
        }
        return instance;
    }
}
