package blizzard.development.plantations.database.cache;

import blizzard.development.plantations.database.dao.PlayerDAO;
import blizzard.development.plantations.database.storage.PlayerData;
import org.bukkit.entity.Player;

import java.util.*;
import java.util.stream.Collectors;

public class PlayerCacheManager {

    private static final PlayerCacheManager instance = new PlayerCacheManager();
    public final Map<String, PlayerData> playerCache = new HashMap<>();
    public final PlayerDAO playerDAO = new PlayerDAO();

    public static PlayerCacheManager getInstance() {
        return instance;
    }

    public PlayerData getPlayerData(Player player) {
        return getPlayerDataByUUID(player.getUniqueId().toString());
    }

    public PlayerData getPlayerDataByUUID(String playerUUID) {
        PlayerData playerData = playerCache.get(playerUUID);

        if (playerData == null) {
            playerData = playerDAO.findPlayerData(playerUUID);
            if (playerData != null) {
                playerCache.put(playerUUID, playerData);
            }
        }

        return playerData;
    }

    public void cachePlayerData(String player, PlayerData playerData) {
        playerCache.put(player, playerData);
    }

    public void removePlayerData(String playerId) {
        playerCache.remove(playerId);
    }

    public void clearCache() {
        playerCache.clear();
    }

    public List<PlayerData> getTopPlayers(int topCount) {
        return playerCache.values().stream()
                .sorted((p1, p2) -> Integer.compare(p2.getPlantations(), p1.getPlantations()))
                .limit(topCount)
                .collect(Collectors.toList());
    }
}
