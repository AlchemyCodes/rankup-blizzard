package blizzard.development.spawners.database.cache.setters;

import blizzard.development.spawners.database.cache.managers.SpawnersCacheManager;
import blizzard.development.spawners.database.storage.SpawnersData;

public class SpawnersCacheSetters {
    private static SpawnersCacheSetters instance;

    private final SpawnersCacheManager cache = SpawnersCacheManager.getInstance();

    public void setMobAmout(String id, double amount) {
        SpawnersData data = cache.getSpawnerData(id);
        if (data != null) {
            data.setMob_amount(amount);
            cache.cacheSpawnerData(id, data);
        }
    }

    public static SpawnersCacheSetters getInstance() {
        if (instance == null) instance = new SpawnersCacheSetters();
        return instance;
    }
}
