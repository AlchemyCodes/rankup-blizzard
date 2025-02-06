package blizzard.development.monsters.database.cache.methods;

import blizzard.development.monsters.database.cache.managers.MonstersCacheManager;
import blizzard.development.monsters.database.storage.MonstersData;
import blizzard.development.monsters.monsters.enums.Tools;
import blizzard.development.monsters.utils.LocationUtils;
import org.bukkit.Location;

public class MonstersCacheMethods {
    private static MonstersCacheMethods instance;

    private final MonstersCacheManager cache = MonstersCacheManager.getInstance();

    public void setLife(String uuid, int life) {
        MonstersData data = cache.getMonsterData(uuid);
        if (data != null) {
            data.setLife(life);
            cache.cacheMonsterData(uuid, data);
        }
    }

    public String getType(String uuid) {
        MonstersData data = cache.getMonsterData(uuid);
        if (data != null) {
            return data.getType();
        }
        return null;
    }

    public Location getLocation(String uuid) {
        MonstersData data = cache.getMonsterData(uuid);
        if (data != null) {
            return LocationUtils.getInstance().deserializeLocation(data.getLocation());
        }
        return null;
    }

    public static MonstersCacheMethods getInstance() {
        if (instance == null) instance = new MonstersCacheMethods();
        return instance;
    }
}
