package blizzard.development.vips.tasks;
import blizzard.development.vips.database.cache.PlayersCacheManager;
import blizzard.development.vips.database.dao.PlayersDAO;
import blizzard.development.vips.database.storage.PlayersData;
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
