package blizzard.development.spawners.tasks;

import blizzard.development.spawners.database.cache.PlayersCacheManager;
import blizzard.development.spawners.database.cache.SpawnersCacheManager;
import blizzard.development.spawners.database.dao.PlayersDAO;
import blizzard.development.spawners.database.dao.SpawnersDAO;
import org.bukkit.scheduler.BukkitRunnable;

import java.sql.SQLException;

public class SpawnersSaveTask extends BukkitRunnable {
    private final SpawnersDAO spawnersDAO;

    public SpawnersSaveTask(SpawnersDAO spawnersDAO) {
        this.spawnersDAO = spawnersDAO;
    }

    @Override
    public void run() {
        SpawnersCacheManager.getInstance().spawnersCache.forEach((id, spawnersData) -> {
            try {
                spawnersDAO.updateSpawnerData(spawnersData);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });
    }
}