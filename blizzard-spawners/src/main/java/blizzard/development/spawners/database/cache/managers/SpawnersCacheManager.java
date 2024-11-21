package blizzard.development.spawners.database.cache.managers;

import blizzard.development.spawners.database.storage.SpawnersData;

import java.util.concurrent.ConcurrentHashMap;

public class SpawnersCacheManager {
    private static SpawnersCacheManager instance;

    public final ConcurrentHashMap<String, SpawnersData> spawnersCache = new ConcurrentHashMap<>();

    public void cacheSpawnerData(String id, SpawnersData spawnersData) {
        spawnersCache.put(id, spawnersData);
    }
    public SpawnersData getSpawnerData(String id) {
        return spawnersCache.get(id);
    }
    public void removeSpawnerData(String id) {
        spawnersCache.remove(id);
    }

    public static SpawnersCacheManager getInstance() {
        if (instance == null) instance = new SpawnersCacheManager();
        return instance;
    }
}
