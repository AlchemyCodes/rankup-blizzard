package blizzard.development.spawners.handlers;

import blizzard.development.spawners.handlers.enums.Spawners;
import org.bukkit.Location;
import org.bukkit.block.CreatureSpawner;
import org.bukkit.entity.EntityType;

public class StaticSpawners {
    public static void create(Location spawnerLocation, Spawners spawnerType) {
        if (spawnerLocation.getBlock().getState() instanceof CreatureSpawner creatureSpawner) {
            creatureSpawner.setSpawnedType(getEntityTypeFromSpawner(spawnerType));
            creatureSpawner.update();
        }
    }

    private static EntityType getEntityTypeFromSpawner(Spawners spawnerType) {
        return switch (spawnerType.getType().toLowerCase()) {
            case "porco" -> EntityType.PIG;
            case "vaca" -> EntityType.COW;
            default -> null;
        };
    }
}
