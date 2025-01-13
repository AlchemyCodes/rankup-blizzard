package blizzard.development.monsters.tasks.tools;

import blizzard.development.monsters.database.cache.managers.ToolsCacheManager;
import blizzard.development.monsters.database.dao.ToolsDAO;
import org.bukkit.scheduler.BukkitRunnable;

import java.sql.SQLException;

public class ToolsSaveTask extends BukkitRunnable {
    private final ToolsDAO toolsDAO;

    public ToolsSaveTask(ToolsDAO toolsDAO) {
        this.toolsDAO = toolsDAO;
    }

    @Override
    public void run() {
        ToolsCacheManager.getInstance().toolsCache.forEach((player, toolsData) -> {
            try {
                toolsDAO.updateToolData(toolsData);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });
    }
}