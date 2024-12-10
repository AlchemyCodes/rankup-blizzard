package blizzard.development.rankup.database.cache.method;

import blizzard.development.rankup.database.cache.PlayersCacheManager;
import blizzard.development.rankup.database.storage.PlayersData;
import org.bukkit.entity.Player;

public class PlayersCacheMethod {
    private static PlayersCacheMethod instance;

    private final PlayersCacheManager cache = PlayersCacheManager.getInstance();

    public String getRank(Player player) {
        PlayersData data =  cache.getPlayerData(player);
        return (data != null) ? data.getRank() : "null";
    }

    public void setRank(Player player, String rank) {
        PlayersData data = PlayersCacheManager.getInstance().getPlayerData(player);
        if (data != null) {
            data.setRank(rank);
            cache.cachePlayerData(player, data);
        }
    }

    public int getPrestige(Player player) {
        PlayersData data = PlayersCacheManager.getInstance().getPlayerData(player);
        return (data != null) ? data.getPrestige() : 0;
    }

    public void setPrestige(Player player, int prestige) {
        PlayersData data = PlayersCacheManager.getInstance().getPlayerData(player);
        if (data != null) {
            data.setPrestige(prestige);
            cache.cachePlayerData(player, data);
        }
    }


    public static PlayersCacheMethod getInstance() {
        if (instance == null) {
            instance = new PlayersCacheMethod();
        }
        return instance;
    }
}
