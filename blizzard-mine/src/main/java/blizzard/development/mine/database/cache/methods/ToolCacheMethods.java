package blizzard.development.mine.database.cache.methods;

import blizzard.development.currencies.api.CurrenciesAPI;
import blizzard.development.currencies.database.cache.PlayersCacheManager;
import blizzard.development.currencies.database.storage.PlayersData;
import blizzard.development.currencies.enums.Currencies;
import blizzard.development.mine.database.cache.ToolCacheManager;
import blizzard.development.mine.database.storage.ToolData;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.stream.Collectors;

public class ToolCacheMethods {
    private static final ToolCacheMethods instance = new ToolCacheMethods();

    public static ToolCacheMethods getInstance() {
        return instance;
    }

    private final ToolCacheManager toolCacheManager = ToolCacheManager.getInstance();

    public Integer getBlocks(Player player) {
        ToolData toolData = toolCacheManager.getToolData(player);
        return toolData != null ? toolData.getBlocks() : 0;
    }

    public void setBlocks(Player player, int amount) {
        ToolData toolData = toolCacheManager.getToolData(player);
        if (toolData != null) {
            toolData.setBlocks(amount);
            toolCacheManager.cacheToolData(player, toolData);
        }
    }

    public Integer getMeteor(Player player) {
        ToolData toolData = toolCacheManager.getToolData(player);
        return toolData != null ? toolData.getMeteor() : 0;
    }

    public void setMeteor(Player player, int amount) {
        ToolData toolData = toolCacheManager.getToolData(player);
        if (toolData != null) {
            toolData.setMeteor(amount);
            toolCacheManager.cacheToolData(player, toolData);
        }
    }

    public List<ToolData> getTopBlocks() {
        return toolCacheManager.toolCache.values().stream()
                .sorted((p1, p2) -> Double.compare(p2.getBlocks(), p1.getBlocks()))
                .limit(10)
                .collect(Collectors.toList());
    }
}
