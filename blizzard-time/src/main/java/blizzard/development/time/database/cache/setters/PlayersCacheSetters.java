package blizzard.development.time.database.cache.setters;

import blizzard.development.time.database.cache.managers.PlayersCacheManager;
import blizzard.development.time.database.storage.PlayersData;
import org.bukkit.entity.Player;

public class PlayersCacheSetters {
    private static PlayersCacheSetters instance;

    final private PlayersCacheManager cache = PlayersCacheManager.getInstance();

    public void setPlayTime(Player player, long time) {
        PlayersData data = cache.getPlayerData(player);
        if (data != null) {
            long playTime = data.getPlayTime();
            data.setPlayTime(time);
            cache.cachePlayerData(player, data);
        }
    }

    public void addPlayTime(Player player, long time) {
        PlayersData data = cache.getPlayerData(player);
        if (data != null) {
            long playTime = data.getPlayTime();
            data.setPlayTime(playTime + time);
            cache.cachePlayerData(player, data);
        }
    }

    public void removePlayTime(Player player, long time) {
        PlayersData data = cache.getPlayerData(player);
        if (data != null) {
            long playTime = data.getPlayTime();
            data.setPlayTime(playTime - time);
            cache.cachePlayerData(player, data);
        }
    }

    public static PlayersCacheSetters getInstance() {
        if (instance == null) instance = new PlayersCacheSetters();
        return instance;
    }
}
