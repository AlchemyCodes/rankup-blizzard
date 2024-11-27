package blizzard.development.spawners.utils;

import blizzard.development.spawners.database.cache.getters.SpawnersCacheGetters;
import blizzard.development.spawners.handlers.enchantments.EnchantmentsHandler;
import blizzard.development.spawners.handlers.enums.Spawners;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;

public class SpawnersUtils {
    private static SpawnersUtils instance;

    public EntityType getEntityTypeFromSpawner(Spawners spawnerType) {
        return switch (spawnerType.getType().toLowerCase()) {
            case "porco" -> EntityType.PIG;
            case "vaca" -> EntityType.COW;
            default -> null;
        };
    }

    public Spawners getSpawnerFromName(String name) {
        return switch (name.toLowerCase()) {
            case "porco" -> Spawners.PIG;
            case "vaca" -> Spawners.COW;
            default -> null;
        };
    }

    public String getMobNameByEntity(EntityType entityType) {
        if (entityType.equals(EntityType.PIG)) {
            return "Porco";
        } else if (entityType.equals(EntityType.COW)) {
            return "Vaca";
        }
        return null;
    }

    public String getMobNameBySpawner(Spawners spawner) {
        if (spawner.equals(Spawners.PIG)) {
            return "Porco";
        } else if (spawner.equals(Spawners.COW)) {
            return "Vaca";
        }
        return null;
    }

    public boolean isPickaxe(Material material) {
        return material == Material.WOODEN_PICKAXE
                || material == Material.STONE_PICKAXE
                || material == Material.IRON_PICKAXE
                || material == Material.GOLDEN_PICKAXE
                || material == Material.DIAMOND_PICKAXE
                || material == Material.NETHERITE_PICKAXE;
    }

    public static SpawnersUtils getInstance() {
        if (instance == null) instance = new SpawnersUtils();
        return instance;
    }
}
