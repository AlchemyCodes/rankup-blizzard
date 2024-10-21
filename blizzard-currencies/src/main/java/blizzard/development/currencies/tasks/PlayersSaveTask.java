package blizzard.development.currencies.tasks;

import blizzard.development.currencies.database.cache.PlayersCacheManager;
import blizzard.development.currencies.database.dao.PlayersDAO;
import org.bukkit.scheduler.BukkitRunnable;

import java.sql.SQLException;

public class PlayersSaveTask extends BukkitRunnable {
    private final PlayersDAO playersDAO;

    public PlayersSaveTask(PlayersDAO playersDAO) {
        this.playersDAO = playersDAO;
    }

    @Override
    public void run() {
        PlayersCacheManager.getInstance().playersCache.forEach((player, playersData) -> {
            try {
                playersDAO.updatePlayerData(playersData);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });
    }
}