package blizzard.development.mine.database.cache;

import blizzard.development.mine.database.storage.ToolData;
import org.bukkit.entity.Player;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class ToolCacheManager {

    private static final ToolCacheManager instance = new ToolCacheManager();

    public static ToolCacheManager getInstance() {
        return instance;
    }

    public final ConcurrentHashMap<String, ToolData> toolCache = new ConcurrentHashMap<>();

    public void cacheToolData(String id, ToolData toolData) {
        toolCache.put(id, toolData);
    }
    public ToolData getToolData(String id) {
        return toolCache.get(id);
    }
    public void removeToolData(String id) {
        toolCache.remove(id);
    }
}
