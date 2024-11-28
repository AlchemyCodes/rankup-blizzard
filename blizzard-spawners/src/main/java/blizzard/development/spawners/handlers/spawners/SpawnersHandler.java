package blizzard.development.spawners.handlers.spawners;

import blizzard.development.spawners.handlers.enums.Spawners;
import blizzard.development.spawners.utils.NumberFormat;
import blizzard.development.spawners.utils.PluginImpl;
import blizzard.development.spawners.utils.SpawnersUtils;
import blizzard.development.spawners.utils.items.TextAPI;
import org.bukkit.Location;
import org.bukkit.block.CreatureSpawner;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.metadata.FixedMetadataValue;

import java.util.HashMap;
import java.util.Map;

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

    public Map<String, Double> getSpawnerInfo(String spawnerKey) {
        ConfigurationSection section = PluginImpl.getInstance().Spawners.getConfig().getConfigurationSection("spawners." + spawnerKey);
        if (section == null) {
            return new HashMap<>();
        }

        Map<String, Double> spawnerData = new HashMap<>();
        spawnerData.put("buy-price", section.getDouble("buy-price", 0.0));
        spawnerData.put("sell-drop-price", section.getDouble("sell-drop-price", 0.0));

        return spawnerData;
    }

    public double getBuyPrice(String spawnerKey) {
        return PluginImpl.getInstance().Spawners.getConfig().getDouble("spawners." + spawnerKey + ".buy-price", 0.0);
    }

    public double getSellDropPrice(String spawnerKey) {
        return PluginImpl.getInstance().Spawners.getConfig().getDouble("spawners." + spawnerKey + ".sell-drop-price", 0.0);
    }

    public boolean isSpawnerValid(String spawnerKey) {
        return PluginImpl.getInstance().Spawners.getConfig().contains("spawners." + spawnerKey);
    }

    public static SpawnersHandler getInstance() {
        if (instance == null) instance = new SpawnersHandler();
        return instance;
    }
}
