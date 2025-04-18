package blizzard.development.spawners.utils;

import blizzard.development.spawners.builders.spawners.DisplayBuilder;
import blizzard.development.spawners.commands.CommandRegistry;
import blizzard.development.spawners.database.DatabaseConnection;
import blizzard.development.spawners.database.cache.managers.PlayersCacheManager;
import blizzard.development.spawners.database.cache.managers.SlaughterhouseCacheManager;
import blizzard.development.spawners.database.cache.managers.SpawnersCacheManager;
import blizzard.development.spawners.database.dao.PlayersDAO;
import blizzard.development.spawners.database.dao.SlaughterhouseDAO;
import blizzard.development.spawners.database.dao.SpawnersDAO;
import blizzard.development.spawners.database.storage.PlayersData;
import blizzard.development.spawners.database.storage.SlaughterhouseData;
import blizzard.development.spawners.database.storage.SpawnersData;
import blizzard.development.spawners.listeners.ListenerRegistry;
import blizzard.development.spawners.listeners.plots.PlotClearListener;
import blizzard.development.spawners.listeners.plots.PlotDeleteListener;
import blizzard.development.spawners.managers.slaughterhouses.SlaughterhouseBatchManager;
import blizzard.development.spawners.tasks.players.PlayersSaveTask;
import blizzard.development.spawners.tasks.slaughterhouses.SlaughterhouseSaveTask;
import blizzard.development.spawners.tasks.slaughterhouses.kill.SlaughterhouseKillTaskManager;
import blizzard.development.spawners.tasks.spawners.SpawnersSaveTask;
import blizzard.development.spawners.tasks.spawners.drops.DropsAutoSellTaskManager;
import blizzard.development.spawners.managers.spawners.SpawnerBatchManager;
import blizzard.development.spawners.tasks.spawners.mobs.SpawnersMobsTaskManager;
import blizzard.development.spawners.utils.config.ConfigUtils;
import co.aikar.commands.Locales;
import co.aikar.commands.PaperCommandManager;
import com.plotsquared.core.PlotAPI;
import org.bukkit.Bukkit;
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
    public SlaughterhouseDAO slaughterhouseDAO;

    public ConfigUtils Config;
    public ConfigUtils Database;
    public ConfigUtils Spawners;
    public ConfigUtils Slaughterhouses;
    public ConfigUtils Enchantments;
    public ConfigUtils Rewards;
    public ConfigUtils Bonus;

    public PluginImpl(Plugin plugin) {
        this.plugin = plugin;
        instance = this;
        plotAPI = new PlotAPI();
        Config = new ConfigUtils((JavaPlugin) plugin, "config.yml");
        Database = new ConfigUtils((JavaPlugin) plugin, "database.yml");
        Spawners = new ConfigUtils((JavaPlugin) plugin, "spawners.yml");
        Slaughterhouses = new ConfigUtils((JavaPlugin) plugin, "slaughterhouses.yml");
        Enchantments = new ConfigUtils((JavaPlugin) plugin, "enchantments.yml");
        Rewards = new ConfigUtils((JavaPlugin) plugin, "rewards.yml");
        Bonus = new ConfigUtils((JavaPlugin) plugin, "bonus.yml");
    }

    public void onEnable() {
        Config.saveDefaultConfig();
        Database.saveDefaultConfig();
        Spawners.saveDefaultConfig();
        Slaughterhouses.saveDefaultConfig();
        Enchantments.saveDefaultConfig();
        Rewards.saveDefaultConfig();
        Bonus.saveDefaultConfig();

        PaperCommandManager commandManager = new PaperCommandManager(plugin);
        commandManager.getLocales().setDefaultLocale(Locales.PORTUGUESE);

        SpawnersMobsTaskManager.getInstance();
        DropsAutoSellTaskManager.getInstance();
        SlaughterhouseKillTaskManager.getInstance();

        registerDatabase();
        registerListeners();
        registerCommands();

        Bukkit.getScheduler().runTaskLater(PluginImpl.getInstance().plugin, DisplayBuilder::createAllSpawnerDisplay, 200L);
        Bukkit.getScheduler().runTaskLater(PluginImpl.getInstance().plugin, blizzard.development.spawners.builders.slaughterhouses.DisplayBuilder::createAllSlaughterhouseDisplay, 250L);
    }

    public void onDisable() {
        SpawnersMobsTaskManager mobsTaskManager = SpawnersMobsTaskManager.getInstance();
        if (mobsTaskManager != null) {
            mobsTaskManager.stopAllTasks();
        }

        SlaughterhouseKillTaskManager slaughterhouseKillTaskManager = SlaughterhouseKillTaskManager.getInstance();
        if (slaughterhouseKillTaskManager != null) {
            slaughterhouseKillTaskManager.stopAllTasks();
        }

        DropsAutoSellTaskManager dropsTaskManager = DropsAutoSellTaskManager.getInstance();
        if (dropsTaskManager != null) {
            dropsTaskManager.stopAllTasks();
        }

        DisplayBuilder.removeAllSpawnerDisplay();
        blizzard.development.spawners.builders.slaughterhouses.DisplayBuilder.removeAllSlaughterhouseDisplay();
        DatabaseConnection.getInstance().close();
    }

    public void registerDatabase() {
        DatabaseConnection.getInstance();
        playersDAO = new PlayersDAO();
        playersDAO.initializeDatabase();
        spawnersDAO = new SpawnersDAO();
        spawnersDAO.initializeDatabase();
        slaughterhouseDAO = new SlaughterhouseDAO();
        slaughterhouseDAO.initializeDatabase();

        new PlayersSaveTask(playersDAO).runTaskTimerAsynchronously(plugin, 0, 20L * 3);
        new SpawnersSaveTask(spawnersDAO).runTaskTimerAsynchronously(plugin, 0, 20L * 3);
        new SlaughterhouseSaveTask(slaughterhouseDAO).runTaskTimerAsynchronously(plugin, 0, 20L * 3);

        try {
            List<SpawnersData> spawners = spawnersDAO.getAllSpawnersData();
            for (SpawnersData spawner : spawners) {
                SpawnersCacheManager.getInstance().cacheSpawnerData(spawner.getId(), spawner);
            }
            SpawnerBatchManager.getInstance().processSpawnersDefault(spawners);
        } catch (SQLException exception) {
            throw new RuntimeException(exception);
        }

        try {
            List<PlayersData> players = playersDAO.getAllPlayersData();
            for (PlayersData player : players) {
                PlayersCacheManager.getInstance().cachePlayerData(player.getNickname(), player);
            }
        } catch (SQLException exception) {
            throw new RuntimeException(exception);
        }

        try {
            List<SlaughterhouseData> slaughterhouses = slaughterhouseDAO.getAllSlaughterhousesData();
            for (SlaughterhouseData slaughterhouse : slaughterhouses) {
                SlaughterhouseCacheManager.getInstance().cacheSlaughterhouseData(slaughterhouse.getId(), slaughterhouse);
            }
            SlaughterhouseBatchManager.getInstance().processSlaughterhousesDefault(slaughterhouses);
        } catch (SQLException exception) {
            throw new RuntimeException(exception);
        }
    }

    private void registerListeners() {
        ListenerRegistry.getInstance().register();
        new PlotClearListener(plotAPI, spawnersDAO, slaughterhouseDAO);
        new PlotDeleteListener(plotAPI, spawnersDAO, slaughterhouseDAO);
    }

    private void registerCommands() {
        CommandRegistry.getInstance().register();
    }

    public static PluginImpl getInstance() {
        return instance;
    }
}