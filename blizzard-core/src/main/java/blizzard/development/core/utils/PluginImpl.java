package blizzard.development.core.utils;

import blizzard.development.core.Main;
import blizzard.development.core.commands.CommandRegistry;
import blizzard.development.core.database.DatabaseConnection;
import blizzard.development.core.database.dao.PlayersDAO;
import blizzard.development.core.listener.ListenerRegistry;
import blizzard.development.core.placeholder.PlaceholderRegistry;
import blizzard.development.core.tasks.BlizzardTask;
import blizzard.development.core.tasks.CoreDebugTask;
import blizzard.development.core.tasks.PlayerSaveTask;
import blizzard.development.core.tasks.TemperatureDecayTask;
import blizzard.development.core.utils.config.ConfigUtils;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public class PluginImpl {
    public final Plugin plugin;
    private static PluginImpl instance;
    private static PlayersDAO playersDAO;
    private static ProtocolManager protocolManager;
    private static PluginManager pluginManager;
    public ConfigUtils Config;
    public ConfigUtils Database;
    public ConfigUtils Coordinates;

    public PluginImpl(Plugin plugin) {
        this.plugin = plugin;
        instance = this;
        playersDAO = new PlayersDAO();
        protocolManager = ProtocolLibrary.getProtocolManager();
        pluginManager = Bukkit.getPluginManager();
        this.Config = new ConfigUtils((JavaPlugin)plugin, "config.yml");
        this.Database = new ConfigUtils((JavaPlugin)plugin, "database.yml");
        this.Coordinates = new ConfigUtils((JavaPlugin)plugin, "coordinates.yml");
    }

    public void onLoad() {
        if (pluginManager.getPlugin("PlaceholderAPI") != null) {
            new PlaceholderRegistry((Main) plugin).register();
        }

        this.Config.saveDefaultConfig();
        this.Database.saveDefaultConfig();
        this.Coordinates.saveDefaultConfig();
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
        (new PlayerSaveTask(playersDAO)).runTaskTimerAsynchronously(plugin, 0L, 60L);
    }

    private void registerListeners() {
        ListenerRegistry listenerRegistry = new ListenerRegistry(playersDAO, protocolManager);
        listenerRegistry.register();
    }

    private void registerTasks() {
        new TemperatureDecayTask();
        new CoreDebugTask();
//        new BlizzardTask();
    }

    private void registerCommands() {
        CommandRegistry commandRegistry = new CommandRegistry();
        commandRegistry.register();
    }

    public static PluginImpl getInstance() {
        return instance;
    }
}
