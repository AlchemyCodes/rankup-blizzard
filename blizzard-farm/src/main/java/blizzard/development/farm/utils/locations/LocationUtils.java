package blizzard.development.farm.utils.locations;

import blizzard.development.farm.utils.PluginImpl;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;

public class LocationUtils {

    public static void setLocation(Player player, String to, Location location) {
        PluginImpl.getInstance().Locations.getConfig().set("locations." + to + ".world", location.getWorld().getName());
        PluginImpl.getInstance().Locations.getConfig().set("locations." + to + ".x", location.getX());
        PluginImpl.getInstance().Locations.getConfig().set("locations." + to + ".y", location.getY());
        PluginImpl.getInstance().Locations.getConfig().set("locations." + to + ".z", location.getZ());
        PluginImpl.getInstance().Locations.getConfig().set("locations." + to + ".yaw", location.getYaw());
        PluginImpl.getInstance().Locations.getConfig().set("locations." + to + ".pitch", location.getPitch());
        PluginImpl.getInstance().Locations.saveConfig();

        player.sendActionBar("§a§lYAY! §aVocê setou o local §f`" + to + "`" + "§a da mina com sucesso!");
    }

    public static Location getLocation(String from) {
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
}
