package blizzard.development.essentials.database.cache;

import blizzard.development.essentials.database.dao.HomeDAO;
import blizzard.development.essentials.database.storage.HomeData;
import org.bukkit.entity.Player;

import java.util.concurrent.ConcurrentHashMap;

public class HomeCacheManager {

    private static HomeCacheManager instance;

    public static final ConcurrentHashMap<String, HomeData> homeCache = new ConcurrentHashMap<>();

    public final HomeDAO homeDAO = new HomeDAO();

    public HomeData getPlayerData(Player player) {
        return getPlayerDataByUUID(player.getUniqueId().toString());
    }

    public static void cachePlayerData(String player, HomeData playerData) {
        homeCache.put(player, playerData);
    }

    public HomeData getPlayerDataByUUID(String playerUUID) {
        HomeData playerData = homeCache.get(playerUUID);

        if (playerData == null) {
            playerData = homeDAO.findHomeData(playerUUID);
            if (playerData != null) {
                homeCache.put(playerUUID, playerData);
            }
        }

        return playerData;
    }

    public static HomeCacheManager getInstance() {
        if (instance == null) {
            instance = new HomeCacheManager();
        }
        return instance;
    }

}
