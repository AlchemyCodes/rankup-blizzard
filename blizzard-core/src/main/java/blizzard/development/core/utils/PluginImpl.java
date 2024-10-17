package blizzard.development.core.utils;

import blizzard.development.core.commands.SetTemperature;
import blizzard.development.core.database.DatabaseConnection;
import blizzard.development.core.database.dao.PlayersDAO;
import blizzard.development.core.listener.TrafficListener;
import blizzard.development.core.tasks.BlizzardTask;
import blizzard.development.core.tasks.PlayerSaveTask;
import blizzard.development.core.utils.config.ConfigUtils;
import co.aikar.commands.Locales;
import co.aikar.commands.PaperCommandManager;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public class PluginImpl {
    public final Plugin plugin;
    private static PluginImpl instance;
    private static PluginManager pluginManager;
    private static PaperCommandManager commandManager;

    private static PlayersDAO playersDAO;

    public ConfigUtils Config;
    public ConfigUtils Database;

    public PluginImpl(Plugin plugin) {
        this.plugin = plugin;
        instance = this;
        commandManager = new PaperCommandManager(plugin);
        pluginManager = Bukkit.getPluginManager();
        commandManager.getLocales().setDefaultLocale(Locales.PORTUGUESE);

        playersDAO = new PlayersDAO();

        Config = new ConfigUtils((JavaPlugin) plugin, "config.yml");
        Database = new ConfigUtils((JavaPlugin) plugin, "database.yml");
    }

    public void onLoad() {
        Config.saveDefaultConfig();
        Database.saveDefaultConfig();
        registerDatabase();
        registerListeners();
        registerTasks();
        registerCommands();
    }

    public void onUnload() {
    }

    public void registerDatabase() {
        DatabaseConnection.getInstance();
        playersDAO = new PlayersDAO();
        playersDAO.initializeDatabase();

        new PlayerSaveTask(playersDAO).runTaskTimerAsynchronously(plugin, 0, 20L * 3);
    }

    private void registerListeners() {
        pluginManager.registerEvents(new TrafficListener(playersDAO), plugin);
    }

    private void registerTasks() {
        new BlizzardTask();
    }

    private void registerCommands() {
        commandManager.registerCommand(new SetTemperature());

    }

    public static PluginImpl getInstance() {
        return instance;
    }
}
