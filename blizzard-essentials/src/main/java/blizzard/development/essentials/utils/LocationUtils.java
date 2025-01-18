package blizzard.development.essentials.utils;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;

public class LocationUtils {

    public static Location getSpawnLocation() {
        String worldSpawn = PluginImpl.getInstance().Locations.getConfig().getString("spawn.location.world");
        double x = PluginImpl.getInstance().Locations.getConfig().getDouble("spawn.location.x");
        double y = PluginImpl.getInstance().Locations.getConfig().getDouble("spawn.location.y");
        double z = PluginImpl.getInstance().Locations.getConfig().getDouble("spawn.location.z");
        float yaw = (float) PluginImpl.getInstance().Locations.getConfig().getDouble("spawn.location.yaw");
        float pitch = (float) PluginImpl.getInstance().Locations.getConfig().getDouble("spawn.location.pitch");

        if (worldSpawn == null) return null;

        World world = Bukkit.getWorld(worldSpawn);

        return new Location(
            world,
            x,
            y,
            z,
            yaw,
            pitch
        );
    }

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
