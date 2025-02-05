package blizzard.development.mine.database.cache;

import blizzard.development.mine.database.storage.BoosterData;
import org.bukkit.entity.Player;

import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class BoosterCacheManager {

    private static final BoosterCacheManager instance = new BoosterCacheManager();

    public static BoosterCacheManager getInstance() {
        return instance;
    }

    public final ConcurrentHashMap<UUID, BoosterData> boosterCache = new ConcurrentHashMap<>();

    public void cacheBoosterData(Player player, BoosterData boosterData) {
        boosterCache.put(player.getUniqueId(), boosterData);
    }

    public void cacheBoosterData(UUID uuid, BoosterData boosterData) {
        boosterCache.put(uuid, boosterData);
    }

    public BoosterData getBoosterData(UUID uuid) {
        return boosterCache.get(uuid);
    }

    public void removeBoosterData(Player player) {
        boosterCache.remove(player.getUniqueId());
    }
}
