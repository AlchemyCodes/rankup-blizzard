package blizzard.development.spawners.managers;

import blizzard.development.spawners.database.dao.SpawnersDAO;
import blizzard.development.spawners.database.storage.SpawnersData;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

public class BatchManager {
    private static final Queue<SpawnersData> pendingSpawners = new ConcurrentLinkedQueue<>();
    private static boolean isProcessing = false;
    private static final int BATCH_SIZE = 20;
    private static final long PROCESS_INTERVAL = 120;

    public static void initialize(Plugin plugin) {
        Bukkit.getScheduler().runTaskTimerAsynchronously(plugin, () -> {
            if (!isProcessing && !pendingSpawners.isEmpty()) {
                processBatch();
            }
        }, PROCESS_INTERVAL, PROCESS_INTERVAL);
    }

    public static void addToPendingQueue(SpawnersData spawnersData) {
        pendingSpawners.offer(spawnersData);
    }

    private static void processBatch() {
        isProcessing = true;
        int processed = 0;

        try {
            while (!pendingSpawners.isEmpty() && processed < BATCH_SIZE) {
                SpawnersData spawner = pendingSpawners.poll();
                if (spawner != null) {
                    new SpawnersDAO().createSpawnerData(spawner);
                    processed++;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            isProcessing = false;
        }
    }
}