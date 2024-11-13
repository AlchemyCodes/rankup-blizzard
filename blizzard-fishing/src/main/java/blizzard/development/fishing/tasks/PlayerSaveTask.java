package blizzard.development.fishing.tasks;

import blizzard.development.fishing.database.cache.PlayersCacheManager;
import blizzard.development.fishing.database.dao.PlayersDAO;
import org.bukkit.scheduler.BukkitRunnable;

import java.sql.SQLException;

public class PlayerSaveTask extends BukkitRunnable {
    private final PlayersDAO playersDAO;

    public PlayerSaveTask(PlayersDAO playersDAO) {
        this.playersDAO = playersDAO;
    }

    @Override
    public void run() {
        PlayersCacheManager.getInstance().playerCache.forEach((player, playersData) -> {
            try {
                playersDAO.updatePlayerData(playersData);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });
    }
}
