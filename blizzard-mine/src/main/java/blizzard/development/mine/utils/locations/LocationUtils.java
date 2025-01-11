package blizzard.development.mine.utils.locations;

import blizzard.development.mine.utils.PluginImpl;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;

public class LocationUtils {

    public static void setMineSpawnLocation(Player player, World world, int x, int y, int z, float yaw, float pitch) {
        PluginImpl.getInstance().Locations.getConfig().set("mina.location.world", world.getName());
        PluginImpl.getInstance().Locations.getConfig().set("mina.location.x", x);
        PluginImpl.getInstance().Locations.getConfig().set("mina.location.y", y);
        PluginImpl.getInstance().Locations.getConfig().set("mina.location.z", z);
        PluginImpl.getInstance().Locations.getConfig().set("mina.location.yaw", yaw);
        PluginImpl.getInstance().Locations.getConfig().set("mina.location.pitch", pitch);
        PluginImpl.getInstance().Locations.saveConfig();

        player.sendActionBar("§a§lYAY! §aVocê setou o local de spawn da mina com sucesso!");
    }

    public static void setMineCenterLocation(Player player, World world, int x, int y, int z, float yaw, float pitch) {
        PluginImpl.getInstance().Locations.getConfig().set("mina.center-location.world", world.getName());
        PluginImpl.getInstance().Locations.getConfig().set("mina.center-location.x", x);
        PluginImpl.getInstance().Locations.getConfig().set("mina.center-location.y", y);
        PluginImpl.getInstance().Locations.getConfig().set("mina.center-location.z", z);
        PluginImpl.getInstance().Locations.getConfig().set("mina.center-location.yaw", yaw);
        PluginImpl.getInstance().Locations.getConfig().set("mina.center-location.pitch", pitch);
        PluginImpl.getInstance().Locations.saveConfig();

        player.sendActionBar("§a§lYAY! §aVocê setou o local de spawn da mina com sucesso!");
    }

    public static Location getMineCenterLocation() {
        String worldName = PluginImpl.getInstance().Locations.getConfig().getString("mina.center-location.world");
        double x = PluginImpl.getInstance().Locations.getConfig().getDouble("mina.center-location.x");
        double y = PluginImpl.getInstance().Locations.getConfig().getDouble("mina.center-location.y");
        double z = PluginImpl.getInstance().Locations.getConfig().getDouble("mina.center-location.z");
        float yaw = (float) PluginImpl.getInstance().Locations.getConfig().getDouble("mina.center-location.yaw");
        float pitch = (float) PluginImpl.getInstance().Locations.getConfig().getDouble("mina.center-location.pitch");

        assert worldName != null;
        World world = Bukkit.getWorld(worldName);


        if (world != null) {
            return new Location(
                world,
                x,
                y + 2,
                z,
                yaw,
                pitch
            ).add(0.5, 0, 0.5);
        }

        return null;
    }

    public static Location getMineSpawnLocation() {
        String worldName = PluginImpl.getInstance().Locations.getConfig().getString("mina.location.world");
        double x = PluginImpl.getInstance().Locations.getConfig().getDouble("mina.location.x");
        double y = PluginImpl.getInstance().Locations.getConfig().getDouble("mina.location.y");
        double z = PluginImpl.getInstance().Locations.getConfig().getDouble("mina.location.z");
        float yaw = (float) PluginImpl.getInstance().Locations.getConfig().getDouble("mina.location.yaw");
        float pitch = (float) PluginImpl.getInstance().Locations.getConfig().getDouble("mina.location.pitch");

        assert worldName != null;
        World world = Bukkit.getWorld(worldName);


        if (world != null) {
            return new Location(
                world,
                x,
                y + 2,
                z,
                yaw,
                pitch
            ).add(0.5, 0, 0.5);
        }

        return null;
    }
}
