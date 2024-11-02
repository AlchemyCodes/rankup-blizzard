package blizzard.development.plantations.plantations.item;

import blizzard.development.plantations.Main;
import blizzard.development.plantations.builder.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;

public class PlowingToolBuildItem {

    public static ItemStack plowingTool(int amount) {
        return new ItemBuilder(Material.WOODEN_HOE)
                .setDisplayName("<#a88459>Ferramenta de Arar<#a88459>")
                .setLore(Arrays.asList(
                        "§7Use esta ferramenta para",
                        "§7arar o solo na sua estufa.",
                        "",
                        " <#a88459>Encantamentos:<#a88459>",
                        "  §7Durabilidade §l∞",
                        "  §7Acelerador §l1",
                        "  §7Arador §l1",
                        "",
                        " <#a88459>Os encantamentos são<#a88459>",
                        " <#a88459>upados automaticamente.<#a88459>",
                        ""
                ))
                .addEnchant(Enchantment.DURABILITY, 1000, true)
                .addPersistentData(Main.getInstance(), "ferramenta", "ferramenta.arar")
                .build(amount);
    }
}
