package blizzard.development.plantations.plantations.item;

import blizzard.development.plantations.Main;
import blizzard.development.plantations.builder.ItemBuilder;
import blizzard.development.plantations.database.cache.methods.ToolCacheMethod;
import blizzard.development.plantations.plantations.enums.ToolsEnum;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;
import java.util.UUID;

import static blizzard.development.plantations.utils.NumberFormat.formatNumber;

public class PlowingToolBuildItem {

    public static ItemStack plowingTool(Player player, int amount) {
        String id = UUID.randomUUID().toString().substring(0, 5);
        ToolCacheMethod.createTool(
                player,
                id,
                ToolsEnum.PLOWING.toString(),
                0,
                0
        );

        return new ItemBuilder(Material.WOODEN_HOE)
                .setDisplayName("<#a88459>Ferramenta de Arar<#a88459>")
                .setLore(Arrays.asList(
                        "§7Use esta ferramenta para",
                        "§7arar o solo na sua estufa.",
                        "",
                        " <#a88459>Encantamentos:<#a88459>",
                        "  §7Durabilidade §l∞",
                        "  §7Acelerador §l0",
                        "  §7Arador §l0",
                        "",
                        " <#a88459>Os encantamentos são<#a88459>",
                        " <#a88459>upados automaticamente.<#a88459>",
                        ""
                ))
                .addEnchant(Enchantment.DURABILITY, 1000, true)
                .addPersistentData(Main.getInstance(), "ferramenta.arar", "ferramenta.arar")
                .addPersistentData(Main.getInstance(), "ferramenta.arar-id", id)
                .build(amount);
    }

    public static ItemStack plowingTool(String id, int accelerator, int plow) {
        return new ItemBuilder(Material.GOLDEN_HOE)
                .setDisplayName("<#a88459>Ferramenta de Arar<#a88459>")
                .setLore(Arrays.asList(
                        "§7Use esta ferramenta para",
                        "§7arar o solo na sua estufa.",
                        "",
                        " <#a88459>Encantamentos:<#a88459>",
                        "  §7Durabilidade §l∞",
                        "  §7Acelerador §l" + formatNumber(accelerator),
                        "  §7Arador §l" + formatNumber(plow),
                        "",
                        " <#a88459>Os encantamentos são<#a88459>",
                        " <#a88459>upados automaticamente.<#a88459>",
                        ""
                ))
                .addEnchant(Enchantment.DURABILITY, 1000, true)
                .addPersistentData(Main.getInstance(), "ferramenta", "ferramenta.cultivar")
                .addPersistentData(Main.getInstance(), "ferramenta-id", id)
                .build(1);
    }
}
