package blizzard.development.core.managers;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

import blizzard.development.core.utils.PluginImpl;
import com.comphenix.protocol.wrappers.BlockPosition;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

public class CampfireManager {
    private static final Map<Player, BlockPosition> virtualCampfires = new HashMap<>();

    public static boolean hasCampfire(Player player) {
        return virtualCampfires.containsKey(player);
    }

    public static void placeCampfire(Player player, BlockPosition position) {
        virtualCampfires.put(player, position);
    }

    public static boolean isPacketCampfire(int x, int y, int z) {
        return virtualCampfires.values().stream()
                .anyMatch(pos -> pos.getX() == x && pos.getY() == y && pos.getZ() == z);
    }

    public static void removeCampfire(Player player) {
        virtualCampfires.remove(player);
    }

    public static boolean isBlockLegit(String world, int x, int y, int z) {
        YamlConfiguration config = PluginImpl.getInstance().Coordinates.getConfig();
        ConfigurationSection campfireSection = config.getConfigurationSection("campfire");

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




