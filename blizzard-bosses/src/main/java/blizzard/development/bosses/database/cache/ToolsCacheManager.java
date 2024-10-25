package blizzard.development.bosses.database.cache;

import blizzard.development.bosses.database.storage.ToolsData;

import java.util.concurrent.ConcurrentHashMap;

public class ToolsCacheManager {
    public static final ConcurrentHashMap<String, ToolsData> toolsCache = new ConcurrentHashMap<>();

    public static void cacheToolData(String toolId, ToolsData toolsData) {
        toolsCache.put(toolId, toolsData);
    }
    public static ToolsData getToolData(String toolId) {
        return toolsCache.get(toolId);
    }
    public static void removeToolData(String toolId) {
        toolsCache.remove(toolId);
    }

}
