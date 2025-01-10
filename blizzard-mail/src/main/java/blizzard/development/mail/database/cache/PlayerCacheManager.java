package blizzard.development.mail.database.cache;

import blizzard.development.mail.database.storage.PlayerData;

import java.util.concurrent.ConcurrentHashMap;

public class PlayerCacheManager {
    private static PlayerCacheManager instance;

    public final ConcurrentHashMap<String, PlayerData> playerCache = new ConcurrentHashMap<>();

    public void cachePlayerData(String player, PlayerData playerData) {
        playerCache.put(player, playerData);
    }

    public PlayerData getPlayerData(String player) {
        return playerCache.get(player);
    }

    public void removePlayerData(String player) {
        playerCache.remove(player);
    }

    public static PlayerCacheManager getInstance() {
        if (instance == null) instance = new PlayerCacheManager();
        return instance;
    }
}
