package blizzard.development.spawners.database.cache.managers;

import blizzard.development.spawners.database.storage.SlaughterhouseData;

import java.util.concurrent.ConcurrentHashMap;

public class SlaughterhouseCacheManager {
    private static SlaughterhouseCacheManager instance;

    public final ConcurrentHashMap<String, SlaughterhouseData> slaughterhouseCache = new ConcurrentHashMap<>();

    public void cacheSlaughterhouseData(String id, SlaughterhouseData slaughterhouseData) {
        slaughterhouseCache.put(id, slaughterhouseData);
    }
    public SlaughterhouseData getSlaughterhouseData(String id) {
        return slaughterhouseCache.get(id);
    }
    public void removeSlaughterhouseData(String id) {
        slaughterhouseCache.remove(id);
    }

    public static SlaughterhouseCacheManager getInstance() {
        if (instance == null) instance = new SlaughterhouseCacheManager();
        return instance;
    }
}
