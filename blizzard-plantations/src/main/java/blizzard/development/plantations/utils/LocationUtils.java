package blizzard.development.plantations.utils;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;

public class LocationUtils {

    public static void setPlantationSpawnLocation(Player player, World world, int x, int y, int z, float yaw, float pitch) {
        PluginImpl.getInstance().Locations.getConfig().set("estufa.location.world", world.getName());
        PluginImpl.getInstance().Locations.getConfig().set("estufa.location.x", x);
        PluginImpl.getInstance().Locations.getConfig().set("estufa.location.y", y);
        PluginImpl.getInstance().Locations.getConfig().set("estufa.location.z", z);
        PluginImpl.getInstance().Locations.getConfig().set("estufa.location.yaw", yaw);
        PluginImpl.getInstance().Locations.getConfig().set("estufa.location.pitch", pitch);
        PluginImpl.getInstance().Locations.saveConfig();

        player.sendActionBar("§a§lYAY! §aVocê setou o local de spawn da estufa com sucesso!");
    }

    public static Location getPlantationSpawnLocation() {
        String worldName = PluginImpl.getInstance().Locations.getConfig().getString("estufa.location.world");
        double x = PluginImpl.getInstance().Locations.getConfig().getDouble("estufa.location.x");
        double y = PluginImpl.getInstance().Locations.getConfig().getDouble("estufa.location.y");
        double z = PluginImpl.getInstance().Locations.getConfig().getDouble("estufa.location.z");
        float yaw = (float) PluginImpl.getInstance().Locations.getConfig().getDouble("estufa.location.yaw");
        float pitch = (float) PluginImpl.getInstance().Locations.getConfig().getDouble("estufa.location.pitch");

        assert worldName != null;
        World world = Bukkit.getWorld(worldName);


        if (world != null) {
            return new Location(
                world,
                x,
                y,
                z,
                yaw,
                pitch
            ).add(0.5, 0, 0.5);
        }

        return null;
    }

    public static void setSpawnLocation(Player player, World world, int x, int y, int z, float yaw, float pitch) {
        PluginImpl.getInstance().Locations.getConfig().set("spawn.location.world", world.getName());
        PluginImpl.getInstance().Locations.getConfig().set("spawn.location.x", x);
        PluginImpl.getInstance().Locations.getConfig().set("spawn.location.y", y);
        PluginImpl.getInstance().Locations.getConfig().set("spawn.location.z", z);
        PluginImpl.getInstance().Locations.getConfig().set("spawn.location.yaw", yaw);
        PluginImpl.getInstance().Locations.getConfig().set("spawn.location.pitch", pitch);
        PluginImpl.getInstance().Locations.saveConfig();

        player.sendActionBar("§a§lYAY! §aVocê setou o local de spawn da estufa com sucesso!");
    }

    public static Location getSpawnLocation() {
        String worldName = PluginImpl.getInstance().Locations.getConfig().getString("spawn.location.world");
        double x = PluginImpl.getInstance().Locations.getConfig().getDouble("spawn.location.x");
        double y = PluginImpl.getInstance().Locations.getConfig().getDouble("spawn.location.y");
        double z = PluginImpl.getInstance().Locations.getConfig().getDouble("spawn.location.z");
        float yaw = (float) PluginImpl.getInstance().Locations.getConfig().getDouble("spawn.location.yaw");
        float pitch = (float) PluginImpl.getInstance().Locations.getConfig().getDouble("spawn.location.pitch");

        assert worldName != null;
        World world = Bukkit.getWorld(worldName);


        if (world != null) {
            return new Location(
                world,
                x,
                y,
                z,
                yaw,
                pitch
            ).add(0.5, 0, 0.5);
        }

        return null;
    }

    public static void setNpcSpawnLocation(Player player, World world, int x, int y, int z, float yaw, float pitch) {
        PluginImpl.getInstance().Locations.getConfig().set("estufa-npc.location.world", world.getName());
        PluginImpl.getInstance().Locations.getConfig().set("estufa-npc.location.x", x);
        PluginImpl.getInstance().Locations.getConfig().set("estufa-npc.location.y", y);
        PluginImpl.getInstance().Locations.getConfig().set("estufa-npc.location.z", z);
        PluginImpl.getInstance().Locations.getConfig().set("estufa-npc.location.yaw", yaw);
        PluginImpl.getInstance().Locations.getConfig().set("estufa-npc.location.pitch", pitch);
        PluginImpl.getInstance().Locations.saveConfig();

        player.sendActionBar("§a§lYAY! §aVocê setou o local de spawn do npc da estufa com sucesso!");
    }

    public static Location getNpcSpawnLocation() {
        String worldName = PluginImpl.getInstance().Locations.getConfig().getString("estufa-npc.location.world");
        double x = PluginImpl.getInstance().Locations.getConfig().getDouble("estufa-npc.location.x");
        double y = PluginImpl.getInstance().Locations.getConfig().getDouble("estufa-npc.location.y");
        double z = PluginImpl.getInstance().Locations.getConfig().getDouble("estufa-npc.location.z");
        float yaw = (float) PluginImpl.getInstance().Locations.getConfig().getDouble("estufa-npc.location.yaw");
        float pitch = (float) PluginImpl.getInstance().Locations.getConfig().getDouble("estufa-npc.location.pitch");

        assert worldName != null;
        World world = Bukkit.getWorld(worldName);


        if (world != null) {
            return new Location(
                world,
                x,
                y,
                z,
                yaw,
                pitch
            );
        }

        return null;
    }

    public static void setCenterLocation(Player player, World world, int x, int y, int z, float yaw, float pitch) {
        PluginImpl.getInstance().Locations.getConfig().set("estufa.center-location.world", world.getName());
        PluginImpl.getInstance().Locations.getConfig().set("estufa.center-location.x", x);
        PluginImpl.getInstance().Locations.getConfig().set("estufa.center-location.y", y);
        PluginImpl.getInstance().Locations.getConfig().set("estufa.center-location.z", z);
        PluginImpl.getInstance().Locations.getConfig().set("estufa.center-location.yaw", yaw);
        PluginImpl.getInstance().Locations.getConfig().set("estufa.center-location.pitch", pitch);
        PluginImpl.getInstance().Locations.saveConfig();

        player.sendActionBar("§a§lYAY! §aVocê setou o local de spawn da estufa com sucesso!");
    }

    public static Location getCenterLocation() {
        String worldName = PluginImpl.getInstance().Locations.getConfig().getString("estufa.center-location.world");
        double x = PluginImpl.getInstance().Locations.getConfig().getDouble("estufa.center-location.x");
        double y = PluginImpl.getInstance().Locations.getConfig().getDouble("estufa.center-location.y");
        double z = PluginImpl.getInstance().Locations.getConfig().getDouble("estufa.center-location.z");
        float yaw = (float) PluginImpl.getInstance().Locations.getConfig().getDouble("estufa.center-location.yaw");
        float pitch = (float) PluginImpl.getInstance().Locations.getConfig().getDouble("estufa.center-location.pitch");

        assert worldName != null;
        World world = Bukkit.getWorld(worldName);


        if (world != null) {
            return new Location(
                world,
                x,
                y,
                z,
                yaw,
                pitch
            ).add(0.5, 0, 0.5);
        }

        return null;
    }
}
