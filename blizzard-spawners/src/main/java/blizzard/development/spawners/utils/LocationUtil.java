package blizzard.development.spawners.utils;

import blizzard.development.spawners.utils.items.TextAPI;
import com.plotsquared.core.PlotSquared;
import com.plotsquared.core.plot.Plot;
import com.plotsquared.core.plot.PlotArea;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

import java.util.Objects;
import java.util.UUID;

public class LocationUtil {

    public static String getSerializedLocation(Location location) {
        return location.getWorld().getName() + ";" + location.getBlockX() + ";" + location.getBlockY() + ";" + location.getBlockZ() + ";" + location.getYaw() + ";" + location.getPitch();
    }

    public static Location deserializeLocation(String serializedLocation) {
        String[] parts = serializedLocation.split(";");
        return new Location(
                PluginImpl.getInstance().plugin.getServer().getWorld(parts[0]),
                Double.parseDouble(parts[1]),
                Double.parseDouble(parts[2]),
                Double.parseDouble(parts[3]),
                Float.parseFloat(parts[4]),
                Float.parseFloat(parts[5])
        );
    }

    public static Boolean hasNearbySpawners(Location location, int radius) {
        int x = location.getBlockX();
        int y = location.getBlockY();
        int z = location.getBlockZ();

        for (int i = -radius; i <= radius; i++) {
            for (int j = -radius; j <= radius; j++) {
                for (int k = -radius; k <= radius; k++) {
                    Location checkLoc = new Location(location.getWorld(), x + i, y + j, z + k);

                    if (i == 0 && j == 0 && k == 0) continue;

                    if (checkLoc.getBlock().getType() == Material.SPAWNER &&
                            checkLoc.distance(location) <= radius) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public static Boolean terrainVerify(Player player, Block block) {
        com.plotsquared.core.location.Location blockLocation = getPlotLocation(block);

        UUID playerUUID = player.getUniqueId();
        PlotArea plotArea = PlotSquared.get().getPlotAreaManager().getPlotArea(blockLocation);

        if (plotArea == null) {
            player.sendActionBar(TextAPI.parse("§c§lEI! §cVocê não pode colocar um spawner nesse local."));
            return false;
        }

        Plot plot = plotArea.getPlot(blockLocation);

        if (plot == null) {
            player.sendActionBar(TextAPI.parse("§c§lEI! §cVocê não pode colocar um spawner nesse local."));
            return false;
        }

        if (!(Objects.equals(plot.getOwner(), playerUUID) || plot.isAdded(playerUUID) || player.hasPermission("blizzard.spawners.admin"))) {
            player.sendActionBar(TextAPI.parse("§c§lEI! §cVocê não tem permissão para colocar um spawner nesse local."));
            return false;
        }
        return true;
    }

    public static com.plotsquared.core.location.Location getPlotLocation(Block block) {
        return com.plotsquared.core.location.Location.at(
                block.getLocation().getWorld().getName(),
                (int) block.getLocation().getX(),
                (int) block.getLocation().getY(),
                (int) block.getLocation().getZ()
        );
    }
}
