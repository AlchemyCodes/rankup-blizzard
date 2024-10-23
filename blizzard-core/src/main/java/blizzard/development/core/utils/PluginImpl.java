package blizzard.development.core.utils;

import blizzard.development.core.commands.CommandRegistry;
import blizzard.development.core.database.DatabaseConnection;
import blizzard.development.core.database.dao.PlayersDAO;
import blizzard.development.core.listener.ListenerRegistry;
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
        this.Config = new ConfigUtils((JavaPlugin)plugin, "config.yml");
        this.Database = new ConfigUtils((JavaPlugin)plugin, "database.yml");
    }

    public void onLoad() {
        this.Config.saveDefaultConfig();
        this.Database.saveDefaultConfig();
        registerDatabase();
        registerListeners();
        registerTasks();
        registerCommands();
    }

    public void onUnload() {}

    public void registerDatabase() {
        DatabaseConnection.getInstance();
        playersDAO = new PlayersDAO();
        playersDAO.initializeDatabase();
        (new PlayerSaveTask(playersDAO)).runTaskTimerAsynchronously(this.plugin, 0L, 60L);
    }

    private void registerListeners() {
        ListenerRegistry listenerRegistry = new ListenerRegistry(playersDAO);
        listenerRegistry.register();
    }

    private void registerTasks() {}

    private void registerCommands() {
        CommandRegistry commandRegistry = new CommandRegistry();
        commandRegistry.register();
    }

    public static PluginImpl getInstance() {
        return instance;
    }
}
