package blizzard.development.spawners.database.cache.setters;

import blizzard.development.spawners.database.cache.managers.SpawnersCacheManager;
import blizzard.development.spawners.database.storage.SpawnersData;

public class SpawnersCacheSetters {
    private static SpawnersCacheSetters instance;

    private final SpawnersCacheManager cache = SpawnersCacheManager.getInstance();

    public void setSpawnerMobAmout(String id, double amount) {
        SpawnersData data = cache.getSpawnerData(id);
        if (data != null) {
            data.setMobAmount(amount);
            cache.cacheSpawnerData(id, data);
        }
    }

    public void setSpawnerState(String id, String state) {
        SpawnersData data = cache.getSpawnerData(id);
        if (data != null) {
            data.setState(state);
            cache.cacheSpawnerData(id, data);
        }
    }

    public void addSpawnerDrops(String id, double amount) {
        SpawnersData data = cache.getSpawnerData(id);
        if (data != null) {
            data.setDrops(data.getDrops() + amount);
            cache.cacheSpawnerData(id, data);
        }
    }

    public void removeSpawnerDrops(String id, double amount) {
        SpawnersData data = cache.getSpawnerData(id);
        if (data != null) {
            data.setDrops(data.getDrops() - amount);
            cache.cacheSpawnerData(id, data);
        }
    }

    public void addSpawnerSpeedLevel(String id, int amount) {
        SpawnersData data = cache.getSpawnerData(id);
        if (data != null) {
            data.setSpeedLevel(data.getSpeedLevel() - amount);
            cache.cacheSpawnerData(id, data);
        }
    }

    public void addSpawnerLuckyLevel(String id, int amount) {
        SpawnersData data = cache.getSpawnerData(id);
        if (data != null) {
            data.setLuckyLevel(data.getLuckyLevel() + amount);
            cache.cacheSpawnerData(id, data);
        }
    }

    public void addSpawnerExperienceLevel(String id, int amount) {
        SpawnersData data = cache.getSpawnerData(id);
        if (data != null) {
            data.setExperienceLevel(data.getExperienceLevel() + amount);
            cache.cacheSpawnerData(id, data);
        }
    }

    public static SpawnersCacheSetters getInstance() {
        if (instance == null) instance = new SpawnersCacheSetters();
        return instance;
    }
}
