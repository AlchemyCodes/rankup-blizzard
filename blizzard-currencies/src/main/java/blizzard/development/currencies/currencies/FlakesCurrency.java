package blizzard.development.currencies.currencies;

import blizzard.development.currencies.database.cache.PlayersCacheManager;
import blizzard.development.currencies.database.storage.PlayersData;
import org.bukkit.entity.Player;

public class FlakesCurrency {
    private static FlakesCurrency instance;

    PlayersCacheManager cache = PlayersCacheManager.getInstance();

    public double getBalance(Player player) {
        PlayersData data = cache.getPlayerData(player);
        if (data == null) return 0.0;
        return data.getFlakes();
    }

    public boolean setBalance(Player player, Double balance) {
        PlayersData data = cache.getPlayerData(player);
        if (data == null) return false;
        data.setFlakes(balance);
        cache.cachePlayerData(player, data);
        return true;
    }

    public boolean addBalance(Player player, Double balance) {
        PlayersData data = cache.getPlayerData(player);
        if (data == null) return false;
        double initialBalance = data.getFlakes();
        data.setFlakes(initialBalance + balance);
        cache.cachePlayerData(player, data);
        return true;
    }

    public boolean removeBalance(Player player, Double balance) {
        PlayersData data = cache.getPlayerData(player);
        if (data == null) return false;
        double initialBalance = data.getFlakes();
        if (initialBalance < balance) return false;
        data.setFlakes(initialBalance - balance);
        cache.cachePlayerData(player, data);
        return true;
    }

    public static FlakesCurrency getInstance() {
        if (instance == null) instance = new FlakesCurrency();
        return instance;
    }
}
