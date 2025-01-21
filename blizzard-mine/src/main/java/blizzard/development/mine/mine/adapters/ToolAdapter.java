package blizzard.development.mine.mine.adapters;

import blizzard.development.core.Main;
import blizzard.development.mine.builders.item.ItemBuilder;
import blizzard.development.mine.mine.factory.ToolFactory;
import blizzard.development.mine.mine.item.EnchantmentBuildItem;
import blizzard.development.mine.mine.item.ExitBuildItem;
import blizzard.development.mine.mine.item.ToolBuildItem;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class ToolAdapter implements ToolFactory {

    private static final ToolAdapter instance = new ToolAdapter();

    public static ToolAdapter getInstance() {
        return instance;
    }

    @Override
    public void giveTool(Player player) {
        player.getInventory().setItem(4, ToolBuildItem.tool(player));
        player.getInventory().setItem(7, ExitBuildItem.exit(player));
        player.getInventory().setItem(1, EnchantmentBuildItem.enchantment(player));

    }

    @Override
    public void removeTool(Player player) {
        for (ItemStack item : player.getInventory().getContents()) {
            if (item == null) continue;

            if (ItemBuilder.hasPersistentData(Main.getInstance(), item, "blizzard.mine.tool") ||
                ItemBuilder.hasPersistentData(Main.getInstance(), item, "blizzard.mine.exit") ||
                ItemBuilder.hasPersistentData(Main.getInstance(), item, "blizzard.mine.enchantment")) {
                player.getInventory().remove(item);
            }
        }
    }
}