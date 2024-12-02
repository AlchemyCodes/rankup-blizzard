package blizzard.development.essentials.managers;

import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.HashMap;

public class BackManager {

    public static HashMap<Player, Location> backLocations = new HashMap<>();

    public static void add(Player player, Location location) {
        backLocations.put(player, location);
    }

    public static void remove(Player player) {
        backLocations.remove(player);
    }

    public static boolean contains(Player player) {
        return backLocations.containsKey(player);
    }

    public static Location get(Player player) {
        return backLocations.get(player);
    }
}
