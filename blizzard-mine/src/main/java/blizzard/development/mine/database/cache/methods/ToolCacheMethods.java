package blizzard.development.mine.database.cache.methods;

import blizzard.development.mine.database.cache.ToolCacheManager;
import blizzard.development.mine.database.storage.ToolData;
import blizzard.development.mine.managers.data.DataBatchManager;
import blizzard.development.mine.mine.enums.ToolEnum;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.stream.Collectors;

public class ToolCacheMethods {
    private static final ToolCacheMethods instance = new ToolCacheMethods();

    public static ToolCacheMethods getInstance() {
        return instance;
    }

    private final ToolCacheManager toolCacheManager = ToolCacheManager.getInstance();

    public ToolData createTool(Player player, String id, String type, String skin, Integer blocks, Integer meteor) {
        String nickname = player.getName();
        ToolData toolData = new ToolData(
                id,
                type,
                skin,
                nickname,
                blocks,
                meteor
        );

        try {
            DataBatchManager.addToPendingQueue(toolData);
            toolCacheManager.cacheToolData(id, toolData);
        } catch (Exception exception) {
            player.sendMessage("§cOcorreu um erro ao salvar essa ferramenta no banco de dados!");
            throw new RuntimeException(exception);
        }

        return toolData;
    }

    public void setSkin(String id, ToolEnum skin) {
        ToolData toolData = toolCacheManager.getToolData(id);
        if (toolData != null) {
            toolData.setSkin(skin.getType());
            toolCacheManager.cacheToolData(id, toolData);
        }
    }

    public Integer getBlocks(String id) {
        ToolData toolData = toolCacheManager.getToolData(id);
        return toolData != null ? toolData.getBlocks() : 0;
    }

    public void setBlocks(String id, int amount) {
        ToolData toolData = toolCacheManager.getToolData(id);
        if (toolData != null) {
            toolData.setBlocks(amount);
            toolCacheManager.cacheToolData(id, toolData);
        }
    }

    public Integer getMeteor(String id) {
        ToolData toolData = toolCacheManager.getToolData(id);
        return toolData != null ? toolData.getMeteor() : 0;
    }

    public void setMeteor(String id, int amount) {
        ToolData toolData = toolCacheManager.getToolData(id);
        if (toolData != null) {
            toolData.setMeteor(amount);
            toolCacheManager.cacheToolData(id, toolData);
        }
    }

    public List<ToolData> getTopBlocks() {
        return toolCacheManager.toolCache.values().stream()
                .sorted((p1, p2) -> Double.compare(p2.getBlocks(), p1.getBlocks()))
                .limit(10)
                .collect(Collectors.toList());
    }
}
