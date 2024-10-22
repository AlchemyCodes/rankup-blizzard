package blizzard.development.currencies.currencies;

import blizzard.development.currencies.database.cache.PlayersCacheManager;
import blizzard.development.currencies.database.storage.PlayersData;
import org.bukkit.entity.Player;

public class FossilsCurrency {
    private static FossilsCurrency instance;

    PlayersCacheManager cache = PlayersCacheManager.getInstance();

    public Double getBalance(Player player) {
        PlayersData data = cache.getPlayerData(player);
        if (data == null) return 0.0;
        return data.getFossils();
    }

    public boolean setBalance(Player player, Double balance) {
        PlayersData data = cache.getPlayerData(player);
        if (data == null) return false;
        data.setFossils(balance);
        cache.cachePlayerData(player, data);
        return true;
    }

    public boolean addBalance(Player player, Double balance) {
        PlayersData data = cache.getPlayerData(player);
        if (data == null) return false;
        double initialBalance = data.getFossils();
        data.setFossils(initialBalance + balance);
        cache.cachePlayerData(player, data);
        return true;
    }

    public boolean removeBalance(Player player, Double balance) {
        PlayersData data = cache.getPlayerData(player);
        if (data == null) return false;
        double initialBalance = data.getFossils();
        if (initialBalance < balance) return false;
        data.setFossils(initialBalance - balance);
        cache.cachePlayerData(player, data);
        return true;
    }

    public static FossilsCurrency getInstance() {
        if (instance == null) instance = new FossilsCurrency();
        return instance;
    }
}
