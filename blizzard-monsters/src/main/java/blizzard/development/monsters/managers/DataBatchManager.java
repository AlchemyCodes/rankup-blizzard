package blizzard.development.monsters.managers;

import blizzard.development.monsters.database.dao.ToolsDAO;
import blizzard.development.monsters.database.storage.ToolsData;
import blizzard.development.monsters.monsters.enums.Tools;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

public class DataBatchManager {
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
                    new ToolsDAO().createToolData(tool);
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
