package blizzard.development.monsters.database.cache.managers;

import blizzard.development.monsters.database.storage.MonstersData;

import java.util.concurrent.ConcurrentHashMap;

public class MonstersCacheManager {
    private static MonstersCacheManager instance;

    public final ConcurrentHashMap<String, MonstersData> monstersCache = new ConcurrentHashMap<>();

    public void cacheMonsterData(String uuid, MonstersData monstersData) {
        monstersCache.put(uuid, monstersData);
    }
    public MonstersData getMonsterData(String uuid) {
        return monstersCache.get(uuid);
    }
    public void removeMonsterData(String uuid) {
        monstersCache.remove(uuid);
    }

    public static MonstersCacheManager getInstance() {
        if (instance == null) instance = new MonstersCacheManager();
        return instance;
    }
}
