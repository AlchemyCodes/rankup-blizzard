package blizzard.development.monsters.database.cache.methods;

import blizzard.development.monsters.database.cache.managers.MonstersCacheManager;
import blizzard.development.monsters.database.storage.MonstersData;

import java.util.concurrent.ConcurrentHashMap;

public class MonstersCacheMethods {
    private static MonstersCacheMethods instance;

    public static MonstersCacheMethods getInstance() {
        if (instance == null) instance = new MonstersCacheMethods();
        return instance;
    }
}
