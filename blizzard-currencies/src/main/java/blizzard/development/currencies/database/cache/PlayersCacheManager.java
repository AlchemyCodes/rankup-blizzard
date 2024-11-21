package blizzard.development.currencies.database.cache;

import blizzard.development.currencies.database.storage.PlayersData;
import org.bukkit.entity.Player;

import java.util.concurrent.ConcurrentHashMap;

public class PlayersCacheManager {
    private static PlayersCacheManager instance;

    public final ConcurrentHashMap<String, PlayersData> playersCache = new ConcurrentHashMap<>();

    public void cachePlayerData(Player player, PlayersData playerData) {
        playersCache.put(player.getName(), playerData);
    }
    public void cachePlayerData(String player, PlayersData playerData) {
        playersCache.put(player, playerData);
    }
    public PlayersData getPlayerData(Player player) {
        return playersCache.get(player.getName());
    }
    public void removePlayerData(Player player) {
        playersCache.remove(player.getName());
    }

    public static PlayersCacheManager getInstance() {
        if (instance == null) instance = new PlayersCacheManager();
        return instance;
    }
}
