package blizzard.development.farm.listeners.commons;

import blizzard.development.farm.database.StorageData;
import blizzard.development.farm.listeners.storage.CactusDropListener;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerListener implements Listener {

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();

        CactusDropListener cactusDropListener = new CactusDropListener();

        StorageData storageData = new StorageData(
            player.getUniqueId().toString(),
            player.getName(),
            "aa",
            0,
            0,
            0,
            0,
            0
        );

        cactusDropListener.cacheStorageData(
            player.getUniqueId(),
            storageData
        );
    }

}
