package blizzard.development.farm.tasks;

import blizzard.development.farm.database.cache.ToolCacheManager;
import blizzard.development.farm.database.dao.ToolDAO;
import org.bukkit.scheduler.BukkitRunnable;

import java.sql.SQLException;

public class ToolSaveTask extends BukkitRunnable {

    private final ToolDAO toolDAO;

    public ToolSaveTask(ToolDAO toolDAO) {
        this.toolDAO = toolDAO;
    }

    @Override
    public void run() {
        ToolCacheManager.getInstance().toolsCache.forEach((storage, toolData) -> {
            try {
                toolDAO.updateToolData(toolData);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });
    }
}
