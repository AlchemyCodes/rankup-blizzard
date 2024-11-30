package blizzard.development.plantations.tasks;

import blizzard.development.plantations.database.cache.ToolCacheManager;
import blizzard.development.plantations.database.dao.ToolDAO;
import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitRunnable;

import java.sql.SQLException;

public class ToolSaveTask extends BukkitRunnable {
    private final ToolDAO toolDAO;

    public ToolSaveTask(ToolDAO toolDAO) {
        this.toolDAO = toolDAO;
    }

    @Override
    public void run() {
        ToolCacheManager.toolsCache.forEach((id, toolData) -> {
            try {
                toolDAO.updateToolData(toolData);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });
    }
}
