package blizzard.development.bosses.utils;

import blizzard.development.bosses.commands.BossesCommand;
import blizzard.development.bosses.commands.subcommands.GiveCommand;
import blizzard.development.bosses.commands.subcommands.ReloadCommand;
import blizzard.development.bosses.commands.subcommands.SetSpawnCommand;
import blizzard.development.bosses.commands.subcommands.ToolsCommand;
import blizzard.development.bosses.currencies.CoinsCurrency;
import blizzard.development.bosses.database.DatabaseConnection;
import blizzard.development.bosses.database.cache.ToolsCacheManager;
import blizzard.development.bosses.database.dao.PlayersDAO;
import blizzard.development.bosses.database.dao.ToolsDAO;
import blizzard.development.bosses.database.storage.ToolsData;
import blizzard.development.bosses.listeners.bosses.BossesAreaListener;
import blizzard.development.bosses.listeners.commons.PlayersJoinListener;
import blizzard.development.bosses.listeners.commons.PlayersQuitListener;
import blizzard.development.bosses.managers.BatchManager;
import blizzard.development.bosses.tasks.PlayersSaveTask;
import blizzard.development.bosses.tasks.ToolsSaveTask;
import blizzard.development.bosses.utils.config.ConfigUtils;
import co.aikar.commands.Locales;
import co.aikar.commands.PaperCommandManager;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class PluginImpl {
    public final Plugin plugin;
    private static PluginImpl instance;
    private static PluginManager pluginManager;
    private static PaperCommandManager commandManager;
    public PlayersDAO playersDAO;
    public ToolsDAO toolsDAO;

    public ConfigUtils Config;
    public ConfigUtils Locations;
    public ConfigUtils Database;
    public PluginImpl(Plugin plugin) {
        this.plugin = plugin;
        instance = this;
        pluginManager = Bukkit.getPluginManager();
        commandManager = new PaperCommandManager(plugin);
        commandManager.getLocales().setDefaultLocale(Locales.PORTUGUESE);
        Config = new ConfigUtils((JavaPlugin) plugin, "config.yml");
        Locations = new ConfigUtils((JavaPlugin) plugin, "locations.yml");
        Database = new ConfigUtils((JavaPlugin) plugin, "database.yml");
    }

    public void onLoad() {
        Config.saveDefaultConfig();
        Locations.saveDefaultConfig();
        Database.saveDefaultConfig();
        registerDatabase();
        registerListeners();
        registerCommands();
        BatchManager.initialize(this.plugin);
        try {
            List<ToolsData> allTools = toolsDAO.getAllToolsData();
            for (ToolsData tool : allTools) {
                ToolsCacheManager.cacheToolData(tool.getId(), tool);
            }
        } catch (SQLException exception) {
            throw new RuntimeException(exception);
        }
        CoinsCurrency.setupEconomy();
    }

    public void onUnload() {
        DatabaseConnection.getInstance().close();
    }

    public void registerDatabase() {
        DatabaseConnection.getInstance();
        playersDAO = new PlayersDAO();
        playersDAO.initializeDatabase();
        toolsDAO = new ToolsDAO();
        toolsDAO.initializeDatabase();

        new PlayersSaveTask(playersDAO).runTaskTimerAsynchronously(plugin, 0, 20L * 3);
        new ToolsSaveTask(toolsDAO).runTaskTimerAsynchronously(plugin, 0, 20L * 3);
    }

    private void registerListeners() {
        pluginManager.registerEvents(new PlayersJoinListener(playersDAO), plugin);
        pluginManager.registerEvents(new PlayersQuitListener(playersDAO), plugin);
        pluginManager.registerEvents(new BossesAreaListener(), plugin);
    }

    private void registerCommands() {
        // Command
        commandManager.registerCommand(new BossesCommand());

        // Subcommands
        commandManager.registerCommand(new ToolsCommand());
        commandManager.registerCommand(new SetSpawnCommand());
        commandManager.registerCommand(new GiveCommand());
        commandManager.registerCommand(new ReloadCommand());

        // Completions
        commandManager.getCommandCompletions().registerCompletion("bosses", c -> {
            ArrayList<String> array = new ArrayList<>();
            array.add("pegrande");
           return array;
        });
        commandManager.getCommandCompletions().registerCompletion("amount", c -> {
            ArrayList<String> array = new ArrayList<>();
            for (int i = 0; i < 101; i++) {
                array.add(String.valueOf(i));
            }
            return array;
        });
    }

    public static PluginImpl getInstance() {
        return instance;
    }
}
