package blizzard.development.spawners.handlers;

import blizzard.development.spawners.handlers.enums.Spawners;
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

    public static void spawnStaticMob(Player player, Spawners spawnerType, Location location) {
        EntityType entityType = getEntityTypeFromSpawner(spawnerType);
        if (entityType == null) return;
        LivingEntity mob = (LivingEntity) location.getWorld().spawnEntity(location, entityType);

        mob.setAI(false);
        mob.setGravity(false);
        mob.setRotation(player.getLocation().getYaw(), player.getLocation().getPitch());
        mob.customName(TextAPI.parse("§8" + spawnerType));
        mob.setCustomNameVisible(true);

        Location mobLocation = mob.getLocation();
        Location playerLocation = player.getLocation();
        double dx = playerLocation.getX() - mobLocation.getX();
        double dz = playerLocation.getZ() - mobLocation.getZ();
        float yaw = (float) Math.toDegrees(Math.atan2(-dx, dz));
        mob.setRotation(yaw, 0);
    }

    private static EntityType getEntityTypeFromSpawner(Spawners spawnerType) {
        return switch (spawnerType.getType().toLowerCase()) {
            case "porco" -> EntityType.PIG;
            case "vaca" -> EntityType.COW;
            default -> null;
        };
    }
}
