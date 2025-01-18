package blizzard.development.mine.mine.item;

import blizzard.development.core.Main;
import blizzard.development.mine.builders.item.ItemBuilder;
import blizzard.development.mine.database.cache.ToolCacheManager;
import blizzard.development.mine.database.storage.ToolData;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;

public class ToolBuildItem {
    public static ItemStack tool(Player player) {

        ToolData toolData = ToolCacheManager.getInstance().getToolData(player);

        return new ItemBuilder(Material.WOODEN_PICKAXE) // usar o sistema de skin dps tlg
                .setDisplayName("<#a88459>Picareta de<#a88459> <bold><#a88459>Madeira<#a88459></bold><#a88459> §7[" + toolData.getBlocks() + "§7]")
                .setLore(Arrays.asList(
                        "§7Essa é uma picareta (definitivamente)"
                ))
                .addEnchant(Enchantment.DURABILITY, 1000, true)
                .addEnchant(Enchantment.DIG_SPEED, 1000, true)
                .addPersistentData(Main.getInstance(), "blizzard.mine.tool", player.getName())
                .build(1);
    }
}
