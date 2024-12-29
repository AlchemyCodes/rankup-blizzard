package blizzard.development.monsters.utils;

import blizzard.development.monsters.utils.items.TextAPI;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;

public class LocationUtils {
    private static LocationUtils instance;

    public String getSerializedLocation(Location location) {
        return location.getWorld().getName() + ";" + location.getBlockX() + ";" + location.getBlockY() + ";" + location.getBlockZ() + ";" + location.getYaw() + ";" + location.getPitch();
    }

    public Location deserializeLocation(String serializedLocation) {
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

    public void setLocation(String to, Location location) {
        PluginImpl.getInstance().Locations.getConfig().set("locations." + to + ".world", location.getWorld().getName());
        PluginImpl.getInstance().Locations.getConfig().set("locations." + to + ".x", location.getX());
        PluginImpl.getInstance().Locations.getConfig().set("locations." + to + ".y", location.getY());
        PluginImpl.getInstance().Locations.getConfig().set("locations." + to + ".z", location.getZ());
        PluginImpl.getInstance().Locations.getConfig().set("locations." + to + ".yaw", location.getYaw());
        PluginImpl.getInstance().Locations.getConfig().set("locations." + to + ".pitch", location.getPitch());
        PluginImpl.getInstance().Locations.saveConfig();
    }

    public Location getLocation(String from) {
        String worldName = PluginImpl.getInstance().Locations.getConfig().getString("locations." + from + ".world");

        if (worldName == null) {
            return null;
        }

        World world = Bukkit.getWorld(worldName);
        if (world == null) {
            return null;
        }

        double x = PluginImpl.getInstance().Locations.getConfig().getDouble("locations." + from + ".x");
        double y = PluginImpl.getInstance().Locations.getConfig().getDouble("locations." + from + ".y");
        double z = PluginImpl.getInstance().Locations.getConfig().getDouble("locations." + from + ".z");
        float yaw = (float) PluginImpl.getInstance().Locations.getConfig().getDouble("locations." + from + ".yaw");
        float pitch = (float) PluginImpl.getInstance().Locations.getConfig().getDouble("locations." + from + ".pitch");

        return new Location(world, x, y, z, yaw, pitch).add(0.5, 0, 0.5);
    }


    public static LocationUtils getInstance() {
        if (instance == null) instance = new LocationUtils();
        return instance;
    }
}
