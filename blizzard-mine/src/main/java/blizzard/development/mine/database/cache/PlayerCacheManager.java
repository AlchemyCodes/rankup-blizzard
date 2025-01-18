package blizzard.development.mine.database.cache;

import blizzard.development.mine.database.storage.PlayerData;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

public class PlayerCacheManager {

    private static final PlayerCacheManager instance = new PlayerCacheManager();

    public static PlayerCacheManager getInstance() {
        return instance;
    }

    public final ConcurrentHashMap<UUID, PlayerData> playerCache = new ConcurrentHashMap<>();

    public void cachePlayerData(Player player, PlayerData playerData) {
        playerCache.put(player.getUniqueId(), playerData);
    }
    public void cachePlayerData(UUID uuid, PlayerData playerData) {
        playerCache.put(uuid, playerData);
    }
    public PlayerData getPlayerData(Player player) {
        return playerCache.get(player.getUniqueId());
    }
    public void removePlayerData(Player player) {
        playerCache.remove(player.getUniqueId());
    }
}
