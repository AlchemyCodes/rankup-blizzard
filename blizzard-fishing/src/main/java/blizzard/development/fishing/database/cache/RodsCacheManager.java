package blizzard.development.fishing.database.cache;

import blizzard.development.fishing.database.dao.PlayersDAO;
import blizzard.development.fishing.database.dao.RodsDAO;
import blizzard.development.fishing.database.storage.PlayersData;
import blizzard.development.fishing.database.storage.RodsData;
import org.bukkit.entity.Player;

import java.util.Collection;
import java.util.concurrent.ConcurrentHashMap;

public class RodsCacheManager {
    public static final ConcurrentHashMap<Player, RodsData> rodsCache = new ConcurrentHashMap<>();
    public static RodsDAO rodsDAO;

    public static void cachePlayerData(Player player, RodsData rodsData) {
        rodsCache.put(player, rodsData);
    }

    public static RodsData getPlayerData(Player player) {
        return rodsCache.get(player);
    }

    public static void removePlayerData(Player player) {
        rodsCache.remove(player);
    }

    public static Collection<RodsData> getAllRodsData() {
        return rodsCache.values();
    }
}
