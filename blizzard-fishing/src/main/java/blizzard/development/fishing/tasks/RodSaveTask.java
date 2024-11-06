package blizzard.development.fishing.tasks;

import blizzard.development.fishing.database.cache.PlayersCacheManager;
import blizzard.development.fishing.database.cache.RodsCacheManager;
import blizzard.development.fishing.database.dao.PlayersDAO;
import blizzard.development.fishing.database.dao.RodsDAO;
import org.bukkit.scheduler.BukkitRunnable;

import java.sql.SQLException;

public class RodSaveTask extends BukkitRunnable {
    private final RodsDAO rodsDAO;

    public RodSaveTask(RodsDAO rodsDAO) {
        this.rodsDAO = rodsDAO;
    }

    public void run() {
        RodsCacheManager.rodsCache.forEach((player, rodsData) -> {
            try {
                this.rodsDAO.updateRodsData(rodsData);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });
    }
}
