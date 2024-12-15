package blizzard.development.spawners.listeners.plots;

import blizzard.development.spawners.builders.DisplayBuilder;
import blizzard.development.spawners.database.dao.SpawnersDAO;
import blizzard.development.spawners.database.storage.SpawnersData;
import blizzard.development.spawners.tasks.spawners.drops.DropsAutoSellTaskManager;
import blizzard.development.spawners.tasks.spawners.mobs.SpawnersMobsTaskManager;
import blizzard.development.spawners.utils.LocationUtil;
import com.google.common.eventbus.Subscribe;
import com.plotsquared.core.PlotAPI;
import com.plotsquared.core.events.PlotDeleteEvent;
import com.plotsquared.core.plot.Plot;
import org.bukkit.Location;

import java.sql.SQLException;
import java.util.List;

public class PlotDeleteListener {

    private final SpawnersDAO spawnersDAO;

    public PlotDeleteListener(PlotAPI plotAPI, SpawnersDAO spawnersDAO) {
        this.spawnersDAO = spawnersDAO;
        plotAPI.registerListener(this);
    }

    @Subscribe
    public void onPlotDelete(PlotDeleteEvent event) {
        final Plot plot = event.getPlot();
        handlePlotRemoval(plot);
    }

    private void handlePlotRemoval(Plot plot) {
        final String plotId = plot.getId().toString();
        final List<SpawnersData> spawnersList = spawnersDAO.findSpawnerDataByPlotId(plotId);

        if (!spawnersList.isEmpty()) {
            for (SpawnersData spawnersData : spawnersList) {
                Location spawnerLocation = LocationUtil.deserializeLocation(spawnersData.getLocation());
                DisplayBuilder.removeSpawnerDisplay(spawnerLocation);
                SpawnersMobsTaskManager.getInstance().stopTask(spawnersData.getId());
                DropsAutoSellTaskManager.getInstance().stopTask(spawnersData.getId());
                final String spawnerId = spawnersData.getId();
                try {
                    spawnersDAO.deleteSpawnerData(spawnerId);
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }
}
