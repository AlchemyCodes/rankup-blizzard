package blizzard.development.bosses.methods;

import blizzard.development.bosses.database.cache.ToolsCacheManager;
import blizzard.development.bosses.database.storage.ToolsData;
import blizzard.development.bosses.enums.Tools;
import blizzard.development.bosses.managers.BatchManager;
import org.bukkit.entity.Player;

public class ToolsMethods {
    public static void createTool(Player player, String id, Tools tool) {
        String nickname = player.getName();
        String type = tool.getType();
        ToolsData toolsData = new ToolsData(
                id, type, nickname
        );

        try {
            BatchManager.addToPendingQueue(toolsData);
            ToolsCacheManager.cacheToolData(id, toolsData);
        } catch (Exception ex) {
            player.sendMessage("§cOcorreu um erro ao salvar essa ferramenta no banco de dados!");
            ex.printStackTrace();
        }
    }
}
