package blizzard.development.farm.utils.apis;

import com.plotsquared.core.plot.Plot;
import com.sk89q.worldedit.math.BlockVector3;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

import java.util.UUID;

public class PlotSquaredAPI {

    public static Player getPlayerFromPlot(Block block) {
        Location location = block.getLocation();
        com.plotsquared.core.location.Location plotLocation = com.plotsquared.core.location.Location.at(
            location.getWorld().getName(),
            BlockVector3.at(
                location.getBlockX(),
                location.getBlockY(),
                location.getBlockZ()
            ),
            location.getYaw(),
            location.getPitch()
        );

        Plot plot = plotLocation.getPlot();

        if (plot != null) {
            UUID ownerUUID = plot.getOwnerAbs();

            if (ownerUUID != null) {
                return Bukkit.getPlayer(ownerUUID);
            }
        }

        return null;
    }

    public static Player getPlotFromPlayer(Player player) {
        Location location = player.getLocation();
        com.plotsquared.core.location.Location plotLocation = com.plotsquared.core.location.Location.at(
            location.getWorld().getName(),
            BlockVector3.at(
                location.getBlockX(),
                location.getBlockY(),
                location.getBlockZ()
            ),
            location.getYaw(),
            location.getPitch()
        );

        Plot plot = plotLocation.getPlot();

        if (plot != null) {
            UUID ownerUUID = plot.getOwnerAbs();

            if (ownerUUID != null) {
                return Bukkit.getPlayer(ownerUUID);
            }
        }

        return null;
    }
}