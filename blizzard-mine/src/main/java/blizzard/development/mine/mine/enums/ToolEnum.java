package blizzard.development.mine.mine.enums;

import org.bukkit.Material;

public enum ToolEnum {

    TOOL("Picareta", Material.WOODEN_PICKAXE),

    // Skins da picareta;

    WOODEN("Madeira", Material.WOODEN_HOE),
    STONE("Pedra", Material.STONE_HOE),
    IRON("Ferro", Material.IRON_HOE),
    GOLD("Ouro", Material.GOLDEN_HOE),
    DIAMOND("Diamante", Material.DIAMOND_HOE);

    private final String type;
    private final Material material;

    ToolEnum(String type, Material material) {
        this.type = type;
        this.material = material;
    }

    public String getType() {
        return type;
    }

    public Material getMaterial() {
        return material;
    }
}
