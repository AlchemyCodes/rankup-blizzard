package blizzard.development.farm.utils;

import blizzard.development.farm.commands.CommandRegistry;
import blizzard.development.farm.database.cache.StorageCacheManager;
import blizzard.development.farm.database.cache.ToolCacheManager;
import blizzard.development.farm.database.dao.StorageDAO;
import blizzard.development.farm.database.dao.ToolDAO;
import blizzard.development.farm.database.storage.StorageData;
import blizzard.development.farm.database.storage.ToolData;
import blizzard.development.farm.listeners.ListenerRegistry;
import blizzard.development.farm.managers.BatchManager;
import blizzard.development.farm.tasks.StorageSaveTask;
import blizzard.development.farm.tasks.ToolSaveTask;
import blizzard.development.farm.utils.config.ConfigUtils;
import co.aikar.commands.Locales;
import co.aikar.commands.PaperCommandManager;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import java.sql.SQLException;
import java.util.List;
import java.util.UUID;

public class PluginImpl {

    public final Plugin plugin;
    private static PluginImpl instance;

    private StorageDAO storageDAO;
    private ToolDAO toolDAO;

    public ConfigUtils Config;
    public ConfigUtils Locations;
    public ConfigUtils Ranking;
    public ConfigUtils Database;

    public PluginImpl(Plugin plugin) {
        this.plugin = plugin;
        instance = this;
        storageDAO = new StorageDAO();
        toolDAO = new ToolDAO();
        Config = new ConfigUtils((JavaPlugin) plugin, "config.yml");
        Locations = new ConfigUtils((JavaPlugin) plugin, "locations.yml");
        Ranking = new ConfigUtils((JavaPlugin) plugin, "ranking.yml");
        Database = new ConfigUtils((JavaPlugin) plugin, "database.yml");
    }

    public void onEnable() {
        Config.saveDefaultConfig();
        Locations.saveDefaultConfig();
        Ranking.saveDefaultConfig();
        Database.saveDefaultConfig();
        BatchManager.initialize(plugin);

        PaperCommandManager commandManager = new PaperCommandManager(plugin);
        commandManager.getLocales().setDefaultLocale(Locales.PORTUGUESE);

        registerDatabase();
        registerListeners();
        registerCommands();
        registerTasks();

        registerExpansions();
    }

    public void onDisable() {
        setupDisableData();
    }

    public void registerDatabase() {
        storageDAO = new StorageDAO();
        storageDAO.initializeDatabase();
        toolDAO = new ToolDAO();
        toolDAO.initializeDatabase();

        setupEnableData();

        new StorageSaveTask(storageDAO).runTaskTimerAsynchronously(plugin, 0, 20L * 3);
        new ToolSaveTask(toolDAO).runTaskTimerAsynchronously(plugin, 0, 20 * 3);
    }

    private void setupEnableData() {
        List<StorageData> storages;
        try {
            storages = storageDAO.getAllStorageData();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        for (StorageData storage : storages) {
            StorageCacheManager.getInstance().cacheStorageData(UUID.fromString(storage.getUuid()), storage);
        }

        List<ToolData> tools;
        try {
            tools = toolDAO.getAllToolsData();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        for (ToolData tool : tools) {
            ToolCacheManager.getInstance().cacheToolData(tool.getUuid(), tool);
        }
    }

    private void setupDisableData() {
//        PlayerCacheManager.getInstance().playerCache.forEach((player, playerData) -> {
//            try {
//                playerData.setInMine(false);
//                playerDAO.updatePlayerData(playerData);
//            } catch (SQLException exception) {
//                throw new RuntimeException("Error when update player date " + playerData.getNickname(), exception);
//            }
//        });
//
//        ToolCacheManager.getInstance().toolCache.forEach((player, toolData) -> {
//            try {
//                toolDAO.updateToolData(toolData);
//            } catch (SQLException exception) {
//                throw new RuntimeException("Error when update player date " + toolData.getNickname(), exception);
//            }
//        });
//
//        DatabaseConnection.getInstance().close();
    }

    private void registerListeners() {
        ListenerRegistry listenerRegistry = new ListenerRegistry(storageDAO);
        listenerRegistry.register();
    }

    private void registerTasks() {
    }

    private void registerCommands() {
        CommandRegistry commandRegistry = new CommandRegistry();
        commandRegistry.register();
    }

    public void registerExpansions() {
//        if (Bukkit.getPluginManager().getPlugin("PlaceholderAPI") != null) {
//            new PlaceholderExpansion().register();
//        }
    }

    public static PluginImpl getInstance() {
        return instance;
    }
}