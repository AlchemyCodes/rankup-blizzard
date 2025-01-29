package blizzard.development.farm.database.cache;

import blizzard.development.farm.database.storage.ToolData;

import java.util.concurrent.ConcurrentHashMap;

public class ToolCacheManager {

    private static final ToolCacheManager instance = new ToolCacheManager();

    public final ConcurrentHashMap<String, ToolData> toolsCache = new ConcurrentHashMap<>();

    public static ToolCacheManager getInstance() {
        return instance;
    }

    public void cacheToolData(String toolId, ToolData toolData) {
        toolsCache.put(toolId, toolData);
    }

    public ToolData getToolData(String toolId) {
        return toolsCache.get(toolId);
    }

    public void removeToolData(String toolId) {
        toolsCache.remove(toolId);
    }

    public void updateToolData(String toolId, ToolData toolData) {
        toolsCache.put(toolId, toolData);
    }
}
