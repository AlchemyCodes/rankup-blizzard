package blizzard.development.time.utils;

import blizzard.development.time.commands.CommandRegistry;
import blizzard.development.time.database.DatabaseConnection;
import blizzard.development.time.database.dao.PlayersDAO;
import blizzard.development.time.listeners.ListenerRegistry;
import blizzard.development.time.tasks.PlayersSaveTask;
import blizzard.development.time.utils.config.ConfigUtils;
import co.aikar.commands.Locales;
import co.aikar.commands.PaperCommandManager;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

public class PluginImpl {
    public final Plugin plugin;
    private static PluginImpl instance;
    public PlayersDAO playersDAO;

    public ConfigUtils Config;
    public ConfigUtils Database;

    public PluginImpl(Plugin plugin) {
        this.plugin = plugin;
        instance = this;
        Config = new ConfigUtils((JavaPlugin) plugin, "config.yml");
        Database = new ConfigUtils((JavaPlugin) plugin, "database.yml");
    }

    public void onEnable() {
        Config.saveDefaultConfig();

        PaperCommandManager commandManager = new PaperCommandManager(plugin);
        commandManager.getLocales().setDefaultLocale(Locales.PORTUGUESE);

        registerDatabase();
        registerListeners();
        registerCommands();
        registerTasks();
    }

    public void onDisable() {}

    public void registerDatabase() {
        DatabaseConnection.getInstance();
        playersDAO = new PlayersDAO();
        playersDAO.initializeDatabase();

        new PlayersSaveTask(playersDAO).runTaskTimerAsynchronously(plugin, 0, 20L * 3);
    }

    private void registerListeners() {
        ListenerRegistry.getInstance().register();
    }

    private void registerCommands() {
        CommandRegistry.getInstance().register();
    }

    private void registerTasks() {
        //new TopsMessageTask().runTaskTimerAsynchronously(plugin, 0, 20L * 600);
    }

    public static PluginImpl getInstance() {
        return instance;
    }
}