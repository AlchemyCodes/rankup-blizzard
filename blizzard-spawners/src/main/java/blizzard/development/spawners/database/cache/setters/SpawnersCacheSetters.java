package blizzard.development.spawners.database.cache.setters;

import blizzard.development.spawners.database.cache.managers.SpawnersCacheManager;
import blizzard.development.spawners.database.storage.SpawnersData;

import java.util.List;

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

    public void setSpawnerDrops(String id, double amount) {
        SpawnersData data = cache.getSpawnerData(id);
        if (data != null) {
            data.setDrops(amount);
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

    public void setSpawnerAmout(String id, double amount) {
        SpawnersData data = cache.getSpawnerData(id);
        if (data != null) {
            data.setAmount(amount);
            cache.cacheSpawnerData(id, data);
        }
    }

    public void addSpawnerSpeedLevel(String id, int amount) {
        SpawnersData data = cache.getSpawnerData(id);
        if (data != null) {
            data.setSpeedLevel(data.getSpeedLevel() + amount);
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

    public void addSpawnerFriend(String id, List<String> friend) {
        SpawnersData data = cache.getSpawnerData(id);
        if (data != null) {
            List<String> friends = data.getFriends();
            friends.addAll(friend);
            data.setFriends(friends);
            cache.cacheSpawnerData(id, data);
        }
    }

    public void removeSpawnerFriend(String id, List<String> friend) {
        SpawnersData data = cache.getSpawnerData(id);
        if (data != null) {
            List<String> friends = data.getFriends();
            friends.removeAll(friend);
            data.setFriends(friends);
            cache.cacheSpawnerData(id, data);
        }
    }

    public void addSpawnerFriendsLimit(String id, int amount) {
        SpawnersData data = cache.getSpawnerData(id);
        if (data != null) {
            data.setFriendsLimit(data.getFriendsLimit() + amount);
            cache.cacheSpawnerData(id, data);
        }
    }

    public void setDropsAutoSell(String id, boolean state) {
        SpawnersData data = cache.getSpawnerData(id);
        if (data != null) {
            data.setAutoSell(state);
            cache.cacheSpawnerData(id, data);
        }
    }

    public void setDropsAutoSellState(String id, boolean state) {
        SpawnersData data = cache.getSpawnerData(id);
        if (data != null) {
            data.setAutoSellState(state);
            cache.cacheSpawnerData(id, data);
        }
    }

    public static SpawnersCacheSetters getInstance() {
        if (instance == null) instance = new SpawnersCacheSetters();
        return instance;
    }
}
