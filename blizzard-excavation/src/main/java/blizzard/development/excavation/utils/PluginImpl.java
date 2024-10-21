package blizzard.development.excavation.utils;

import blizzard.development.excavation.Main;
import blizzard.development.excavation.commands.CommandRegistry;
import blizzard.development.excavation.database.DatabaseConnection;
import blizzard.development.excavation.database.dao.ExcavatorDAO;
import blizzard.development.excavation.database.dao.PlayerDAO;
import blizzard.development.excavation.listeners.ListenerRegistry;
import blizzard.development.excavation.tasks.PlayerSaveTask;
import blizzard.development.excavation.utils.config.ConfigUtils;
import co.aikar.commands.Locales;
import co.aikar.commands.PaperCommandManager;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

public class PluginImpl {
    public final Plugin plugin;
    private static PluginImpl instance;
    private static PaperCommandManager commandManager;
    private static ExcavatorDAO excavatorDAO;
    private static PlayerDAO playerDAO;
    public ConfigUtils Config;
    public ConfigUtils Locations;
    public ConfigUtils Database;

    public PluginImpl(Plugin plugin) {
        this.plugin = plugin;
        instance = this;
        commandManager = new PaperCommandManager(plugin);
        commandManager.getLocales().setDefaultLocale(Locales.PORTUGUESE);
        excavatorDAO = new ExcavatorDAO();
        playerDAO = new PlayerDAO();
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
        registerTasks();
        registerCommands();

        PlayerSaveTask playerSaveTask = new PlayerSaveTask(playerDAO);
        playerSaveTask.runTaskTimerAsynchronously(Main.getInstance(), 0L, 20 * 5);
    }

    public void onUnload() {
    }

    public void registerDatabase() {
        playerDAO = new PlayerDAO();
        playerDAO.initializeDatabase();

        excavatorDAO = new ExcavatorDAO();
        excavatorDAO.initializeDatabase();
    }

    private void registerListeners() {
        ListenerRegistry listenerRegistry = new ListenerRegistry(excavatorDAO, playerDAO);
        listenerRegistry.register();
    }

    private void registerTasks() {

    }

    private void registerCommands() {
        CommandRegistry commandRegistry = new CommandRegistry();
        commandRegistry.register();
    }

    public static PluginImpl getInstance() {
        return instance;
    }
}
