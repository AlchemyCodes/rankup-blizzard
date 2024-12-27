package blizzard.development.monsters.database.cache.managers;

import blizzard.development.monsters.database.storage.MonstersData;

import java.util.concurrent.ConcurrentHashMap;

public class ToolsCacheManager {
    private static MonstersCacheManager instance;

    public final ConcurrentHashMap<String, MonstersData> monstersCache = new ConcurrentHashMap<>();

    public void cacheMonsterData(String id, MonstersData monstersData) {
        monstersCache.put(id, monstersData);
    }
    public MonstersData getMonsterData(String id) {
        return monstersCache.get(id);
    }
    public void removeMonsterData(String id) {
        monstersCache.remove(id);
    }

    public static MonstersCacheManager getInstance() {
        if (instance == null) instance = new MonstersCacheManager();
        return instance;
    }
}
