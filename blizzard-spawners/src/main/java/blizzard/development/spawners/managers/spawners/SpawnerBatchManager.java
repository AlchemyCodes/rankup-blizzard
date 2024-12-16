package blizzard.development.spawners.managers.spawners;

import blizzard.development.spawners.database.storage.SpawnersData;
import blizzard.development.spawners.tasks.spawners.drops.DropsAutoSellTaskManager;
import blizzard.development.spawners.tasks.spawners.mobs.SpawnersMobsTaskManager;
import blizzard.development.spawners.utils.PluginImpl;
import org.bukkit.Bukkit;

import java.util.List;
import java.util.logging.Logger;

public class SpawnerBatchManager {
    private static SpawnerBatchManager instance;
    private static final Logger LOGGER = Logger.getLogger(SpawnerBatchManager.class.getName());

    private SpawnerBatchManager() {}

    public void startTasksInBatches(List<SpawnersData> spawners, int batchSize, long delayBetweenBatches) {
        for (int i = 0; i < spawners.size(); i += batchSize) {
            int end = Math.min(i + batchSize, spawners.size());
            List<SpawnersData> batch = spawners.subList(i, end);

            Bukkit.getScheduler().runTaskLater(PluginImpl.getInstance().plugin, () -> {
                for (SpawnersData spawner : batch) {
                    try {
                        SpawnersMobsTaskManager.getInstance().startTask(spawner);

                        if (spawner.getAutoSellState()) {
                            DropsAutoSellTaskManager.getInstance().startTask(spawner);
                        }
                    } catch (Exception e) {
                        LOGGER.warning("Erro ao processar spawner " + spawner.getId() + ": " + e.getMessage());
                    }
                }
            }, (i / batchSize) * delayBetweenBatches);
        }
    }

//    public void startTasksAsynchronously(List<SpawnersData> spawners, int batchSize) {
//        CompletableFuture.runAsync(() -> {
//            for (int i = 0; i < spawners.size(); i += batchSize) {
//                int end = Math.min(i + batchSize, spawners.size());
//                List<SpawnersData> batch = spawners.subList(i, end);
//
//                Bukkit.getScheduler().runTask(PluginImpl.getInstance().plugin, () -> {
//                    for (SpawnersData spawner : batch) {
//                        try {
//                            SpawnersMobsTaskManager.getInstance().startTask(spawner);
//
//
//                            if (spawner.getAutoSellState()) {
//                                DropsAutoSellTaskManager.getInstance().startTask(spawner);
//                            }
//                        } catch (Exception e) {
//                            LOGGER.warning("Erro ao processar spawner " + spawner.getId() + ": " + e.getMessage());
//                        }
//                    }
//                });
//
//                try {
//                    Thread.sleep(50);
//                } catch (InterruptedException e) {
//                    LOGGER.warning("Interrupção durante processamento de spawners: " + e.getMessage());
//                    Thread.currentThread().interrupt();
//                }
//            }
//        });
//    }

    public void processSpawnersDefault(List<SpawnersData> spawners) {
        startTasksInBatches(spawners, 50, 10L);
    }

    public static synchronized SpawnerBatchManager getInstance() {
        if (instance == null) {
            instance = new SpawnerBatchManager();
        }
        return instance;
    }
}