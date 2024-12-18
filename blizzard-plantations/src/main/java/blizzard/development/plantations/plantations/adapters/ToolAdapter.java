package blizzard.development.plantations.plantations.adapters;

import blizzard.development.plantations.plantations.factory.ToolFactory;
import blizzard.development.plantations.plantations.item.ToolBuildItem;
import org.bukkit.entity.Player;

public class ToolAdapter implements ToolFactory {
    @Override
    public void giveTool(Player player) {
        player.getInventory().addItem(ToolBuildItem.tool(player, 1));
    }
}
