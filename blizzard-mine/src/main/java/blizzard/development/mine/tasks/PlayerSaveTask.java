package blizzard.development.mine.tasks;

import blizzard.development.mine.database.cache.PlayerCacheManager;
import blizzard.development.mine.database.dao.PlayerDAO;
import org.bukkit.scheduler.BukkitRunnable;

import java.sql.SQLException;

public class PlayerSaveTask extends BukkitRunnable {
    private final PlayerDAO playerDAO;

    public PlayerSaveTask(PlayerDAO playerDAO) {
        this.playerDAO = playerDAO;
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
    }
}
