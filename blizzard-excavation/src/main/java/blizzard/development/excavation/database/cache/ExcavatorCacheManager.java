package blizzard.development.excavation.database.cache;

import blizzard.development.excavation.database.storage.ExcavatorData;

import java.util.concurrent.ConcurrentHashMap;

public class ExcavatorCacheManager {

    public static final ConcurrentHashMap<String, ExcavatorData> excavatorCache = new ConcurrentHashMap<>();

    public static void cacheExcavatorData(String toolId, ExcavatorData excavatorData) {
        excavatorCache.put(toolId, excavatorData);
    }
    public ExcavatorData getExcavatorData(String excavator) {
        return excavatorCache.get(excavator);
    }

    public void removeExcavatorData(String excavator) {
        excavatorCache.remove(excavator);
    }
}
