package blizzard.development.farm.database.cache;

import blizzard.development.farm.database.storage.StorageData;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class StorageCacheManager {

    private static final StorageCacheManager instance = new StorageCacheManager();
    public static StorageCacheManager getInstance() {
        return instance;
    }

    public final Map<UUID, StorageData> plantations = new HashMap<>();

    public void cacheStorageData(Player player, StorageData playerData) {
        plantations.put(player.getUniqueId(), playerData);
    }
    public void cacheStorageData(UUID uuid, StorageData playerData) {
        plantations.put(uuid, playerData);
    }
    public StorageData getStorageData(Player player) {
        return plantations.get(player.getUniqueId());
    }
    public void removeStorageData(Player player) {
        plantations.remove(player.getUniqueId());
    }

}
