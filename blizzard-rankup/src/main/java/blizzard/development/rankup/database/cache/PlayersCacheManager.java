package blizzard.development.rankup.database.cache;

import blizzard.development.rankup.database.storage.PlayersData;
import org.bukkit.entity.Player;
import java.util.Collection;
import java.util.concurrent.ConcurrentHashMap;

public class PlayersCacheManager {
    public static PlayersCacheManager instance;

    public final ConcurrentHashMap<Player, PlayersData> playerCache = new ConcurrentHashMap<>();

    public void cachePlayerData(Player player, PlayersData playerData) {
        playerCache.put(player, playerData);
    }

    public PlayersData getPlayerData(Player player) {
        return playerCache.get(player);
    }

    public void removePlayerData(Player player) {
        playerCache.remove(player);
    }

//    public static void setNickname(Player player, String nickname) {
//        PlayersData data = playerCache.get(player);
//        if (data != null) {
//            data.setNickname(nickname);
//        } else {
//            data = new PlayersData(player.getUniqueId().toString(), nickname, getRankWithMinOrder(), 0);
//        }
//        playerCache.put(player, data);
//    }
//
//    public static String getRankWithMinOrder() {
//        ConfigurationSection ranks = PluginImpl.getInstance().Config.getConfig().getConfigurationSection("ranks");
//        String minRankName = null;
//
//        assert ranks != null;
//        for (String key : ranks.getKeys(false)) {
//            int order = ranks.getInt(key + ".order");
//
//            if (order == 1) {
//                minRankName = ranks.getString(key + ".name");
//            }
//        }
//
//        return minRankName;
//    }

    public Collection<PlayersData> getAllPlayersData() {
        return playerCache.values();
    }

    public static PlayersCacheManager getInstance() {
        if (instance == null) {
            instance = new PlayersCacheManager();
        }
        return instance;
    }
}
