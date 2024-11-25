package blizzard.development.spawners.handlers;

import blizzard.development.spawners.handlers.enums.Spawners;
import blizzard.development.spawners.utils.NumberFormat;
import blizzard.development.spawners.utils.SpawnersUtils;
import blizzard.development.spawners.utils.items.TextAPI;
import org.bukkit.Location;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.metadata.FixedMetadataValue;
import blizzard.development.spawners.utils.PluginImpl;

public class StaticMobs {
    public static void spawn(Spawners spawnerType, Double amount, Location location) {
        SpawnersUtils utils = SpawnersUtils.getInstance();

        EntityType entityType = utils.getEntityTypeFromSpawner(spawnerType);
        if (entityType == null) return;

        Location spawnLocation = location.clone();

        LivingEntity mob = (LivingEntity) spawnLocation.getWorld().spawnEntity(spawnLocation, entityType);

        mob.setAI(false);
        mob.setGravity(false);
        mob.teleport(spawnLocation);
        mob.customName(TextAPI.parse("§7" + utils.getMobNameByEntity(entityType) + " (x" + NumberFormat.getInstance().formatNumber(amount) + ")"));
        mob.setMetadata("blizzard_spawners-mob", new FixedMetadataValue(PluginImpl.getInstance().plugin, spawnerType));
        mob.setCustomNameVisible(true);
    }
}
