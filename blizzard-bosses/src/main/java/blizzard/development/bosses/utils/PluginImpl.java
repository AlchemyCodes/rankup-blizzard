package blizzard.development.bosses.utils;

import blizzard.development.bosses.commands.BossesCommand;
import blizzard.development.bosses.commands.subcommands.*;
import blizzard.development.bosses.database.DatabaseConnection;
import blizzard.development.bosses.database.cache.ToolsCacheManager;
import blizzard.development.bosses.database.dao.PlayersDAO;
import blizzard.development.bosses.database.dao.ToolsDAO;
import blizzard.development.bosses.database.storage.ToolsData;
import blizzard.development.bosses.handlers.eggs.BigFootEgg;
import blizzard.development.bosses.listeners.bosses.BossesAreaListener;
import blizzard.development.bosses.listeners.bosses.BossesGeneralListener;
import blizzard.development.bosses.listeners.commons.PlayersJoinListener;
import blizzard.development.bosses.listeners.commons.PlayersQuitListener;
import blizzard.development.bosses.managers.BatchManager;
import blizzard.development.bosses.methods.GeneralMethods;
import blizzard.development.bosses.tasks.PlayersSaveTask;
import blizzard.development.bosses.tasks.ToolsSaveTask;
import blizzard.development.bosses.utils.config.ConfigUtils;
import co.aikar.commands.Locales;
import co.aikar.commands.PaperCommandManager;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
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
    }

    public void onUnload() {
        DatabaseConnection.getInstance().close();
        for (Player player : Bukkit.getOnlinePlayers()) {
            GeneralMethods.removePlayerFromWorld(player);
        }
    }

    public void registerDatabase() {
        DatabaseConnection.getInstance();
        playersDAO = new PlayersDAO();
        playersDAO.initializeDatabase();
        toolsDAO = new ToolsDAO();
        toolsDAO.initializeDatabase();

        new PlayersSaveTask(playersDAO).runTaskTimerAsynchronously(plugin, 0, 20L * 3);
        new ToolsSaveTask(toolsDAO).runTaskTimerAsynchronously(plugin, 0, 20L * 3);

        try {
            List<ToolsData> allTools = toolsDAO.getAllToolsData();
            for (ToolsData tool : allTools) {
                ToolsCacheManager.cacheToolData(tool.getId(), tool);
            }
        } catch (SQLException exception) {
            throw new RuntimeException(exception);
        }
    }

    private void registerListeners() {
        // Commons
        pluginManager.registerEvents(new PlayersJoinListener(playersDAO), plugin);
        pluginManager.registerEvents(new PlayersQuitListener(playersDAO), plugin);

        // Bosses
        pluginManager.registerEvents(new BossesAreaListener(), plugin);
        pluginManager.registerEvents(new BossesGeneralListener(), plugin);

        // Eggs
        pluginManager.registerEvents(new BigFootEgg(), plugin);
    }

    private void registerCommands() {
        // Command
        commandManager.registerCommand(new BossesCommand());

        // Subcommands
        commandManager.registerCommand(new ToolsCommand());
        commandManager.registerCommand(new SetSpawnCommand());
        commandManager.registerCommand(new GiveCommand());
        commandManager.registerCommand(new GoCommand());
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
