package blizzard.development.spawners.database.cache.getters;

import blizzard.development.spawners.database.cache.managers.SlaughterhouseCacheManager;
import blizzard.development.spawners.database.cache.setters.PlayersCacheSetters;
import blizzard.development.spawners.database.storage.SlaughterhouseData;
import blizzard.development.spawners.database.storage.SpawnersData;

import java.util.List;

public class SlaughterhouseCacheGetters {
    private static SlaughterhouseCacheGetters instance;

    private final SlaughterhouseCacheManager cache = SlaughterhouseCacheManager.getInstance();

    public String getSlaughterhouseTier(String id) {
        SlaughterhouseData data = cache.getSlaughterhouseData(id);
        if (data != null) {
            return data.getTier();
        }
        return null;
    }

    public String getSlaughterhouseState(String id) {
        SlaughterhouseData data = cache.getSlaughterhouseData(id);
        if (data != null) {
            return data.getState();
        }
        return null;
    }

    public String getSlaughterhouseOwner(String id) {
        SlaughterhouseData data = cache.getSlaughterhouseData(id);
        if (data != null) {
            return data.getNickname();
        }
        return null;
    }

    public List<String> getSlaughterhouseFriends(String id) {
        SlaughterhouseData data = cache.getSlaughterhouseData(id);
        if (data != null) {
            return data.getFriends();
        }
        return null;
    }

    public int getSlaughterhouseFriendsLimit(String id) {
        SlaughterhouseData data = cache.getSlaughterhouseData(id);
        if (data != null) {
            return data.getFriendsLimit();
        }
        return 0;
    }

    public String getSlaughterhouseLocation(String id) {
        SlaughterhouseData data = cache.getSlaughterhouseData(id);
        if (data != null) {
            return data.getLocation();
        }
        return null;
    }

    public static SlaughterhouseCacheGetters getInstance() {
        if (instance == null) instance = new SlaughterhouseCacheGetters();
        return instance;
    }
}
