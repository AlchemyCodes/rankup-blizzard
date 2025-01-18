package blizzard.development.mine.tasks;

import blizzard.development.mine.database.cache.PlayerCacheManager;
import blizzard.development.mine.database.cache.ToolCacheManager;
import blizzard.development.mine.database.dao.ToolDAO;
import org.bukkit.scheduler.BukkitRunnable;

import java.sql.SQLException;

public class ToolSaveTask extends BukkitRunnable {
    private final ToolDAO toolDAO;

    public ToolSaveTask(ToolDAO toolDAO) {
        this.toolDAO = toolDAO;
    }

    @Override
    public void run() {
        ToolCacheManager.getInstance().toolCache.forEach((player, toolData) -> {
            try {
                toolDAO.updateToolData(toolData);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });
    }
}
