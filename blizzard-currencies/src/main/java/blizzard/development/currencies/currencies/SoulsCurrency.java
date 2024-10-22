package blizzard.development.currencies.currencies;

import blizzard.development.currencies.database.cache.PlayersCacheManager;
import blizzard.development.currencies.database.storage.PlayersData;
import org.bukkit.entity.Player;

public class SoulsCurrency {

    private static SoulsCurrency instance;

    PlayersCacheManager cache = PlayersCacheManager.getInstance();

    public Double getBalance(Player player) {
        PlayersData data = cache.getPlayerData(player);
        if (data == null) return 0.0;
        return data.getSouls();
    }

    public void setBalance(Player player, Double balance) {
        PlayersData data = cache.getPlayerData(player);
        if (data == null) return;
        data.setSouls(balance);
        cache.cachePlayerData(player, data);
    }

    public void addBalance(Player player, Double balance) {
        PlayersData data = cache.getPlayerData(player);
        if (data == null) return;
        double initialBalance = data.getSouls();
        data.setSouls(initialBalance + balance);
        cache.cachePlayerData(player, data);
    }

    public void removeBalance(Player player, Double balance) {
        PlayersData data = cache.getPlayerData(player);
        if (data == null) return;
        double initialBalance = data.getSouls();
        data.setSouls(initialBalance - balance);
        cache.cachePlayerData(player, data);
    }

    public static SoulsCurrency getInstance() {
        if (instance == null) instance = new SoulsCurrency();
        return instance;
    }
}
