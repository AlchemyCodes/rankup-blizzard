package blizzard.development.excavation.database.cache;

import blizzard.development.excavation.database.dao.PlayerDAO;
import blizzard.development.excavation.database.storage.PlayerData;
import org.bukkit.entity.Player;

import java.util.concurrent.ConcurrentHashMap;

public class PlayerCacheManager {

    public static final ConcurrentHashMap<String, PlayerData> playerCache = new ConcurrentHashMap<>();

    public final PlayerDAO playerDAO = new PlayerDAO();

    public PlayerData getPlayerData(Player player) {
        return getPlayerDataByUUID(player.getUniqueId().toString());
    }

    public void cachePlayerData(Player player, PlayerData playerData) {
        playerCache.put(player.getUniqueId().toString(), playerData);
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

}
