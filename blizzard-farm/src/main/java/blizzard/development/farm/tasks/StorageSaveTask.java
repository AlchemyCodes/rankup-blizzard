package blizzard.development.farm.tasks;

import blizzard.development.farm.database.cache.StorageCacheManager;
import blizzard.development.farm.database.dao.StorageDAO;
import org.bukkit.scheduler.BukkitRunnable;

import java.sql.SQLException;

public class StorageSaveTask extends BukkitRunnable {

    private final StorageDAO storageDAO;

    public StorageSaveTask(StorageDAO storageDAO) {
        this.storageDAO = storageDAO;
    }

    @Override
    public void run() {
        StorageCacheManager.getInstance().plantations.forEach((storage, storageData) -> {
            try {
                storageDAO.updateStorageData(storageData);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });
    }
}
