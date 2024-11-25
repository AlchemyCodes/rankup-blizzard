package blizzard.development.spawners.database.cache.getters;


import blizzard.development.spawners.database.cache.managers.SpawnersCacheManager;
import blizzard.development.spawners.database.storage.SpawnersData;

public class SpawnersCacheGetters {
    private static SpawnersCacheGetters instance;

    private final SpawnersCacheManager cache = SpawnersCacheManager.getInstance();

    public String getSpawnerType(String id) {
        SpawnersData data = cache.getSpawnerData(id);
        if (data != null) {
            return data.getType();
        }
        return null;
    }

    public double getSpawnerAmount(String id) {
        SpawnersData data = cache.getSpawnerData(id);
        if (data != null) {
            return data.getAmount();
        }
        return 0;
    }

    public double getSpawnerMobsAmount(String id) {
        SpawnersData data = cache.getSpawnerData(id);
        if (data != null) {
            return data.getMobAmount();
        }
        return 0;
    }

    public String getSpawnerLocation(String id) {
        SpawnersData data = cache.getSpawnerData(id);
        if (data != null) {
            return data.getLocation();
        }
        return null;
    }

    public String getSpawnerMobsLocation(String id) {
        SpawnersData data = cache.getSpawnerData(id);
        if (data != null) {
            return data.getMobLocation();
        }
        return null;
    }

    public String getSpawnerOwner(String id) {
        SpawnersData data = cache.getSpawnerData(id);
        if (data != null) {
            return data.getNickname();
        }
        return null;
    }

    public String getSpawnerPlotId(String id) {
        SpawnersData data = cache.getSpawnerData(id);
        if (data != null) {
            return data.getPlotId();
        }
        return null;
    }

    public double getSpawnerSpeedLevel(String id) {
        SpawnersData data = cache.getSpawnerData(id);
        if (data != null) {
            return data.getSpeedLevel();
        }
        return 0;
    }

    public double getSpawnerLuckyLevel(String id) {
        SpawnersData data = cache.getSpawnerData(id);
        if (data != null) {
            return data.getLuckyLevel();
        }
        return 0;
    }

    public double getSpawnerExperienceLevel(String id) {
        SpawnersData data = cache.getSpawnerData(id);
        if (data != null) {
            return data.getExperienceLevel();
        }
        return 0;
    }

    public static SpawnersCacheGetters getInstance() {
        if (instance == null) instance = new SpawnersCacheGetters();
        return instance;
    }
}
