package blizzard.development.plantations.database.cache;

import blizzard.development.plantations.database.dao.PlayerDAO;
import blizzard.development.plantations.database.storage.PlayerData;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

public class PlayerCacheManager {

    public static final ConcurrentHashMap<String, PlayerData> playerCache = new ConcurrentHashMap<>();

    public final PlayerDAO playerDAO = new PlayerDAO();

    public PlayerData getPlayerData(Player player) {
        return getPlayerDataByUUID(player.getUniqueId().toString());
    }

    public static void cachePlayerData(String player, PlayerData playerData) {
        playerCache.put(player, playerData);
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

    public List<PlayerData> getTopPlayers() {
        return playerCache.values().stream()
                .sorted((p1, p2) -> Integer.compare(p2.getPlantations(), p1.getPlantations()))
                .limit(10)
                .collect(Collectors.toList());
    }
}
