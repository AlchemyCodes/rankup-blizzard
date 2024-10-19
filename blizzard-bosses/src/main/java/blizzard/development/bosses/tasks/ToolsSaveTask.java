package blizzard.development.bosses.tasks;

import blizzard.development.bosses.database.cache.ToolsCacheManager;
import blizzard.development.bosses.database.dao.ToolsDAO;
import org.bukkit.scheduler.BukkitRunnable;

import java.sql.SQLException;

public class ToolsSaveTask extends BukkitRunnable {
    private final ToolsDAO toolsDAO;

    public ToolsSaveTask(ToolsDAO toolsDAO) {
        this.toolsDAO = toolsDAO;
    }

    @Override
    public void run() {
        ToolsCacheManager.toolsCache.forEach((id, toolsData) -> {
            try {
                toolsDAO.updateToolsData(toolsData);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });
    }
}
