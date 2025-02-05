package blizzard.development.mine.tasks;

import blizzard.development.mine.database.cache.BoosterCacheManager;
import blizzard.development.mine.database.cache.PlayerCacheManager;
import blizzard.development.mine.database.cache.ToolCacheManager;
import blizzard.development.mine.database.dao.BoosterDAO;
import blizzard.development.mine.database.dao.PlayerDAO;
import blizzard.development.mine.database.dao.ToolDAO;
import org.bukkit.scheduler.BukkitRunnable;

import java.sql.SQLException;

public class DatabaseSaveTask extends BukkitRunnable {
    private final ToolDAO toolDAO;
    private final PlayerDAO playerDAO;
    private final BoosterDAO boosterDAO;

    public DatabaseSaveTask(ToolDAO toolDAO, PlayerDAO playerDAO, BoosterDAO boosterDAO) {
        this.toolDAO = toolDAO;
        this.playerDAO = playerDAO;
        this.boosterDAO = boosterDAO;
    }

    @Override
    public void run() {
        PlayerCacheManager.getInstance().playerCache.forEach((player, playerData) -> {
            try {
                playerDAO.updatePlayerData(playerData);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });

        ToolCacheManager.getInstance().toolCache.forEach((player, toolData) -> {
            try {
                toolDAO.updateToolData(toolData);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });

        BoosterCacheManager.getInstance().boosterCache.forEach((player, boosterData) -> {
            try {
                boosterDAO.updateBoosterData(boosterData);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });
    }
}
