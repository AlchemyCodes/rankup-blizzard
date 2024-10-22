package blizzard.development.excavation.managers;

import blizzard.development.excavation.database.dao.ExcavatorDAO;
import blizzard.development.excavation.database.storage.ExcavatorData;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

public class BatchManager {
    private static final Queue<ExcavatorData> pendingTools = new ConcurrentLinkedQueue<>();
    private static boolean isProcessing = false;
    private static final int BATCH_SIZE = 20;
    private static final long PROCESS_INTERVAL = 120;

    public static void initialize(Plugin plugin) {
        Bukkit.getScheduler().runTaskTimerAsynchronously(plugin, () -> {
            if (!isProcessing && !pendingTools.isEmpty()) {
                processBatch();
            }
        }, PROCESS_INTERVAL, PROCESS_INTERVAL);
    }

    public static void addToPendingQueue(ExcavatorData excavatorData) {
        pendingTools.offer(excavatorData);
    }

    private static void processBatch() {
        isProcessing = true;
        int processed = 0;

        try {
            while (!pendingTools.isEmpty() && processed < BATCH_SIZE) {
                ExcavatorData excavatorData = pendingTools.poll();
                if (excavatorData != null) {
                    new ExcavatorDAO().createExcavatorData(excavatorData);
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