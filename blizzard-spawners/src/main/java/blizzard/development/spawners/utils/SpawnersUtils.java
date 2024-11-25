package blizzard.development.spawners.utils;

import blizzard.development.spawners.handlers.enums.Spawners;
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

    public static String getMobNameByEntity(EntityType entityType) {
        if (entityType.equals(EntityType.PIG)) {
            return "Porco";
        } else if (entityType.equals(EntityType.COW)) {
            return "Vaca";
        }
        return null;
    }

    public static SpawnersUtils getInstance() {
        if (instance == null) instance = new SpawnersUtils();
        return instance;
    }
}
