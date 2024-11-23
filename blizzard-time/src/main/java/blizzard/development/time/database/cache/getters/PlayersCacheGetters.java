package blizzard.development.time.database.cache.getters;

import blizzard.development.time.database.cache.managers.PlayersCacheManager;
import blizzard.development.time.database.storage.PlayersData;
import blizzard.development.time.utils.TimeConverter;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.stream.Collectors;

public class PlayersCacheGetters {
    private static PlayersCacheGetters instance;

    final private PlayersCacheManager cache = PlayersCacheManager.getInstance();

    public long getPlayTime(Player player) {
        PlayersData data = cache.getPlayerData(player);
        if (data != null) {
            return data.getPlayTime();
        }
        return 0;
    }

    public List<PlayersData> getTopPlayers() {
        return PlayersCacheManager.getInstance().playersCache.values().stream()
                .sorted((p1, p2) -> Double.compare(p2.getPlayTime(), p1.getPlayTime()))
                .limit(10)
                .collect(Collectors.toList());
    }

    public static PlayersCacheGetters getInstance() {
        if (instance == null) instance = new PlayersCacheGetters();
        return instance;
    }
}
