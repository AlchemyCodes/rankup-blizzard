package blizzard.development.excavation.utils;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;

public class LocationUtils {

    public Location excavationLocation() {
        String excavation = PluginImpl.getInstance().Locations.getConfig().getString("excavation.location.world");
        double x = PluginImpl.getInstance().Locations.getConfig().getDouble("excavation.location.x");
        double y = PluginImpl.getInstance().Locations.getConfig().getDouble("excavation.location.y");
        double z = PluginImpl.getInstance().Locations.getConfig().getDouble("excavation.location.z");
        float yaw = (float) PluginImpl.getInstance().Locations.getConfig().getDouble("excavation.location.yaw");
        float pitch = (float) PluginImpl.getInstance().Locations.getConfig().getDouble("excavation.location.pitch");

        if (excavation == null) return null;

        World world = Bukkit.getWorld(excavation);

        return new Location(world, x, y, z, yaw, pitch);
    }
}
