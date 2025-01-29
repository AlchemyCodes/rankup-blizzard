package blizzard.development.farm.managers;

import blizzard.development.farm.database.dao.ToolDAO;
import blizzard.development.farm.database.storage.ToolData;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

public class BatchManager {
    private static final Queue<ToolData> pendingTools = new ConcurrentLinkedQueue<>();
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

    public static void addToPendingQueue(ToolData toolData) {
        pendingTools.offer(toolData);
    }

    private static void processBatch() {
        isProcessing = true;
        int processed = 0;

        try {
            while (!pendingTools.isEmpty() && processed < BATCH_SIZE) {
                ToolData tool = pendingTools.poll();
                if (tool != null) {
                    ToolDAO toolDAO = new ToolDAO();
                    toolDAO.createToolData(
                        tool
                    );
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
