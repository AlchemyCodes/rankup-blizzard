package blizzard.development.spawners.database.cache.setters;

import blizzard.development.spawners.database.cache.managers.SlaughterhouseCacheManager;
import blizzard.development.spawners.database.cache.managers.SpawnersCacheManager;
import blizzard.development.spawners.database.storage.SlaughterhouseData;
import blizzard.development.spawners.database.storage.SpawnersData;

import java.util.List;

public class SlaughterhouseCacheSetters {
    private static SlaughterhouseCacheSetters instance;

    private final SlaughterhouseCacheManager cache = SlaughterhouseCacheManager.getInstance();

    public void setSlaughterhouseState(String id, String state) {
        SlaughterhouseData data = cache.getSlaughterhouseData(id);
        if (data != null) {
            data.setState(state);
            cache.cacheSlaughterhouseData(id, data);
        }
    }

    public void addSlaughterhouseFriend(String id, List<String> friend) {
        SlaughterhouseData data = cache.getSlaughterhouseData(id);
        if (data != null) {
            List<String> friends = data.getFriends();
            friends.addAll(friend);
            data.setFriends(friends);
            cache.cacheSlaughterhouseData(id, data);
        }
    }

    public void removeSlaughterhouseFriend(String id, List<String> friend) {
        SlaughterhouseData data = cache.getSlaughterhouseData(id);
        if (data != null) {
            List<String> friends = data.getFriends();
            friends.removeAll(friend);
            data.setFriends(friends);
            cache.cacheSlaughterhouseData(id, data);
        }
    }

    public void addSlaughterhouseFriendsLimit(String id, int amount) {
        SlaughterhouseData data = cache.getSlaughterhouseData(id);
        if (data != null) {
            data.setFriendsLimit(data.getFriendsLimit() + amount);
            cache.cacheSlaughterhouseData(id, data);
        }
    }

    public static SlaughterhouseCacheSetters getInstance() {
        if (instance == null) instance = new SlaughterhouseCacheSetters();
        return instance;
    }
}
