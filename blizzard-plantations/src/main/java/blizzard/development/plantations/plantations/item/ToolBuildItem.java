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
import java.util.List;
import java.util.UUID;

import static blizzard.development.plantations.utils.NumberFormat.formatNumber;

public class ToolBuildItem {

    public static ItemStack tool(Player player, int amount) {
        String id = UUID.randomUUID().toString().substring(0, 8);
        ToolCacheMethod.createTool(
            player,
            id,
            ToolsEnum.TOOL.toString(),
            ToolsEnum.WOODEN.toString(),
            0,
            1,
            0,
            1,
            1,
            1,
            1
        );

        return new ItemBuilder(Material.WOODEN_HOE)
            .setDisplayName("<#a88459>Cultivadora de<#a88459> <bold><#a88459>Madeira<#a88459></bold><#a88459>")
            .setLore(Arrays.asList(
                "§7Use esta ferramenta para",
                "§7cultivar plantações.",
                "",
                " <#a88459>Encantamentos:<#a88459>",
                "  §7Durabilidade §l∞",
                "  §7Agilidade §l0",
                "  §7Botânico §l1",
                "  §7Explosão §l1",
                "  §7Trovoada §l1",
                "  §7Nevasca §l1",
                "  §7Raio-X §l1",
                "",
                "<#a88459>Pressione shift + b. direito.<#a88459>"
            ))
            .addEnchant(Enchantment.DURABILITY, 1000, true)
            .addPersistentData(Main.getInstance(), "ferramenta", "ferramenta.cultivar")
            .addPersistentData(Main.getInstance(), "ferramenta-id", id)
            .build(amount);
    }

    public static ItemStack tool(String id, int blocks, int botany, int agility, int explosion, int thunderstorm, int xray, int blizzard, int amount) {
        String skin = ToolCacheMethod.getInstance().getSkin(id);
        ToolsEnum tool = ToolsEnum.valueOf(skin);

        String displayName = tool.getColorCode() + "Cultivadora de §l" + tool.getType() + " §7[" + formatNumber(blocks) + "]";
        List<String> lore = Arrays.asList(
            "§7Use esta ferramenta para",
            "§7cultivar plantações.",
            "",
            "  §fSkin: " + tool.getColorCode() + tool.getType(),
            "  §fBônus: " + tool.getColorCode() + tool.getBonus(),
            "",
            " " + tool.getColorCode() + "Encantamentos:",
            "  §7Durabilidade §l∞",
            "  §7Agilidade §l" + formatNumber(agility),
            "  §7Botânico §l" + formatNumber(botany),
            "  §7Explosão §l" + formatNumber(explosion),
            "  §7Trovoada §l" + formatNumber(thunderstorm),
            "  §7Nevasca §l" + formatNumber(blizzard),
            "  §7Raio-X §l" + formatNumber(xray),
            "",
            tool.getColorCode() + "Pressione shift + b. direito." + tool.getColorCode()
        );

        return new ItemBuilder(tool.getMaterial())
            .setDisplayName(displayName)
            .setLore(lore)
            .addEnchant(Enchantment.DURABILITY, 1000, true)
            .addPersistentData(Main.getInstance(), "ferramenta", "ferramenta.cultivar")
            .addPersistentData(Main.getInstance(), "ferramenta-id", id)
            .build(amount);
    }


//    public static ItemStack tool(String id, int blocks, int botany, int agility, int explosion, int thunderstorm, int xray, int blizzard, int amount) {
//        String skin = ToolCacheMethod.getInstance().getSkin(id);
//        ToolsEnum tool = ToolsEnum.valueOf(skin);
//
//        switch (tool) {
//            case WOODEN -> {
//                return new ItemBuilder(tool.getMaterial())
//                    .setDisplayName("<#a88459>Cultivadora de <bold><#a88459>Madeira<#a88459></bold><#a88459> §7[" + formatNumber(blocks) + "]")
//                    .setLore(Arrays.asList(
//                        "§7Use esta ferramenta para",
//                        "§7cultivar plantações.",
//                        "",
//                        " §6Encantamentos:",
//                        "  §7Durabilidade §l∞",
//                        "  §7Agilidade §l" + formatNumber(agility),
//                        "  §7Botânico §l" + formatNumber(botany),
//                        "  §7Explosão §l" + formatNumber(explosion),
//                        "  §7Trovoada §l" + formatNumber(thunderstorm),
//                        "  §7Nevasca §l" + formatNumber(blizzard),
//                        "  §7Raio-X §l" + formatNumber(xray),
//                        "",
//                        "<#a88459>Pressione shift + b. direito.<#a88459>"
//                    ))
//                    .addEnchant(Enchantment.DURABILITY, 1000, true)
//                    .addPersistentData(Main.getInstance(), "ferramenta", "ferramenta.cultivar")
//                    .addPersistentData(Main.getInstance(), "ferramenta-id", id)
//                    .build(amount);
//            }
//            case STONE -> {
//                return new ItemBuilder(tool.getMaterial())
//                    .setDisplayName("§8Cultivadora de §lPedra §7[" + formatNumber(blocks) + "]")
//                    .setLore(Arrays.asList(
//                        "§7Use esta ferramenta para",
//                        "§7cultivar plantações.",
//                        "",
//                        "  §fSkin: §8Pedra",
//                        "  §fBônus: §81.1§lx",
//                        "",
//                        " §6Encantamentos:",
//                        "  §7Durabilidade §l∞",
//                        "  §7Agilidade §l" + formatNumber(agility),
//                        "  §7Botânico §l" + formatNumber(botany),
//                        "  §7Explosão §l" + formatNumber(explosion),
//                        "  §7Trovoada §l" + formatNumber(thunderstorm),
//                        "  §7Nevasca §l" + formatNumber(blizzard),
//                        "  §7Raio-X §l" + formatNumber(xray),
//                        "",
//                        "§8Pressione shift + b. direito."
//                    ))
//                    .addEnchant(Enchantment.DURABILITY, 1000, true)
//                    .addPersistentData(Main.getInstance(), "ferramenta", "ferramenta.cultivar")
//                    .addPersistentData(Main.getInstance(), "ferramenta-id", id)
//                    .build(amount);
//            }
//            case IRON -> {
//                return new ItemBuilder(tool.getMaterial())
//                    .setDisplayName("§fCultivadora de §lFerro §7[" + formatNumber(blocks) + "]")
//                    .setLore(Arrays.asList(
//                        "§7Use esta ferramenta para",
//                        "§7cultivar plantações.",
//                        "",
//                        "  §fSkin: §7Ferro",
//                        "  §fBônus: §f1.9§lx",
//                        "",
//                        " §6Encantamentos:",
//                        "  §7Durabilidade §l∞",
//                        "  §7Agilidade §l" + formatNumber(agility),
//                        "  §7Botânico §l" + formatNumber(botany),
//                        "  §7Explosão §l" + formatNumber(explosion),
//                        "  §7Trovoada §l" + formatNumber(thunderstorm),
//                        "  §7Nevasca §l" + formatNumber(blizzard),
//                        "  §7Raio-X §l" + formatNumber(xray),
//                        "",
//                        "§fPressione shift + b. direito."
//                    ))
//                    .addEnchant(Enchantment.DURABILITY, 1000, true)
//                    .addPersistentData(Main.getInstance(), "ferramenta", "ferramenta.cultivar")
//                    .addPersistentData(Main.getInstance(), "ferramenta-id", id)
//                    .build(amount);
//            }
//            case GOLD -> {
//                return new ItemBuilder(tool.getMaterial())
//                    .setDisplayName("§6Cultivadora de §lOuro §7[" + formatNumber(blocks) + "]")
//                    .setLore(Arrays.asList(
//                        "§7Use esta ferramenta para",
//                        "§7cultivar plantações.",
//                        "",
//                        "  §fSkin: §6Ouro",
//                        "  §fBônus: §62.6§lx",
//                        "",
//                        " §6Encantamentos:",
//                        "  §7Durabilidade §l∞",
//                        "  §7Agilidade §l" + formatNumber(agility),
//                        "  §7Botânico §l" + formatNumber(botany),
//                        "  §7Explosão §l" + formatNumber(explosion),
//                        "  §7Trovoada §l" + formatNumber(thunderstorm),
//                        "  §7Nevasca §l" + formatNumber(blizzard),
//                        "  §7Raio-X §l" + formatNumber(xray),
//                        "",
//                        "§6Pressione shift + b. direito."
//                    ))
//                    .addEnchant(Enchantment.DURABILITY, 1000, true)
//                    .addPersistentData(Main.getInstance(), "ferramenta", "ferramenta.cultivar")
//                    .addPersistentData(Main.getInstance(), "ferramenta-id", id)
//                    .build(amount);
//            }
//            case DIAMOND -> {
//                return new ItemBuilder(tool.getMaterial())
//                    .setDisplayName("§bCultivadora de §lDiamante §7[" + formatNumber(blocks) + "]")
//                    .setLore(Arrays.asList(
//                        "§7Use esta ferramenta para",
//                        "§7cultivar plantações.",
//                        "",
//                        "  §fSkin: §bDiamante",
//                        "  §fBônus: §b3.3§lx",
//                        "",
//                        " §6Encantamentos:",
//                        "  §7Durabilidade §l∞",
//                        "  §7Agilidade §l" + formatNumber(agility),
//                        "  §7Botânico §l" + formatNumber(botany),
//                        "  §7Explosão §l" + formatNumber(explosion),
//                        "  §7Trovoada §l" + formatNumber(thunderstorm),
//                        "  §7Nevasca §l" + formatNumber(blizzard),
//                        "  §7Raio-X §l" + formatNumber(xray),
//                        "",
//                        "§6Pressione shift + b. direito."
//                    ))
//                    .addEnchant(Enchantment.DURABILITY, 1000, true)
//                    .addPersistentData(Main.getInstance(), "ferramenta", "ferramenta.cultivar")
//                    .addPersistentData(Main.getInstance(), "ferramenta-id", id)
//                    .build(amount);
//            }
//        }
//        return null;
//    }
}
