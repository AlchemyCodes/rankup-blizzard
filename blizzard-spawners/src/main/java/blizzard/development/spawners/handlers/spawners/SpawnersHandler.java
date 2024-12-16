package blizzard.development.spawners.handlers.spawners;

import blizzard.development.spawners.handlers.enums.spawners.Spawners;
import blizzard.development.spawners.utils.PluginImpl;
import blizzard.development.spawners.utils.SpawnersUtils;
import org.bukkit.Location;
import org.bukkit.block.CreatureSpawner;

import java.util.List;

public class SpawnersHandler {
    private static SpawnersHandler instance;

    private final SpawnersUtils utils = SpawnersUtils.getInstance();

    public void createStaticSpawner(Location spawnerLocation, Spawners spawnerType) {
        if (spawnerLocation.getBlock().getState() instanceof CreatureSpawner creatureSpawner) {
            creatureSpawner.setSpawnedType(utils.getEntityTypeFromSpawner(spawnerType));
            creatureSpawner.update();
        }
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

    public String getItem(String spawnerKey) {
        return PluginImpl.getInstance().Spawners.getConfig().getString("spawners." + spawnerKey + ".item", "SPAWNER");
    }

    public int getSlot(String spawnerKey) {
        return PluginImpl.getInstance().Spawners.getConfig().getInt("spawners." + spawnerKey + ".slot", 0);
    }

    public String getDisplayName(String spawnerKey) {
        return PluginImpl.getInstance().Spawners.getConfig().getString("spawners." + spawnerKey + ".display-name", "§cUnknown Spawner");
    }

    public List<String> getLore(String spawnerKey) {
        return PluginImpl.getInstance().Spawners.getConfig().getStringList("spawners." + spawnerKey + ".lore");
    }

    public boolean isPermittedPurchase(String spawnerKey) {
        return PluginImpl.getInstance().Spawners.getConfig().getBoolean("spawners." + spawnerKey + ".permitted-purchase", false);
    }

    public static SpawnersHandler getInstance() {
        if (instance == null) instance = new SpawnersHandler();
        return instance;
    }
}
