package blizzard.development.fishing.database.cache;

import blizzard.development.fishing.database.storage.PlayersData;
import org.bukkit.entity.Player;

import java.util.Collection;
import java.util.concurrent.ConcurrentHashMap;

public class PlayersCacheManager {
    private static PlayersCacheManager instance;

    public final ConcurrentHashMap<Player, PlayersData> playerCache = new ConcurrentHashMap<>();

    public void cachePlayerData(Player player, PlayersData playerData) {
        playerCache.put(player, playerData);
    }

    public PlayersData getPlayerData(Player player) {
        return playerCache.get(player);
    }

    public void removePlayerData(Player player) {
        playerCache.remove(player);
    }

    public Collection<PlayersData> getAllPlayersData() {
        return playerCache.values();
    }

    public static PlayersCacheManager getInstance() {
        if (instance == null) {
            instance = new PlayersCacheManager();
        }
        return instance;
    }
}
