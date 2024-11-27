package blizzard.development.spawners.handlers.spawners;

import blizzard.development.spawners.handlers.enums.Spawners;
import blizzard.development.spawners.utils.NumberFormat;
import blizzard.development.spawners.utils.PluginImpl;
import blizzard.development.spawners.utils.SpawnersUtils;
import blizzard.development.spawners.utils.items.TextAPI;
import org.bukkit.Location;
import org.bukkit.block.CreatureSpawner;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.metadata.FixedMetadataValue;

public class SpawnersHandler {
    private static SpawnersHandler instance;

    private final SpawnersUtils utils = SpawnersUtils.getInstance();

    public void createStaticSpawner(Location spawnerLocation, Spawners spawnerType) {
        if (spawnerLocation.getBlock().getState() instanceof CreatureSpawner creatureSpawner) {
            creatureSpawner.setSpawnedType(utils.getEntityTypeFromSpawner(spawnerType));
            creatureSpawner.update();
        }
    }

    public void spawnStaticMob(Spawners spawnerType, Double amount, Location location) {
        EntityType entityType = utils.getEntityTypeFromSpawner(spawnerType);
        if (entityType == null) return;

        LivingEntity mob = (LivingEntity) location.getWorld().spawnEntity(location, entityType);

        mob.setAI(false);
        mob.setGravity(false);
        mob.teleport(location);
        mob.customName(TextAPI.parse("§7" + utils.getMobNameByEntity(entityType) + " (x" + NumberFormat.getInstance().formatNumber(amount) + ")"));
        mob.setMetadata("blizzard_spawners-mob", new FixedMetadataValue(PluginImpl.getInstance().plugin, spawnerType));
        mob.setMetadata("blizzard_spawners-id", new FixedMetadataValue(PluginImpl.getInstance().plugin, spawnerType));
        mob.setCustomNameVisible(true);
    }

    public static SpawnersHandler getInstance() {
        if (instance == null) instance = new SpawnersHandler();
        return instance;
    }
}
