package blizzard.development.farm.database.cache.methods;

import blizzard.development.farm.database.cache.ToolCacheManager;
import blizzard.development.farm.database.storage.ToolData;
import blizzard.development.farm.managers.BatchManager;
import org.bukkit.entity.Player;

import java.util.UUID;

public class ToolCacheMethod {

    private static final ToolCacheMethod instance = new ToolCacheMethod();

    public static ToolCacheMethod getInstance() {
        return instance;
    }

    private final ToolCacheManager toolCacheManager = ToolCacheManager.getInstance();

    public void createTool(Player player, String uuid, Integer plantations, Integer fortune) {
        String nickname = player.getName();

        ToolData toolData = new ToolData(
            uuid,
            nickname,
            plantations,
            fortune
        );

        try {
            BatchManager.addToPendingQueue(toolData);
            ToolCacheManager.getInstance().cacheToolData(uuid, toolData);
        } catch (Exception exception) {
            player.sendMessage("§cOcorreu um erro ao salvar essa ferramenta no banco de dados!");
            throw new RuntimeException(exception);
        }
    }

    public Integer getPlantations(String uuid) {
        ToolData toolData = toolCacheManager.getToolData(uuid);
        return toolData != null ? toolData.getPlantations() : 0;
    }

    public void setPlantations(String uuid, Integer amount) {
        ToolData toolData = toolCacheManager.getToolData(uuid);
        if (toolData != null) {
            toolData.setPlantations(amount);
            toolCacheManager.cacheToolData(uuid, toolData);
        }
    }

    public Integer getFortune(String uuid) {
        ToolData toolData = toolCacheManager.getToolData(uuid);
        return toolData != null ? toolData.getFortune() : 0;
    }

    public void setFortune(String uuid, Integer amount) {
        ToolData toolData = toolCacheManager.getToolData(uuid);
        if (toolData != null) {
            toolData.setFortune(amount);
            toolCacheManager.cacheToolData(uuid, toolData);
        }
    }
}
