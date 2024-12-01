package blizzard.development.time.api;

import blizzard.development.time.database.cache.managers.PlayersCacheManager;
import blizzard.development.time.database.storage.PlayersData;

import java.util.List;
import java.util.stream.Collectors;

public class TimeAPI {
    public List<PlayersData> getTopPlayers() {
        return PlayersCacheManager.getInstance().playersCache.values().stream()
                .sorted((p1, p2) -> Double.compare(p2.getPlayTime(), p1.getPlayTime()))
                .limit(10)
                .collect(Collectors.toList());
    }
}
