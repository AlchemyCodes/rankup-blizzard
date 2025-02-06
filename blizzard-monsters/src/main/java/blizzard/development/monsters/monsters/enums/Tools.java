package blizzard.development.monsters.monsters.enums;

import lombok.Getter;
import org.bukkit.Material;

public @Getter enum Tools {
    SWORD("sword", 0, Material.WOODEN_SWORD, "", 0.0),

    // skins

    WOODEN("wooden", 1, Material.WOODEN_SWORD, "<#a88459>Aniquiladora de<#a88459> <bold><#a88459>Madeira ✂<#a88459></bold>", 1.0),
    STONE("stone", 2, Material.STONE_SWORD, "§8Aniquiladora de §lPedra ✂", 1.2),
    IRON("iron", 3, Material.IRON_SWORD, "§fAniquiladora de §lFerro ✂", 1.5),
    GOLD("gold", 4, Material.GOLDEN_SWORD, "§6Aniquiladora de §lOuro ✂", 1.7),
    DIAMOND("diamond", 5, Material.DIAMOND_SWORD, "§bAniquiladora de §lDiamante ✂", 2.0);

    private final String type;
    private final Integer priority;
    private final Material material;
    private final String display;
    private final Double bonus;

    Tools(String type, Integer priority, Material material, String display, Double bonus) {
        this.type = type;
        this.priority = priority;
        this.material = material;
        this.display = display;
        this.bonus = bonus;
    }
}
