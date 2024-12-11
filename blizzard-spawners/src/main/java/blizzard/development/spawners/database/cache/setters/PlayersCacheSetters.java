package blizzard.development.spawners.database.cache.setters;

import blizzard.development.spawners.database.cache.managers.PlayersCacheManager;
import blizzard.development.spawners.database.storage.PlayersData;
import org.bukkit.entity.Player;

public class PlayersCacheSetters {
    private static PlayersCacheSetters instance;

    private final PlayersCacheManager cache = PlayersCacheManager.getInstance();

    public void addPurchasedSpawners(Player player, double amount) {
        PlayersData data = cache.getPlayerData(player);
        if (data != null) {
            data.setPurchasedSpawners(data.getPurchasedSpawners() + amount);
            cache.cachePlayerData(player, data);
        }
    }

    public void removePurchasedSpawners(Player player, double amount) {
        PlayersData data = cache.getPlayerData(player);
        if (data != null) {
            data.setPurchasedSpawners(data.getPurchasedSpawners() - amount);
            cache.cachePlayerData(player, data);
        }
    }

    public void addKilledMobs(Player player, double amount) {
        PlayersData data = cache.getPlayerData(player);
        if (data != null) {
            data.setKilledMobs(data.getKilledMobs() + amount);
            cache.cachePlayerData(player, data);
        }
    }

    public void removeKilledMobs(Player player, double amount) {
        PlayersData data = cache.getPlayerData(player);
        if (data != null) {
            data.setKilledMobs(data.getKilledMobs() - amount);
            cache.cachePlayerData(player, data);
        }
    }

    public static PlayersCacheSetters getInstance() {
        if (instance == null) instance = new PlayersCacheSetters();
        return instance;
    }
}
