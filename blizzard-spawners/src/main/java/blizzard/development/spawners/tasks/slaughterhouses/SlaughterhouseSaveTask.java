package blizzard.development.spawners.tasks.slaughterhouses;

import blizzard.development.spawners.database.cache.managers.SlaughterhouseCacheManager;
import blizzard.development.spawners.database.cache.managers.SpawnersCacheManager;
import blizzard.development.spawners.database.dao.SlaughterhouseDAO;
import blizzard.development.spawners.database.dao.SpawnersDAO;
import org.bukkit.scheduler.BukkitRunnable;

import java.sql.SQLException;

public class SlaughterhouseSaveTask extends BukkitRunnable {
    private final SlaughterhouseDAO slaughterhouseDAO;

    public SlaughterhouseSaveTask(SlaughterhouseDAO slaughterhouseDAO) {
        this.slaughterhouseDAO = slaughterhouseDAO;
    }

    @Override
    public void run() {
        SlaughterhouseCacheManager.getInstance().slaughterhouseCache.forEach((id, slaughterhouseData) -> {
            try {
                slaughterhouseDAO.updateSlaughterhouseData(slaughterhouseData);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });
    }
}