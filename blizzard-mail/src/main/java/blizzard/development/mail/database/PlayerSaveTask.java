package blizzard.development.mail.database;

import blizzard.development.mail.database.cache.PlayerCacheManager;
import blizzard.development.mail.database.dao.PlayerDao;
import org.bukkit.scheduler.BukkitRunnable;

import java.sql.SQLException;

public class PlayerSaveTask extends BukkitRunnable {
    private final PlayerDao playerDAO;

    public PlayerSaveTask(PlayerDao playerDAO) {
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
