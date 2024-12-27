package blizzard.development.vips.database.cache;

import blizzard.development.vips.database.storage.PlayersData;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

public class PlayersCacheManager {
    private static PlayersCacheManager instance;

    public final ConcurrentHashMap<String, PlayersData> playerCache = new ConcurrentHashMap<>();

    public void cachePlayerData(String player, PlayersData playerData) {
        playerCache.put(player, playerData);
    }

    public PlayersData getPlayerData(Player player) {
        return playerCache.get(player);
    }

    public PlayersData getPlayerDataByVipId(String vipId) {
        for (PlayersData playerData : PlayersCacheManager.getInstance().getAllPlayersData()) {
            if (playerData.getVipId().equals(vipId)) {
                return playerData;
            }
        }
        return null;
    }

    public PlayersData getPlayerDataByPlayerName(String playerName) {
        for (PlayersData playerData : PlayersCacheManager.getInstance().getAllPlayersData()) {
            if (playerData.getNickname().equals(playerName)) {
                return playerData;
            }
        }
        return null;
    }

    public void removePlayerData(Player player) {
        playerCache.remove(player);
    }

    public Collection<PlayersData> getAllPlayersData() {
        return playerCache.values();
    }

    public static PlayersCacheManager getInstance() {
        if (instance == null) {
            instance = new PlayersCacheManager();
        }
        return instance;
    }

    public Collection<String> getAllVipIds() {
        return playerCache.values().stream()
                .map(PlayersData::getVipId)
                .filter(vipId -> vipId != null && !vipId.isEmpty())
                .collect(Collectors.toList());
    }

    public List<PlayersData> getAllPlayerVips(String nickname) {
        List<PlayersData> result = new ArrayList<>();
        playerCache.forEach((id, data) -> {
            if (data.getNickname().equalsIgnoreCase(nickname)) {
                result.add(data);
            }
        });
        return result;
    }
}
