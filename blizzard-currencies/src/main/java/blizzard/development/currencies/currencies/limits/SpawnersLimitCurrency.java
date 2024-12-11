package blizzard.development.currencies.currencies.limits;

import blizzard.development.currencies.database.cache.PlayersCacheManager;
import blizzard.development.currencies.database.storage.PlayersData;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.stream.Collectors;

public class SpawnersLimitCurrency {
    private static SpawnersLimitCurrency instance;

    PlayersCacheManager cache = PlayersCacheManager.getInstance();

    public double getBalance(Player player) {
        PlayersData data = cache.getPlayerData(player);
        if (data == null) return 0.0;
        return data.getSpawnersLimit();
    }

    public boolean setBalance(Player player, Double balance) {
        PlayersData data = cache.getPlayerData(player);
        if (data == null) return false;
        data.setSpawnersLimit(balance);
        cache.cachePlayerData(player, data);
        return true;
    }

    public boolean addBalance(Player player, Double balance) {
        PlayersData data = cache.getPlayerData(player);
        if (data == null) return false;
        double initialBalance = data.getSpawnersLimit();
        data.setSpawnersLimit(initialBalance + balance);
        cache.cachePlayerData(player, data);
        return true;
    }

    public boolean removeBalance(Player player, Double balance) {
        PlayersData data = cache.getPlayerData(player);
        if (data == null) return false;
        double initialBalance = data.getSpawnersLimit();
        if (initialBalance < balance) return false;
        data.setSpawnersLimit(initialBalance - balance);
        cache.cachePlayerData(player, data);
        return true;
    }

    public List<PlayersData> getTopPlayers() {
        return PlayersCacheManager.getInstance().playersCache.values().stream()
                .sorted((p1, p2) -> Double.compare(p2.getSpawnersLimit(), p1.getSpawnersLimit()))
                .limit(10)
                .collect(Collectors.toList());
    }

    public static SpawnersLimitCurrency getInstance() {
        if (instance == null) instance = new SpawnersLimitCurrency();
        return instance;
    }
}

