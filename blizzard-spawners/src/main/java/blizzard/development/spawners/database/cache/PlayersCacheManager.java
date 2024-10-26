package blizzard.development.spawners.database.cache;

import blizzard.development.spawners.database.storage.PlayersData;
import org.bukkit.entity.Player;

import java.util.concurrent.ConcurrentHashMap;

public class PlayersCacheManager {
    private static PlayersCacheManager instance;

    public final ConcurrentHashMap<Player, PlayersData> playersCache = new ConcurrentHashMap<>();

    public void cachePlayerData(Player player, PlayersData playerData) {
        playersCache.put(player, playerData);
    }
    public PlayersData getPlayerData(Player player) {
        return playersCache.get(player);
    }
    public void removePlayerData(Player player) {
        playersCache.remove(player);
    }

    public static PlayersCacheManager getInstance() {
        if (instance == null) instance = new PlayersCacheManager();
        return instance;
    }
}
