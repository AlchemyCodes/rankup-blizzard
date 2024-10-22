package blizzard.development.excavation.tasks;

import blizzard.development.excavation.database.cache.ExcavatorCacheManager;
import blizzard.development.excavation.database.dao.ExcavatorDAO;
import org.bukkit.scheduler.BukkitRunnable;

import java.sql.SQLException;

public class ExcavatorSaveTask extends BukkitRunnable {

    private final ExcavatorDAO excavatorDAO;

    public ExcavatorSaveTask(ExcavatorDAO excavatorDAO) {
        this.excavatorDAO = excavatorDAO;
    }

    @Override
    public void run() {
        ExcavatorCacheManager.excavatorCache.forEach((nickname, excavatorData) -> {

            try {
                excavatorDAO.updateExcavatorData(excavatorData);
            } catch (SQLException exception) {
                throw new RuntimeException(exception);
            }
        });
    }
}
