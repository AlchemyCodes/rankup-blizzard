package blizzard.development.mine.mine.enums;

import org.bukkit.Material;

public enum ToolEnum {

    TOOL("pickaxe", 0, Material.WOODEN_PICKAXE, "", "", 1.0),

    // Skins

    WOODEN("wooden", 1, Material.WOODEN_PICKAXE, "<#a88459>Picareta de<#a88459> <bold><#a88459>Madeira<#a88459></bold> ", "<#a88459>", 1.0),
    STONE("stone", 2, Material.STONE_PICKAXE, "§8Picareta de §lPedra ", "§8", 1.2),
    IRON("iron", 3, Material.IRON_PICKAXE, "§fPicareta de §lFerro ", "§f", 1.5),
    GOLD("gold", 4, Material.GOLDEN_PICKAXE, "§6Picareta de §lOuro ", "§6", 1.7),
    DIAMOND("diamond", 5, Material.DIAMOND_PICKAXE, "§bPicareta de §lDiamante ", "§b", 2.0);

    private final String type;
    private final Integer priority;
    private final Material material;
    private final String display;
    private final String color;
    private final Double bonus;

    ToolEnum(String type, Integer priority, Material material, String display, String color, Double bonus) {
        this.type = type;
        this.priority = priority;
        this.material = material;
        this.display = display;
        this.color = color;
        this.bonus = bonus;
    }

    public String getType() {
        return type;
    }

    public Integer getPriority() {
        return priority;
    }

    public Material getMaterial() {
        return material;
    }

    public String getDisplay() {
        return display;
    }

    public String getColor() {
        return color;
    }

    public Double getBonus() {
        return bonus;
    }
}
