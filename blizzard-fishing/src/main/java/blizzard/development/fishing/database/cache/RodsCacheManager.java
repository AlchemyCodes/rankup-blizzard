package blizzard.development.fishing.database.cache;

import blizzard.development.fishing.database.storage.RodsData;
import org.bukkit.entity.Player;

import java.util.Collection;
import java.util.concurrent.ConcurrentHashMap;

public class RodsCacheManager {
    private static RodsCacheManager instance;

    public final ConcurrentHashMap<Player, RodsData> rodsCache = new ConcurrentHashMap<>();

    public void cachePlayerData(Player player, RodsData rodsData) {
        rodsCache.put(player, rodsData);
    }

    public RodsData getPlayerData(Player player) {
        return rodsCache.get(player);
    }

    public void removePlayerData(Player player) {
        rodsCache.remove(player);
    }

    public Collection<RodsData> getAllRodsData() {
        return rodsCache.values();
    }

    public static RodsCacheManager getInstance() {
        if (instance == null) {
            instance = new RodsCacheManager();
        }
        return instance;
    }
}
