package blizzard.development.plantations.database.cache.methods;

import blizzard.development.plantations.database.cache.ToolCacheManager;
import blizzard.development.plantations.database.storage.ToolData;
import blizzard.development.plantations.managers.BatchManager;
import blizzard.development.plantations.plantations.enums.ToolsEnum;
import blizzard.development.plantations.plantations.item.ToolBuildItem;
import org.bukkit.entity.Player;

public class ToolCacheMethod {

    private final ToolCacheManager toolCacheManager = ToolCacheManager.getInstance();
    private static final ToolCacheMethod instance = new ToolCacheMethod();

    public static ToolCacheMethod getInstance() {
        return instance;
    }

    public static void createTool(Player player, String id, String type, String skin, Integer blocks, Integer botany, Integer agility, Integer explosion, Integer thunderstorm, Integer xray, Integer blizzard) {
        String nickname = player.getName();
        ToolData toolData = new ToolData(
                id,
                type,
                skin,
                nickname,
                blocks,
                botany,
                agility,
                explosion,
                thunderstorm,
                xray,
                blizzard
        );

        try {
            BatchManager.addToPendingQueue(toolData);
            ToolCacheManager.cacheToolData(id, toolData);
        } catch (Exception exception) {
            player.sendMessage("§cOcorreu um erro ao salvar essa ferramenta no banco de dados!");
            throw new RuntimeException(exception);
        }
    }

//    private void updateTool(String id, Player player, int botany, int agility, int explosion, int thunderstorm, int xray, int blizzard) {
//        ToolCacheMethod toolCacheMethod = ToolCacheMethod.getInstance();
//
//        String skin = toolCacheMethod.getSkin(id);
//        ToolsEnum tool = ToolsEnum.valueOf(skin);
//
//        player.getInventory().setItemInMainHand(ToolBuildItem.tool(
//            id,
//            tool.getMaterial(),
//            toolCacheMethod.getBlocks(id),
//            botany,
//            agility,
//            explosion,
//            thunderstorm,
//            xray,
//            blizzard,
//            1
//        ));
//    }


    public String getSkin(String id) {
        ToolData toolData = toolCacheManager.getToolData(id);
        return toolData != null ? toolData.getSkin() : "ERRO!";
    }

    public void setSkin(String id, ToolsEnum toolsEnum) {
        ToolData toolData = toolCacheManager.getToolData(id);

        if (toolData != null) {
            toolData.setSkin(toolsEnum.toString());
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

    public Integer getThunderstorm(String id) {
        ToolData toolData = toolCacheManager.getToolData(id);
        return toolData != null ? toolData.getThunderstorm() : 0;
    }

    public void setThunderstorm(String id, int thunderstorm) {
        ToolData toolData = toolCacheManager.getToolData(id);
        if (toolData != null) {
            toolData.setThunderstorm(thunderstorm);
            toolCacheManager.updateToolData(id, toolData);
        }
    }

    public Integer getXray(String id) {
        ToolData toolData = toolCacheManager.getToolData(id);
        return toolData != null ? toolData.getXray() : 0;
    }

    public void setXray(String id, int xray) {
        ToolData toolData = toolCacheManager.getToolData(id);
        if (toolData != null) {
            toolData.setXray(xray);
            toolCacheManager.updateToolData(id, toolData);
        }
    }

    public Integer getBlizzard(String id) {
        ToolData toolData = toolCacheManager.getToolData(id);
        return toolData != null ? toolData.getBlizzard() : 0;
    }

    public void setBlizzard(String id, int xray) {
        ToolData toolData = toolCacheManager.getToolData(id);
        if (toolData != null) {
            toolData.setBlizzard(xray);
            toolCacheManager.updateToolData(id, toolData);
        }
    }

    public Integer getBlocks(String id) {
        ToolData toolData = toolCacheManager.getToolData(id);
        return toolData != null ? toolData.getBlocks() : 0;
    }

    public void setBlocks(String id, int blocks) {
        ToolData toolData = toolCacheManager.getToolData(id);
        if (toolData != null) {
            toolData.setBlocks(blocks);
            toolCacheManager.updateToolData(id, toolData);
        }
    }
}
