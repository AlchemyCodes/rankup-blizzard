package blizzard.development.core.managers;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

public class CampfireManager {
    private static final Map<Player, Location> playerCampfireMap = new ConcurrentHashMap<>();

    public static void placeCampfire(Player player, Location location) {
        playerCampfireMap.put(player, location);
    }

    public static void removeCampfire(Player player) {
        playerCampfireMap.remove(player);
    }

    public static boolean hasCampfire(Player player) {
        return playerCampfireMap.containsKey(player);
    }

    public static Location getCampfireLocation(Player player) {
        return playerCampfireMap.get(player);
    }

    public static boolean isCampfireAtLocation(Location location) {
        for (Location campfireLocation : playerCampfireMap.values()) {
            if (campfireLocation.equals(location)) {
                return true;
            }
        }
        return false;
    }
}
