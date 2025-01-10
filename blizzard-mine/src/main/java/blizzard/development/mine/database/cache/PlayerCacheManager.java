package blizzard.development.mine.database.cache;

import blizzard.development.mine.database.dao.PlayerDAO;
import blizzard.development.mine.database.storage.PlayerData;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

public class PlayerCacheManager {

    private static final PlayerCacheManager instance = new PlayerCacheManager();
    public final Map<String, PlayerData> playerCache = new ConcurrentHashMap<>();
    public final PlayerDAO playerDAO = new PlayerDAO();

    public static PlayerCacheManager getInstance() {
        return instance;
    }

    public PlayerData getPlayerData(Player player) {
        return getPlayerDataByUUID(player.getUniqueId().toString());
    }

    public PlayerData getPlayerDataByUUID(String playerUUID) {
        return playerCache.computeIfAbsent(playerUUID, playerDAO::findPlayerData);
    }

    public void cachePlayerData(String playerUUID, PlayerData playerData) {
        playerCache.put(playerUUID, playerData);
    }

    public void removePlayerData(String playerUUID) {
        playerCache.remove(playerUUID);
    }

    public void clearCache() {
        playerCache.clear();
    }

    public List<PlayerData> getTopBlocks(int topCount) {
        return playerCache.values().stream()
            .sorted((p1, p2) -> Integer.compare(p2.getBlocks(), p1.getBlocks()))
            .limit(topCount)
            .collect(Collectors.toList());
    }
}
