package blizzard.development.bosses.utils;

import blizzard.development.bosses.commands.BossesCommand;
import blizzard.development.bosses.database.DatabaseConnection;
import blizzard.development.bosses.database.cache.ToolsCacheManager;
import blizzard.development.bosses.database.dao.PlayersDAO;
import blizzard.development.bosses.database.dao.ToolsDAO;
import blizzard.development.bosses.database.storage.ToolsData;
import blizzard.development.bosses.listeners.commons.PlayersJoinListener;
import blizzard.development.bosses.listeners.commons.PlayersQuitListener;
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
import java.util.List;

public class PluginImpl {
    public final Plugin plugin;
    private static PluginImpl instance;
    private static PluginManager pluginManager;
    private static PaperCommandManager commandManager;
    public PlayersDAO playersDAO;
    public ToolsDAO toolsDAO;

    public ConfigUtils Database;
    public ConfigUtils Config;
    public PluginImpl(Plugin plugin) {
        this.plugin = plugin;
        instance = this;
        pluginManager = Bukkit.getPluginManager();
        commandManager = new PaperCommandManager(plugin);
        commandManager.getLocales().setDefaultLocale(Locales.PORTUGUESE);
        Database = new ConfigUtils((JavaPlugin) plugin, "database.yml");
        Config = new ConfigUtils((JavaPlugin) plugin, "config.yml");
    }

    public void onLoad() {
        Config.saveDefaultConfig();
        Database.saveDefaultConfig();
        registerDatabase();
        registerListeners();
        registerCommands();
        try {
            List<ToolsData> allTools = toolsDAO.getAllToolsData();
            for (ToolsData tool : allTools) {
                ToolsCacheManager.cacheToolData(tool.getId(), tool);
            }
        } catch (SQLException exception) {
            throw new RuntimeException(exception);
        }
    }



    public void onUnload() {
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
    }

    private void registerCommands() {
        commandManager.registerCommand(new BossesCommand());
    }

    public static PluginImpl getInstance() {
        return instance;
    }
}
