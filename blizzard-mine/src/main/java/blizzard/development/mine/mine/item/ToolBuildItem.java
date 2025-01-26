package blizzard.development.mine.mine.item;

import blizzard.development.core.Main;
import blizzard.development.mine.builders.item.ItemBuilder;
import blizzard.development.mine.database.cache.ToolCacheManager;
import blizzard.development.mine.database.storage.ToolData;
import blizzard.development.mine.managers.events.Avalanche;
import blizzard.development.mine.utils.text.NumberUtils;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;

public class ToolBuildItem {

    public static ItemStack tool(Player player) {

        ToolData toolData = ToolCacheManager.getInstance().getToolData(player);

        Material material;

        if (Avalanche.isAvalancheActive) {
            material = Material.IRON_SHOVEL;
        } else {
            material = Material.WOODEN_PICKAXE;
        }

        return new ItemBuilder(material)
            .setDisplayName("<#a88459>Picareta de<#a88459> <bold><#a88459>Madeira<#a88459></bold><#a88459> §7[" + NumberUtils.getInstance().formatNumber(toolData.getBlocks()) + "§7]")
            .setLore(Arrays.asList(
                    "§7Use esta picareta para",
                    "§7quebrar blocos da mina.",
                    "",
                    " <#a88459>Encantamentos:<#a88459>",
                    "  §7Durabilidade §l∞",
                    "  §7Eficiência §l∞",
                    "  §7Agilidade §l0",
                    "  §7Avalanche §l0",
                    "  §7Dinamite §l0",
                    "  §7Meteoro §l0",
                    "  §7Abdução §l0",
                    "  §7Zeus §l0",
                    ""
            ))
            .addEnchant(Enchantment.DURABILITY, 1000, true)
            .addEnchant(Enchantment.DIG_SPEED, 1000, true)
            .addPersistentData(Main.getInstance(), "blizzard.mine.tool", player.getName())
            .build(1);
    }
}
