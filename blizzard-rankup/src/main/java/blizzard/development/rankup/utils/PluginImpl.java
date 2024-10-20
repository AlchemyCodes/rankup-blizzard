package blizzard.development.rankup.utils;

import blizzard.development.rankup.commands.PrestigeCommand;
import blizzard.development.rankup.commands.RankCommand;
import blizzard.development.rankup.commands.RankUpCommand;
import blizzard.development.rankup.commands.RanksCommand;
import blizzard.development.rankup.database.DatabaseConnection;
import blizzard.development.rankup.database.cache.PlayersCacheManager;
import blizzard.development.rankup.database.dao.PlayersDAO;
import blizzard.development.rankup.listeners.TrafficListener;
import blizzard.development.rankup.tasks.PlayerSaveTask;
import co.aikar.commands.Locales;
import co.aikar.commands.PaperCommandManager;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.sql.SQLException;

import static org.apache.logging.log4j.LogManager.getLogger;

public class PluginImpl {

    public PlayersDAO playersDAO;

    public final Plugin plugin;
    private static PluginImpl instance;
    private static PaperCommandManager commandManager;
    private static PluginManager pluginManager;
    public ConfigUtils Database;
    public ConfigUtils Config;
    public ConfigUtils Messages;
    public ConfigUtils Ranks;
    public ConfigUtils Prestige;
    public ConfigUtils Inventories;


    public PluginImpl(Plugin plugin) {
        this.plugin = plugin;
        instance = this;
        commandManager = new PaperCommandManager(plugin);

        commandManager.getLocales().setDefaultLocale(Locales.PORTUGUESE);
        pluginManager = Bukkit.getPluginManager();

        Database = new ConfigUtils((JavaPlugin) plugin, "database.yml");
        Config = new ConfigUtils((JavaPlugin) plugin, "config.yml");
        Messages = new ConfigUtils((JavaPlugin) plugin, "messages.yml");
        Ranks = new ConfigUtils((JavaPlugin) plugin, "ranks.yml");
        Prestige = new ConfigUtils((JavaPlugin) plugin, "prestige.yml");
        Inventories = new ConfigUtils((JavaPlugin) plugin, "inventories.yml");
    }

    public void onLoad() {
        loadConfigs();
        registerDatabase();
        registerCommands();
        registerListeners();
        registerTasks();


    }

    public void loadConfigs() {
        if (!Database.existsConfig()) {
            Database.saveDefaultConfig();
        }
        if (!Config.existsConfig()) {
            Config.saveDefaultConfig();
        }
        if (!Messages.existsConfig()) {
            Messages.saveDefaultConfig();
        }
        if (!Ranks.existsConfig()) {
            Ranks.saveDefaultConfig();
        }
        if (!Prestige.existsConfig()) {
            Prestige.saveDefaultConfig();
        }
        if (!Inventories.existsConfig()) {
            Inventories.saveDefaultConfig();
        }

        Database.reloadConfig();
        Config.reloadConfig();
        Messages.reloadConfig();
        Ranks.reloadConfig();
        Prestige.reloadConfig();
        Inventories.reloadConfig();
    }


    public void onUnload() {
        PlayersCacheManager.playerCache.forEach((player, playersData) -> {
            try {
                playersDAO.updatePlayerData(playersData);
            } catch (SQLException exception) {
                throw new RuntimeException(exception);
            }
        }
        );
    }


    public void registerDatabase() {
        DatabaseConnection.getInstance();
        playersDAO = new PlayersDAO();
        playersDAO.initializeDatabase();

        new PlayerSaveTask(playersDAO).runTaskTimerAsynchronously(plugin, 0, 20L * 3);
    }

    private void registerTasks() {

    }

    private void registerListeners() {
        pluginManager.registerEvents(new TrafficListener(playersDAO), plugin);

    }

    private void registerCommands() {
        commandManager.registerCommand(new RankCommand());
        commandManager.registerCommand(new RanksCommand());
        commandManager.registerCommand(new RankUpCommand());
        commandManager.registerCommand(new PrestigeCommand());
    }

    public static PluginImpl getInstance() {
        return instance;
    }

    public File getDataFolder() {
        return plugin.getDataFolder();
    }
}
