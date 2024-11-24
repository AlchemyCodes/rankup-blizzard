package blizzard.development.spawners.utils;

import blizzard.development.spawners.commands.CommandRegistry;
import blizzard.development.spawners.database.DatabaseConnection;
import blizzard.development.spawners.database.cache.managers.SpawnersCacheManager;
import blizzard.development.spawners.database.dao.PlayersDAO;
import blizzard.development.spawners.database.dao.SpawnersDAO;
import blizzard.development.spawners.database.storage.SpawnersData;
import blizzard.development.spawners.listeners.ListenerRegistry;
import blizzard.development.spawners.listeners.plots.PlotClearListener;
import blizzard.development.spawners.listeners.plots.PlotDeleteListener;
import blizzard.development.spawners.tasks.players.PlayersSaveTask;
import blizzard.development.spawners.tasks.spawners.SpawnersSaveTask;
import blizzard.development.spawners.tasks.spawners.mobs.SpawnersMobsTaskManager;
import blizzard.development.spawners.utils.config.ConfigUtils;
import co.aikar.commands.Locales;
import co.aikar.commands.PaperCommandManager;
import com.plotsquared.core.PlotAPI;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import java.sql.SQLException;
import java.util.List;

public class PluginImpl {
    public final Plugin plugin;
    private static PluginImpl instance;
    private static PlotAPI plotAPI;
    public PlayersDAO playersDAO;
    public SpawnersDAO spawnersDAO;

    public ConfigUtils Config;
    public ConfigUtils Database;

    public PluginImpl(Plugin plugin) {
        this.plugin = plugin;
        instance = this;
        plotAPI = new PlotAPI();
        Config = new ConfigUtils((JavaPlugin) plugin, "config.yml");
        Database = new ConfigUtils((JavaPlugin) plugin, "database.yml");
    }

    public void onEnable() {
        Config.saveDefaultConfig();
        Database.saveDefaultConfig();

        PaperCommandManager commandManager = new PaperCommandManager(plugin);
        commandManager.getLocales().setDefaultLocale(Locales.PORTUGUESE);

        registerDatabase();
        registerListeners();
        registerCommands();
    }

    public void onDisable() {
        DatabaseConnection.getInstance().close();
        SpawnersMobsTaskManager.getInstance().stopAllTasks();
    }

    public void registerDatabase() {
        DatabaseConnection.getInstance();
        playersDAO = new PlayersDAO();
        playersDAO.initializeDatabase();
        spawnersDAO = new SpawnersDAO();
        spawnersDAO.initializeDatabase();

        new PlayersSaveTask(playersDAO).runTaskTimerAsynchronously(plugin, 0, 20L * 3);
        new SpawnersSaveTask(spawnersDAO).runTaskTimerAsynchronously(plugin, 0, 20L * 3);

        try {
            List<SpawnersData> allSpawners = spawnersDAO.getAllSpawnersData();
            for (SpawnersData spawner : allSpawners) {
                SpawnersCacheManager.getInstance().cacheSpawnerData(spawner.getId(), spawner);
                SpawnersMobsTaskManager.getInstance().startTask(spawner);
            }
        } catch (SQLException exception) {
            throw new RuntimeException(exception);
        }
    }

    private void registerListeners() {
        ListenerRegistry.getInstance().register();
        new PlotClearListener(plotAPI, spawnersDAO);
        new PlotDeleteListener(plotAPI, spawnersDAO);
    }

    private void registerCommands() {
        CommandRegistry.getInstance().register();
    }

    public static PluginImpl getInstance() {
        return instance;
    }
}