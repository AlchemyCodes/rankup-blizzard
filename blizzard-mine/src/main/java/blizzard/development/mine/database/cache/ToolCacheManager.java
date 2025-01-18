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

    public final ConcurrentHashMap<UUID, ToolData> toolCache = new ConcurrentHashMap<>();

    public void cacheToolData(Player player, ToolData playerData) {
        toolCache.put(player.getUniqueId(), playerData);
    }
    public void cacheToolData(UUID uuid, ToolData playerData) {
        toolCache.put(uuid, playerData);
    }
    public ToolData getToolData(Player player) {
        return toolCache.get(player.getUniqueId());
    }
    public void removeToolData(Player player) {
        toolCache.remove(player.getUniqueId());
    }
}
