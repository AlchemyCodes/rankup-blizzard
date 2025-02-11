package blizzard.development.core.managers;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import blizzard.development.core.utils.PluginImpl;
import com.comphenix.protocol.wrappers.BlockPosition;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

public class GeneratorManager {
    private static GeneratorManager instance;

    private final Map<Player, Boolean> generators = new HashMap<>();

    public boolean hasGenerator(Player player) {
        return generators.containsKey(player);
    }

    public void activeGenerator(Player player) {
        generators.put(player, true);
    }

    public void deactivateGenerator(Player player) {
        generators.remove(player);
    }

    public boolean isGenerator(String world, double x, double y, double z, double radius) {
        YamlConfiguration config = PluginImpl.getInstance().Coordinates.getConfig();
        ConfigurationSection generatorSection = config.getConfigurationSection("generator");

        if (generatorSection == null) {
            return false;
        }

        for (String key : generatorSection.getKeys(false)) {
            ConfigurationSection section = generatorSection.getConfigurationSection(key);
            if (section == null) continue;

            String genWorld = section.getString("world");
            double genX = section.getDouble("x");
            double genY = section.getDouble("y");
            double genZ = section.getDouble("z");

            if (world.equals(genWorld) && isWithinRadius(x, y, z, genX, genY, genZ, radius)) {
                return true;
            }
        }

        return false;
    }

    private boolean isWithinRadius(double x1, double y1, double z1, double x2, double y2, double z2, double radius) {
        double distanceSquared = Math.pow(x1 - x2, 2) + Math.pow(y1 - y2, 2) + Math.pow(z1 - z2, 2);
        return distanceSquared <= Math.pow(radius, 2);
    }

    public static GeneratorManager getInstance() {
        if (instance == null) {
            instance = new GeneratorManager();
        }
        return instance;
    }
}




