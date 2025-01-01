package blizzard.development.monsters.database.cache.managers;

import blizzard.development.monsters.database.storage.ToolsData;

import java.util.concurrent.ConcurrentHashMap;

public class ToolsCacheManager {
    private static ToolsCacheManager instance;

    public final ConcurrentHashMap<String, ToolsData> toolsCache = new ConcurrentHashMap<>();

    public void cacheToolData(String id, ToolsData toolsData) {
        toolsCache.put(id, toolsData);
    }
    public ToolsData getToolData(String id) {
        return toolsCache.get(id);
    }
    public void removeToolData(String id) {
        toolsCache.remove(id);
    }

    public static ToolsCacheManager getInstance() {
        if (instance == null) instance = new ToolsCacheManager();
        return instance;
    }
}
