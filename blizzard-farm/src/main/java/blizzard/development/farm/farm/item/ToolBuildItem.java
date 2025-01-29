package blizzard.development.farm.farm.item;

import blizzard.development.farm.builders.item.ItemBuilder;
import blizzard.development.farm.database.cache.methods.ToolCacheMethod;
import blizzard.development.farm.utils.NumberFormat;
import blizzard.development.farm.utils.PluginImpl;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;
import java.util.UUID;

public class ToolBuildItem {

    public static ItemStack tool(Player player) {
        String uuid = UUID.randomUUID().toString().substring(0, 8);

        ToolCacheMethod.getInstance().createTool(
            player,
            uuid,
            0,
            0
        );

        return new ItemBuilder(Material.DIAMOND_AXE)
            .setDisplayName("§bMachado §7[0]")
            .setLore(Arrays.asList(
                "§7Use esta ferramenta para ",
                "§7quebrar suas plantações.",
                "",
                " §3Encantamentos:",
                "  §7Durabilidade §l∞",
                "  §7Eficiência §l∞",
                "  §7Fortuna §l0",
                "",
                " §bOs encantamentos são",
                " §bupados automaticamente.",
                ""
            ))
            .addEnchant(Enchantment.DURABILITY, 1000, true)
            .addEnchant(Enchantment.DIG_SPEED, 1000, true)
            .addPersistentData(PluginImpl.getInstance().plugin, "blizzard.farm.tool", player.getName())
            .addPersistentData(PluginImpl.getInstance().plugin, "ferramenta.farm.id", uuid)
            .build(1);
    }

    public static ItemStack tool(Player player, String uuid, int plantations, int fortune) {

        return new ItemBuilder(Material.DIAMOND_AXE)
            .setDisplayName("§bMachado §7[" + NumberFormat.formatNumber(plantations) +"]")
            .setLore(Arrays.asList(
                "§7Use esta ferramenta para ",
                "§7quebrar suas plantações.",
                "",
                " §3Encantamentos:",
                "  §7Durabilidade §l∞",
                "  §7Eficiência §l∞",
                "  §7Fortuna §l" + NumberFormat.formatNumber(fortune),
                "",
                " §bOs encantamentos são",
                " §bupados automaticamente.",
                ""
            ))
            .addEnchant(Enchantment.DURABILITY, 1000, true)
            .addEnchant(Enchantment.DIG_SPEED, 1000, true)
            .addPersistentData(PluginImpl.getInstance().plugin, "blizzard.farm.tool", player.getName())
            .addPersistentData(PluginImpl.getInstance().plugin, "ferramenta.farm.id", uuid)
            .build(1);
    }
}
