package blizzard.development.spawners.handlers;

import blizzard.development.spawners.handlers.enums.Spawners;
import blizzard.development.spawners.utils.NumberFormat;
import blizzard.development.spawners.utils.items.TextAPI;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.util.Vector;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.plugin.Plugin;
import org.bukkit.entity.Player;
import blizzard.development.spawners.utils.PluginImpl;

public class StaticMobs {
    public static void spawn(Spawners spawnerType, Double amount, Location location) {
        EntityType entityType = getEntityTypeFromSpawner(spawnerType);
        if (entityType == null) return;
        LivingEntity mob = (LivingEntity) location.getWorld().spawnEntity(location, entityType);

        mob.setAI(false);
        mob.setGravity(false);
        mob.customName(TextAPI.parse("§7" + getMobNameByEntity(entityType) + " (x" + NumberFormat.getInstance().formatNumber(amount) + ")"));
        mob.setMetadata("blizzard_spawners-mob", new FixedMetadataValue(PluginImpl.getInstance().plugin, spawnerType));
        mob.setCustomNameVisible(true);
    }

    public static EntityType getEntityTypeFromSpawner(Spawners spawnerType) {
        return switch (spawnerType.getType().toLowerCase()) {
            case "porco" -> EntityType.PIG;
            case "vaca" -> EntityType.COW;
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
}
