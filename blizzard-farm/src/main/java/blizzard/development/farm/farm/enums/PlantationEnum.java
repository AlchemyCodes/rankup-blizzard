package blizzard.development.farm.farm.enums;

import org.bukkit.Material;

public enum PlantationEnum {

    CARROTS("Cenoura", Material.CARROTS),
    POTATOES("Batata", Material.POTATOES),
    MELON("Melancia", Material.MELON),
    CACTUS("Cacto", Material.CACTUS);


    private final String name;
    private final Material material;

    PlantationEnum(String name, Material material) {
        this.name = name;
        this.material = material;
    }

    public String getName() {
        return name;
    }

    public Material getMaterial() {
        return material;
    }
}
