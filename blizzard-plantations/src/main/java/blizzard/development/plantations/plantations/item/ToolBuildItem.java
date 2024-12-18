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

public class ToolBuildItem {

    public static ItemStack tool(Player player, int amount) {
        String id = UUID.randomUUID().toString().substring(0, 8);
        ToolCacheMethod.createTool(
            player,
            id,
            ToolsEnum.TOOL.toString(),
            0,
            0,
            0,
            0,
            0,
            0
        );

        return new ItemBuilder(Material.GOLDEN_HOE)
            .setDisplayName("§6Cultivadora")
            .setLore(Arrays.asList(
                "§7Use esta ferramenta para",
                "§7cultivar plantações.",
                "",
                " §6Encantamentos:",
                "  §7Durabilidade §l∞",
                "  §7Agilidade §l0",
                "  §7Botânico §l0",
                "  §7Explosão §l0",
                "  §7Trovoada §l0",
                "  §7Raio-X §l0",
                "",
                "§6Pressione shift + b. direito."
            ))
            .addEnchant(Enchantment.DURABILITY, 1000, true)
            .addPersistentData(Main.getInstance(), "ferramenta", "ferramenta.cultivar")
            .addPersistentData(Main.getInstance(), "ferramenta-id", id)
            .build(amount);
    }

    public static ItemStack tool(String id, int blocks, int botany, int agility, int explosion, int thunderstorm, int xray, int amount) {
        return new ItemBuilder(Material.GOLDEN_HOE)
            .setDisplayName("§6Cultivadora §7[" + formatNumber(blocks) + "]")
            .setLore(Arrays.asList(
                "§7Use esta ferramenta para",
                "§7cultivar plantações.",
                "",
                " §6Encantamentos:",
                "  §7Durabilidade §l∞",
                "  §7Agilidade §l" + formatNumber(agility),
                "  §7Botânico §l" + formatNumber(botany),
                "  §7Explosão §l" + formatNumber(explosion),
                "  §7Trovoada §l " + formatNumber(thunderstorm),
                "  §7Raio-X §l " + formatNumber(xray),
                "",
                "§6Pressione shift + b. direito."
            ))
            .addEnchant(Enchantment.DURABILITY, 1000, true)
            .addPersistentData(Main.getInstance(), "ferramenta", "ferramenta.cultivar")
            .addPersistentData(Main.getInstance(), "ferramenta-id", id)
            .build(amount);
    }
}
