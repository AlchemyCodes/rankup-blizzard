package blizzard.development.vips.database.cache;

import blizzard.development.vips.database.storage.PlayersData;

import java.util.Collection;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

public class PlayersCacheManager {
    private static PlayersCacheManager instance;

    public final ConcurrentHashMap<String, PlayersData> vipCache = new ConcurrentHashMap<>();

    public void cachePlayerData(String vipId, PlayersData playerData) {
        vipCache.put(vipId, playerData);
    }

    public PlayersData getPlayerData(String vipId) {
        return vipCache.get(vipId);
    }

    public void removePlayerData(String vipId) {
        vipCache.remove(vipId);
    }

    public Collection<PlayersData> getAllPlayersData() {
        return vipCache.values();
    }

    public static PlayersCacheManager getInstance() {
        if (instance == null) {
            instance = new PlayersCacheManager();
        }
        return instance;
    }

    public Collection<String> getAllVipIds() {
        return vipCache.values().stream()
                .map(PlayersData::getVipId)
                .filter(vipId -> vipId != null && !vipId.isEmpty())
                .collect(Collectors.toList());
    }
}
