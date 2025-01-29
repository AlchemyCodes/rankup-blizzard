package blizzard.development.farm.farm.factory;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public interface ToolFactory {

    void giveTool(Player player);
    void updateTool(Player player, ItemStack itemStack);
}
