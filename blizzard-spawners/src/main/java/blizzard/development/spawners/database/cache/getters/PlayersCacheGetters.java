package blizzard.development.spawners.database.cache.getters;


import blizzard.development.spawners.database.cache.managers.PlayersCacheManager;
import blizzard.development.spawners.database.cache.managers.SpawnersCacheManager;
import blizzard.development.spawners.database.storage.PlayersData;
import blizzard.development.spawners.database.storage.SpawnersData;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.stream.Collectors;

public class PlayersCacheGetters {
    private static PlayersCacheGetters instance;

    private final PlayersCacheManager cache = PlayersCacheManager.getInstance();

    public double getPlacedSpawners(Player player) {
        PlayersData data = cache.getPlayerData(player);
        if (data != null) {
            return data.getPlacedSpawners();
        }
        return 0.0;
    }

    public double getPurchasedSpawners(Player player) {
        PlayersData data = cache.getPlayerData(player);
        if (data != null) {
            return data.getPurchasedSpawners();
        }
        return 0.0;
    }

    public List<PlayersData> getTopPlacedSpawners() {
        return PlayersCacheManager.getInstance().playersCache.values().stream()
                .sorted((p1, p2) -> Double.compare(p2.getPlacedSpawners(), p1.getPlacedSpawners()))
                .limit(10)
                .collect(Collectors.toList());
    }

    public List<PlayersData> getTopPurchasedSpawners() {
        return PlayersCacheManager.getInstance().playersCache.values().stream()
                .sorted((p1, p2) -> Double.compare(p2.getPurchasedSpawners(), p1.getPurchasedSpawners()))
                .limit(10)
                .collect(Collectors.toList());
    }

    public static PlayersCacheGetters getInstance() {
        if (instance == null) instance = new PlayersCacheGetters();
        return instance;
    }
}
