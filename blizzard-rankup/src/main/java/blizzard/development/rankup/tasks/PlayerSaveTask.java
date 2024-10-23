package blizzard.development.rankup.tasks;

import blizzard.development.rankup.database.cache.PlayersCacheManager;
import blizzard.development.rankup.database.dao.PlayersDAO;
import blizzard.development.rankup.database.storage.PlayersData;
import java.sql.SQLException;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class PlayerSaveTask extends BukkitRunnable {
    private final PlayersDAO playersDAO;

    public PlayerSaveTask(PlayersDAO playersDAO) {
        this.playersDAO = playersDAO;
    }

    public void run() {
        PlayersCacheManager.playerCache.forEach((player, playersData) -> {
            try {
                this.playersDAO.updatePlayerData(playersData);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });
    }
}
