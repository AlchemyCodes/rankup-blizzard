package blizzard.development.plantations.plantations.enums;

import org.bukkit.Material;

public enum ToolsEnum {

    TOOL("cultivadora", Material.WOODEN_HOE, "", ""),

    // Skins
    WOODEN("Madeira", Material.WOODEN_HOE, "<#a88459>", "1.0§lx"),
    STONE("Pedra", Material.STONE_HOE, "§8", "1.1§lx"),
    IRON("Ferro", Material.IRON_HOE, "§f", "1.9§lx"),
    GOLD("Ouro", Material.GOLDEN_HOE, "§6", "2.6§lx"),
    DIAMOND("Diamante", Material.DIAMOND_HOE, "§b", "3.3§lx");

    private final String type;
    private final Material material;
    private final String colorCode;
    private final String bonus;

    ToolsEnum(String type, Material material, String colorCode, String bonus) {
        this.type = type;
        this.material = material;
        this.colorCode = colorCode;
        this.bonus = bonus;
    }

    public String getType() {
        return type;
    }

    public Material getMaterial() {
        return material;
    }

    public String getColorCode() {
        return colorCode;
    }

    public String getBonus() {
        return bonus;
    }
}
