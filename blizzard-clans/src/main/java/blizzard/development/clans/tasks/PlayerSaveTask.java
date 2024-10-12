package blizzard.development.clans.tasks;

import org.bukkit.scheduler.BukkitRunnable;
import blizzard.development.clans.database.cache.PlayersCacheManager;
import blizzard.development.clans.database.dao.PlayersDAO;

import java.sql.SQLException;

public class PlayerSaveTask extends BukkitRunnable {
    private final PlayersDAO playersDAO;

    public PlayerSaveTask(PlayersDAO playersDAO) {
        this.playersDAO = playersDAO;
    }

    @Override
    public void run() {
        PlayersCacheManager.playersCache.forEach((player, playersData) -> {
            try {
                playersDAO.updatePlayerData(playersData);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });
    }
}