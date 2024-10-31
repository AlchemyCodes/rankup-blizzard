package blizzard.development.spawners.utils;

import blizzard.development.spawners.commands.CommandRegistry;
import blizzard.development.spawners.database.DatabaseConnection;
import blizzard.development.spawners.database.cache.SpawnersCacheManager;
import blizzard.development.spawners.database.dao.PlayersDAO;
import blizzard.development.spawners.database.dao.SpawnersDAO;
import blizzard.development.spawners.database.storage.SpawnersData;
import blizzard.development.spawners.listeners.ListenerRegistry;
import blizzard.development.spawners.managers.BatchManager;
import blizzard.development.spawners.tasks.PlayersSaveTask;
import blizzard.development.spawners.tasks.SpawnersSaveTask;
import blizzard.development.spawners.utils.config.ConfigUtils;
import co.aikar.commands.Locales;
import co.aikar.commands.PaperCommandManager;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PluginImpl {
    public final Plugin plugin;
    private static PluginImpl instance;
    private static PluginManager pluginManager;
    private static PaperCommandManager commandManager;
    public PlayersDAO playersDAO;
    public SpawnersDAO spawnersDAO;

    public ConfigUtils Config;
    public ConfigUtils Database;
    public PluginImpl(Plugin plugin) {
        this.plugin = plugin;
        instance = this;
        pluginManager = Bukkit.getPluginManager();
        commandManager = new PaperCommandManager(plugin);
        commandManager.getLocales().setDefaultLocale(Locales.PORTUGUESE);
        Config = new ConfigUtils((JavaPlugin) plugin, "config.yml");
        Database = new ConfigUtils((JavaPlugin) plugin, "database.yml");
    }

    public void onLoad() {
        Config.saveDefaultConfig();
        Database.saveDefaultConfig();
        registerDatabase();
        registerListeners();
        registerCommands();
        BatchManager.initialize(this.plugin);
    }

    public void onUnload() {
        DatabaseConnection.getInstance().close();
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
            }
        } catch (SQLException exception) {
            throw new RuntimeException(exception);
        }
    }

    private void registerListeners() {
        ListenerRegistry.getInstance().register();
    }

    private void registerCommands() {
        CommandRegistry.getInstance().register();
    }

    public static PluginImpl getInstance() {
        return instance;
    }
}
