package blizzard.development.bosses.managers;

import blizzard.development.bosses.database.dao.ToolsDAO;
import blizzard.development.bosses.database.storage.ToolsData;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

public class BatchManager {
    private static final Queue<ToolsData> pendingTools = new ConcurrentLinkedQueue<>();
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

    public static void addToPendingQueue(ToolsData toolsData) {
        pendingTools.offer(toolsData);
    }

    private static void processBatch() {
        isProcessing = true;
        int processed = 0;

        try {
            while (!pendingTools.isEmpty() && processed < BATCH_SIZE) {
                ToolsData tool = pendingTools.poll();
                if (tool != null) {
                    new ToolsDAO().createToolsData(tool);
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