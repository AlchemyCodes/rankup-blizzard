package blizzard.development.fishing.tasks;

import blizzard.development.fishing.database.cache.RodsCacheManager;
import blizzard.development.fishing.database.dao.RodsDAO;
import org.bukkit.scheduler.BukkitRunnable;

import java.sql.SQLException;

public class RodSaveTask extends BukkitRunnable {
    private final RodsDAO rodsDAO;

    public RodSaveTask(RodsDAO rodsDAO) {
        this.rodsDAO = rodsDAO;
    }

    @Override
    public void run() {
        RodsCacheManager.getInstance().rodsCache.forEach((player, rodsData) -> {
            try {
                rodsDAO.updateRodsData(rodsData);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });
    }
}
