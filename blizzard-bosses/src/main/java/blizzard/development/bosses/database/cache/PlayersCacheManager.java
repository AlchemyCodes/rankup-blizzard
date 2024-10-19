package blizzard.development.bosses.database.cache;

import blizzard.development.bosses.database.storage.PlayersData;
import org.bukkit.entity.Player;

import java.util.concurrent.ConcurrentHashMap;

public class PlayersCacheManager {
    public static final ConcurrentHashMap<Player, PlayersData> playersCache = new ConcurrentHashMap<>();

    public static void cachePlayerData(Player player, PlayersData playerData) {
        playersCache.put(player, playerData);
    }
    public static PlayersData getPlayerData(Player player) {
        return playersCache.get(player);
    }
    public static void removePlayerData(Player player) {
        playersCache.remove(player);
    }
}
