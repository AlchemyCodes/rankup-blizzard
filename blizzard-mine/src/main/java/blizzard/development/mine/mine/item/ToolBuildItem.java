package blizzard.development.mine.mine.item;

import blizzard.development.mine.builders.item.ItemBuilder;
import blizzard.development.mine.database.cache.methods.ToolCacheMethods;
import blizzard.development.mine.database.storage.ToolData;
import blizzard.development.mine.mine.enums.ToolEnum;
import blizzard.development.mine.utils.PluginImpl;
import blizzard.development.mine.utils.text.NumberUtils;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;

public class ToolBuildItem {

    public static ItemStack tool(Player player, Boolean give, String id, ToolEnum skin, Integer blocks, Integer meteor) {
        ItemStack item;

        String enchantmentsField = skin.getColor() + " Encantamentos:" +  skin.getColor();
        String rightButtonField = skin.getColor() + "Pressione shift + b. direito." + skin.getColor();

        if (give) {
            ToolData toolData = ToolCacheMethods.getInstance()
                    .createTool(
                            player,
                            id,
                            ToolEnum.TOOL.getType(),
                            skin.getType(),
                            0,
                            0
                    );

            item = new ItemBuilder(skin.getMaterial())
                    .setDisplayName(skin.getDisplay() + "§7[" + NumberUtils.getInstance().formatNumber(toolData.getBlocks()) + "§7]")
                    .setLore(Arrays.asList(
                            "§7Use esta picareta para",
                            "§7quebrar blocos da mina.",
                            "",
                            enchantmentsField,
                            "  §7Durabilidade §l∞",
                            "  §7Eficiência §l∞",
                            "  §7Meteoro §l" + meteor,
                            "",
                            rightButtonField
                    ))
                    .addEnchant(Enchantment.DURABILITY, 1000, true)
                    .addEnchant(Enchantment.DIG_SPEED, 1000, true)
                    .addPersistentData(PluginImpl.getInstance().plugin, "blizzard.mine.tool", id)
                    .build(1);

            player.getInventory().addItem(item);
        } else {
            item = new ItemBuilder(skin.getMaterial())
                    .setDisplayName(skin.getDisplay() + "§7[" + NumberUtils.getInstance().formatNumber(blocks) + "§7]")
                    .setLore(Arrays.asList(
                            "§7Use esta picareta para",
                            "§7quebrar blocos da mina.",
                            "",
                            enchantmentsField,
                            "  §7Durabilidade §l∞",
                            "  §7Eficiência §l∞",
                            "  §7Meteoro §l" + meteor,
                            "",
                            rightButtonField
                    ))
                    .addEnchant(Enchantment.DURABILITY, 1000, true)
                    .addEnchant(Enchantment.DIG_SPEED, 1000, true)
                    .addPersistentData(PluginImpl.getInstance().plugin, "blizzard.mine.tool", id)
                    .build(1);
        }
        return item;
    }
}
