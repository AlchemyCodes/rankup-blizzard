package blizzard.development.spawners.listeners.plots;

import blizzard.development.spawners.builders.spawners.DisplayBuilder;
import blizzard.development.spawners.database.dao.SlaughterhouseDAO;
import blizzard.development.spawners.database.dao.SpawnersDAO;
import blizzard.development.spawners.database.storage.SlaughterhouseData;
import blizzard.development.spawners.database.storage.SpawnersData;
import blizzard.development.spawners.tasks.slaughterhouses.kill.SlaughterhouseKillTaskManager;
import blizzard.development.spawners.tasks.spawners.drops.DropsAutoSellTaskManager;
import blizzard.development.spawners.tasks.spawners.mobs.SpawnersMobsTaskManager;
import blizzard.development.spawners.utils.LocationUtil;
import com.google.common.eventbus.Subscribe;
import com.plotsquared.core.PlotAPI;
import com.plotsquared.core.events.PlotClearEvent;
import com.plotsquared.core.plot.Plot;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.sql.SQLException;
import java.util.List;

public class PlotClearListener {

    private final SpawnersDAO spawnersDAO;
    private final SlaughterhouseDAO slaughterhouseDAO;

    public PlotClearListener(PlotAPI plotAPI, SpawnersDAO spawnersDAO, SlaughterhouseDAO slaughterhouseDAO) {
        this.spawnersDAO = spawnersDAO;
        this.slaughterhouseDAO = slaughterhouseDAO;
        plotAPI.registerListener(this);
    }

    @Subscribe
    public void onPlotClear(PlotClearEvent event) {
        final Plot plot = event.getPlot();
        handlePlotRemoval(plot);
    }

    private void handlePlotRemoval(Plot plot) {
        final String plotId = plot.getId().toString();
        final List<SpawnersData> spawnersList = spawnersDAO.findSpawnerDataByPlotId(plotId);
        final List<SlaughterhouseData> slaughterhouseList = slaughterhouseDAO.findSlaughterhouseDataByPlotId(plotId);

        if (!spawnersList.isEmpty()) {
            for (SpawnersData spawnersData : spawnersList) {
                Location spawnerLocation = LocationUtil.deserializeLocation(spawnersData.getLocation());

                for (Player player : Bukkit.getOnlinePlayers()) {
                    if (player.getWorld().equals(spawnerLocation.getWorld())) {
                        DisplayBuilder.removeSpawnerDisplay(spawnerLocation);
                        SpawnersMobsTaskManager.getInstance().stopTask(spawnersData.getId());
                        DropsAutoSellTaskManager.getInstance().stopTask(spawnersData.getId());
                    }
                }

                final String spawnerId = spawnersData.getId();
                try {
                    spawnersDAO.deleteSpawnerData(spawnerId);
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
        }

        if (!slaughterhouseList.isEmpty()) {
            for (SlaughterhouseData slaughterhouseData : slaughterhouseList) {
                Location slaughterhouseLocation = LocationUtil.deserializeLocation(slaughterhouseData.getLocation());

                for (Player player : Bukkit.getOnlinePlayers()) {
                    if (player.getWorld().equals(slaughterhouseLocation.getWorld())) {
                        blizzard.development.spawners.builders.slaughterhouses.DisplayBuilder.removeSlaughterhouseDisplay(slaughterhouseLocation);
                        SlaughterhouseKillTaskManager.getInstance().stopTask(slaughterhouseData.getId());
                    }
                }

                final String slaughterhouseId = slaughterhouseData.getId();
                try {
                    slaughterhouseDAO.deleteSlaughterhouseData(slaughterhouseId);
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }
}