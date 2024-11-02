package blizzard.development.plantations.plantations.adapters;

import blizzard.development.plantations.plantations.factory.ToolFactory;
import blizzard.development.plantations.plantations.item.PlowingToolBuildItem;
import org.bukkit.entity.Player;

public class ToolAdapter implements ToolFactory {
    @Override
    public void giveTool() {

    }

    @Override
    public void givePlowingTool(Player player) {
        player.getInventory().addItem(PlowingToolBuildItem.plowingTool(1));
    }
}
