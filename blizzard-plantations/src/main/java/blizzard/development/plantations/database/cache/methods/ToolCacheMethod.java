package blizzard.development.plantations.database.cache.methods;

import blizzard.development.plantations.database.cache.ToolCacheManager;
import blizzard.development.plantations.database.storage.ToolData;
import blizzard.development.plantations.managers.BatchManager;
import org.bukkit.entity.Player;

public class ToolCacheMethod {

    private final ToolCacheManager toolCacheManager = ToolCacheManager.getInstance();
    private static final ToolCacheMethod instance = new ToolCacheMethod();

    public static ToolCacheMethod getInstance() {
        return instance;
    }

    public static void createTool(Player player, String id, String type, Integer botany, Integer agility, Integer explosion) {
        String nickname = player.getName();
        ToolData toolData = new ToolData(
                id,
                type,
                nickname,
                botany,
                agility,
                explosion,
                0,
                0
        );

        try {
            BatchManager.addToPendingQueue(toolData);
            ToolCacheManager.cacheToolData(id, toolData);
        } catch (Exception exception) {
            player.sendMessage("§cOcorreu um erro ao salvar essa ferramenta no banco de dados!");
            throw new RuntimeException(exception);
        }
    }

    public static void createTool(Player player, String id, String type, Integer accelerator, Integer plow) {
        String nickname = player.getName();
        ToolData toolData = new ToolData(
                id,
                type,
                nickname,
                0,
                0,
                0,
                accelerator,
                plow
        );

        try {
            BatchManager.addToPendingQueue(toolData);
            ToolCacheManager.cacheToolData(id, toolData);
        } catch (Exception exception) {
            player.sendMessage("§cOcorreu um erro ao salvar essa ferramenta no banco de dados!");
            throw new RuntimeException(exception);
        }
    }

    public Integer getBotany(String id) {
        ToolData toolData = toolCacheManager.getToolData(id);
        return toolData != null ? toolData.getBotany() : 0;
    }

    public void setBotany(String id, int botany) {
        ToolData toolData = toolCacheManager.getToolData(id);
        if (toolData != null) {
            toolData.setBotany(botany);
            toolCacheManager.updateToolData(id, toolData);
        }
    }

    public Integer getAgility(String id) {
        ToolData toolData = toolCacheManager.getToolData(id);
        return toolData != null ? toolData.getAgility() : 0;
    }

    public void setAgility(String id, int agility) {
        ToolData toolData = toolCacheManager.getToolData(id);
        if (toolData != null) {
            toolData.setAgility(agility);
            toolCacheManager.updateToolData(id, toolData);
        }
    }

    public Integer getExplosion(String id) {
        ToolData toolData = toolCacheManager.getToolData(id);
        return toolData != null ? toolData.getExplosion() : 0;
    }

    public void setExplosion(String id, int explosion) {
        ToolData toolData = toolCacheManager.getToolData(id);
        if (toolData != null) {
            toolData.setExplosion(explosion);
            toolCacheManager.updateToolData(id, toolData);
        }
    }

    public Integer getAccelerator(String id) {
        ToolData toolData = toolCacheManager.getToolData(id);
        return toolData != null ? toolData.getAccelerator() : 0;
    }

    public void setAccelerator(String id, int accelerator) {
        ToolData toolData = toolCacheManager.getToolData(id);
        if (toolData != null) {
            toolData.setAccelerator(accelerator);
            toolCacheManager.updateToolData(id, toolData);
        }
    }

    public Integer getPlow(String id) {
        ToolData toolData = toolCacheManager.getToolData(id);
        return toolData != null ? toolData.getPlow() : 0;
    }

    public void setPlow(String id, int plow) {
        ToolData toolData = toolCacheManager.getToolData(id);
        if (toolData != null) {
            toolData.setPlow(plow);
            toolCacheManager.updateToolData(id, toolData);
        }
    }
}
