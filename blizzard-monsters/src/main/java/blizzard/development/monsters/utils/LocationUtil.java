package blizzard.development.monsters.utils;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

import java.util.*;

public class LocationUtil {

    public static String getSerializedLocation(Location location) {
        return location.getWorld().getName() + ";" + location.getBlockX() + ";" + location.getBlockY() + ";" + location.getBlockZ() + ";" + location.getYaw() + ";" + location.getPitch();
    }

    public static Location deserializeLocation(String serializedLocation) {
        try {
            String[] parts = serializedLocation.split(";");
            if (parts.length != 6) {
                throw new IllegalArgumentException();
            }

            World world = PluginImpl.getInstance().plugin.getServer().getWorld(parts[0]);
            if (world == null) {
                throw new IllegalArgumentException();
            }

            return new Location(
                    world,
                    Double.parseDouble(parts[1]),
                    Double.parseDouble(parts[2]),
                    Double.parseDouble(parts[3]),
                    Float.parseFloat(parts[4]),
                    Float.parseFloat(parts[5])
            );
        } catch (Exception e) {
            return null;
        }
    }
}
