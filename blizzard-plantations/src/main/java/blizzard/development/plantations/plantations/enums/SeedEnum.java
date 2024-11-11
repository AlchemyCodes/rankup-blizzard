package blizzard.development.plantations.plantations.enums;

import blizzard.development.plantations.Main;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataType;

public enum SeedEnum {

    POTATO("batata", "semente.batata", Material.POTATOES),
    CARROT("cenoura", "semente.cenoura", Material.CARROTS),
    CORN("milho", "semente.milho", Material.WHEAT),
    TOMATO("tomate", "semente.tomate", Material.BEETROOTS);

    private final String name;
    private final String persistentKey;
    private final Material material;

    SeedEnum(String name, String persistentKey, Material material) {
        this.name = name;
        this.persistentKey = persistentKey;
        this.material = material;
    }

    public String getName() {
        return name;
    }

    public String getPersistentKey() {
        return persistentKey;
    }

    public Material getMaterial() {
        return material;
    }

    public static SeedEnum getByPersistentData(ItemStack item, Main plugin) {
        for (SeedEnum seed : values()) {
            if (item != null && item.hasItemMeta() &&
                    item.getItemMeta().getPersistentDataContainer().has(new NamespacedKey(plugin, seed.persistentKey), PersistentDataType.STRING)) {
                return seed;
            }
        }
        return null;
    }
}
