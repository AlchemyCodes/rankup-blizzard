package blizzard.development.mine.tasks.manager;

import blizzard.development.mine.database.dao.BoosterDAO;
import blizzard.development.mine.database.dao.PlayerDAO;
import blizzard.development.mine.database.dao.ToolDAO;
import blizzard.development.mine.tasks.DatabaseSaveTask;
import blizzard.development.mine.tasks.booster.BoosterTask;
import blizzard.development.mine.tasks.mine.ExtractorUpdateTask;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

public class TaskManager {
    private final Plugin plugin;
    private final DatabaseSaveTask databaseSaveTask;
    private final ExtractorUpdateTask extractorUpdateTask;
    private final BoosterTask boosterTask;
    private BukkitTask task;

    public TaskManager(Plugin plugin, ToolDAO toolDAO, PlayerDAO playerDAO, BoosterDAO boosterDAO) {
        this.plugin = plugin;
        this.boosterTask = new BoosterTask();
        this.databaseSaveTask = new DatabaseSaveTask(toolDAO, playerDAO, boosterDAO);
        this.extractorUpdateTask = new ExtractorUpdateTask();
    }

    public void start() {
        task = new BukkitRunnable() {
            @Override
            public void run() {
                databaseSaveTask.run();
                extractorUpdateTask.run();
                boosterTask.run();
            }
        }.runTaskTimerAsynchronously(plugin, 0, 20L * 3);
    }

    public void stop() {
        if (task != null && !task.isCancelled()) {
            task.cancel();
        }
    }
}
