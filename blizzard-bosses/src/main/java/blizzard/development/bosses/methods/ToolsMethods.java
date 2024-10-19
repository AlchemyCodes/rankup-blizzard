package blizzard.development.bosses.methods;

import blizzard.development.bosses.database.cache.ToolsCacheManager;
import blizzard.development.bosses.database.dao.ToolsDAO;
import blizzard.development.bosses.database.storage.ToolsData;
import blizzard.development.bosses.enums.Tools;
import org.bukkit.entity.Player;

public class ToolsMethods {
    private static final ToolsDAO toolsDAO = new ToolsDAO();

    public static void createTool(Player player, String id, Tools tool) {
        String nickname = player.getName();
        String type = tool.getType();
        ToolsData toolsData = new ToolsData(
                id, type, nickname
        );

        try {
            toolsDAO.createToolsData(toolsData);
            ToolsCacheManager.cacheToolData(id, toolsData);
        } catch (Exception ex) {
            player.sendMessage("§cOcorreu um erro ao salvar essa ferramenta no banco de dados!");
            ex.printStackTrace();
        }
    }
}
