package blizzard.development.fishing.enums;
import lombok.Getter;
import org.bukkit.Material;

@Getter
public enum RodMaterials {

    BAMBOO("Bambu", Material.BAMBOO, 1),
    WOOD("Madeira", Material.STICK, 1.3),
    IRON("Ferro", Material.IRON_INGOT, 1.5),
    CARBON("Carbono", Material.COAL, 1.8);

    private final String fancyName;
    private final Material material;
    private final double bonus;

    RodMaterials(String fancyName, Material material, double bonus) {
        this.fancyName = fancyName;
        this.material = material;
        this.bonus = bonus;
    }
}
