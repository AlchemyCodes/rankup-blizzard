package blizzard.development.fishing.enums;
import lombok.Getter;

@Getter
public enum RodMaterials {

    BAMBOO("Bambu", 1),
    WOOD("Madeira", 1.3),
    IRON("Ferro", 1.5),
    CARBON("Carbono", 1.8);

    private final String fancyName;
    private final double bonus;

    RodMaterials(String fancyName, double bonus) {
        this.fancyName = fancyName;
        this.bonus = bonus;
    }
}
