package blizzard.development.excavation.excavation.item;

import blizzard.development.excavation.Main;
import blizzard.development.excavation.builder.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;

public class ExcavatorBuildItem {

    public ItemStack buildExcavator() {
        return new ItemBuilder(Material.DIAMOND_SHOVEL)
                .setDisplayName("<#34f2f5>Escavadora<#34f2f5> §7[§l21M§7]")
                .setLore(Arrays.asList(
                        "§7Utilize esta escavadora",
                        "§7para coletar recursos.",
                        "",
                        " <#1bf4f7>Encantamentos:<#0ec9cc>",
                        "  §7Eficiência §l5",
                        "  §7Durabilidade §l∞",
                        "  §7Agilidade §l5",
                        "  §7Extrator §l2",
                        "",
                        " §bOs encantamentos são",
                        " §bupados automaticamente.",
                        ""
                ))
                .addEnchant(Enchantment.DIG_SPEED, 100, true)
                .addPersistentData(Main.getInstance(), "excavator", "excavator.tool")
                .build();
    }
}
