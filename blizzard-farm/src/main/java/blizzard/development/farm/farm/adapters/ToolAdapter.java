package blizzard.development.farm.farm.adapters;

import blizzard.development.farm.database.cache.methods.ToolCacheMethod;
import blizzard.development.farm.farm.factory.ToolFactory;
import blizzard.development.farm.farm.item.ToolBuildItem;
import blizzard.development.farm.utils.PluginImpl;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import static blizzard.development.farm.builders.item.ItemBuilder.getPersistentData;

public class ToolAdapter implements ToolFactory {

    private static final ToolAdapter instance = new ToolAdapter();
    public static ToolAdapter getInstance() {
        return instance;
    }

    @Override
    public void giveTool(Player player) {
        player.getInventory().addItem(ToolBuildItem.tool(player));
    }

    @Override
    public void updateTool(Player player, ItemStack itemStack) {
        String id = getPersistentData(PluginImpl.getInstance().plugin, itemStack, "ferramenta.farm.id");
        ToolCacheMethod toolCacheMethod = ToolCacheMethod.getInstance();

        player.getInventory().setItemInMainHand(ToolBuildItem.tool(player, id, toolCacheMethod.getPlantations(id), toolCacheMethod.getFortune(id)));
    }
}
