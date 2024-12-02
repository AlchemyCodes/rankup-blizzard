package blizzard.development.essentials.tasks;

import blizzard.development.essentials.database.cache.HomeCacheManager;
import blizzard.development.essentials.database.dao.HomeDAO;
import org.bukkit.scheduler.BukkitRunnable;

import java.sql.SQLException;

public class HomeSaveTask extends BukkitRunnable {

    private final HomeDAO homeDAO;

    public HomeSaveTask(HomeDAO homeDAO) {
        this.homeDAO = homeDAO;
    }

    @Override
    public void run() {
        HomeCacheManager.homeCache.forEach((player, homeData) -> {
            try {
                homeDAO.updateHomeData(homeData);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });
    }
}
