package blizzard.development.currencies.currencies;

import blizzard.development.currencies.database.cache.PlayersCacheManager;
import blizzard.development.currencies.database.storage.PlayersData;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.stream.Collectors;

public class SoulsCurrency {
    private static SoulsCurrency instance;

    PlayersCacheManager cache = PlayersCacheManager.getInstance();

    public double getBalance(Player player) {
        PlayersData data = cache.getPlayerData(player);
        if (data == null) return 0.0;
        return data.getSouls();
    }

    public boolean setBalance(Player player, Double balance) {
        PlayersData data = cache.getPlayerData(player);
        if (data == null) return false;
        data.setSouls(balance);
        cache.cachePlayerData(player, data);
        return true;
    }

    public boolean addBalance(Player player, Double balance) {
        PlayersData data = cache.getPlayerData(player);
        if (data == null) return false;
        double initialBalance = data.getSouls();
        data.setSouls(initialBalance + balance);
        cache.cachePlayerData(player, data);
        return true;
    }

    public boolean removeBalance(Player player, Double balance) {
        PlayersData data = cache.getPlayerData(player);
        if (data == null) return false;
        double initialBalance = data.getSouls();
        if (initialBalance < balance) return false;
        data.setSouls(initialBalance - balance);
        cache.cachePlayerData(player, data);
        return true;
    }

    public List<PlayersData> getTopPlayers() {
        return PlayersCacheManager.getInstance().playersCache.values().stream()
                .sorted((p1, p2) -> Double.compare(p2.getSouls(), p1.getSouls()))
                .limit(10)
                .collect(Collectors.toList());
    }

    public static SoulsCurrency getInstance() {
        if (instance == null) instance = new SoulsCurrency();
        return instance;
    }
}
