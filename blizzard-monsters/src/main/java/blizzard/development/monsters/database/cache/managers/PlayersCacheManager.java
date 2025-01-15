package blizzard.development.monsters.database.cache.managers;

import blizzard.development.monsters.database.storage.PlayersData;
import org.bukkit.entity.Player;

import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class PlayersCacheManager {
    private static PlayersCacheManager instance;

    public final ConcurrentHashMap<UUID, PlayersData> playersCache = new ConcurrentHashMap<>();

    public void cachePlayerData(Player player, PlayersData playerData) {
        playersCache.put(player.getUniqueId(), playerData);
    }
    public void cachePlayerData(UUID uuid, PlayersData playerData) {
        playersCache.put(uuid, playerData);
    }
    public PlayersData getPlayerData(Player player) {
        return playersCache.get(player.getUniqueId());
    }
    public void removePlayerData(Player player) {
        playersCache.remove(player.getUniqueId());
    }

    public static PlayersCacheManager getInstance() {
        if (instance == null) instance = new PlayersCacheManager();
        return instance;
    }
}
