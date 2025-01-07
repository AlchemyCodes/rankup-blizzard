package blizzard.development.core.managers;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import blizzard.development.core.Main;
import blizzard.development.core.utils.PluginImpl;
import com.comphenix.protocol.wrappers.BlockPosition;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class GeneratorManager {
    private static final Map<Player, BlockPosition> generators = new HashMap<>();
    private static final Map<Player, Boolean> readyGenerators = new ConcurrentHashMap<>();

    public static boolean hasGenerator(Player player) {
        return generators.containsKey(player);
    }

    public static void placeGenerator(Player player, BlockPosition position) {
        generators.put(player, position);

        readyGenerators.put(player, false);

        new BukkitRunnable() {
            @Override
            public void run() {
                readyGenerators.put(player, true);
            }
        }.runTaskLater(Main.getInstance(), 60L);
    }

    public static boolean isGeneratorReady(Player player) {
        return readyGenerators.getOrDefault(player, false);
    }

    public static void removeGenerator(Player player) {
        generators.remove(player);
    }

    public static boolean isPacketGenerator(int x, int y, int z) {
        int radius = 12;
        return generators.values().stream().anyMatch(pos -> {
            int dx = pos.getX() - x;
            int dy = pos.getY() - y;
            int dz = pos.getZ() - z;

            return dx * dx + dy * dy + dz * dz <= radius * radius;
        });
    }


    public static boolean isBlockLegit(String world, int x, int y, int z) {
        YamlConfiguration config = PluginImpl.getInstance().Coordinates.getConfig();
        ConfigurationSection campfireSection = config.getConfigurationSection("generator");

        if (campfireSection == null) {
            return false;
        }

        Set<String> locations = campfireSection.getKeys(false);

        for (String location : locations) {
            ConfigurationSection locationSection = campfireSection.getConfigurationSection(location);
            if (locationSection == null) continue;

            String locationWorld = locationSection.getString("world");
            int locationX = locationSection.getInt("x");
            int locationY = locationSection.getInt("y");
            int locationZ = locationSection.getInt("z");

            if (world.equals(locationWorld) && x == locationX && y == locationY && z == locationZ) {
                return true;
            }
        }

        return false;
    }

}




