package blizzard.development.farm.listeners.commons;

import blizzard.development.farm.database.cache.StorageCacheManager;
import blizzard.development.farm.database.dao.StorageDAO;
import blizzard.development.farm.database.storage.StorageData;
import blizzard.development.farm.farm.adapters.ToolAdapter;
import blizzard.development.farm.listeners.storage.CactusDropListener;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import java.sql.SQLException;

public class PlayerListener implements Listener {

    private final StorageDAO storageDAO;

    public PlayerListener(StorageDAO storageDAO) {
        this.storageDAO = storageDAO;
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();

        StorageData storageData = storageDAO.findStorageData(player.getUniqueId().toString());

        if (storageData == null) {
            storageData = new StorageData(
                player.getUniqueId().toString(),
                player.getName(),
                0,
                0,
                0,
                0,
                0
            );
            try {
                storageDAO.createStorageData(storageData);
            } catch (SQLException exception) {
                exception.printStackTrace();
            }

            ToolAdapter.getInstance()
                .giveTool(
                    player
                );
        }

        StorageCacheManager.getInstance().cacheStorageData(
            player.getUniqueId(),
            storageData
        );
    }

}
