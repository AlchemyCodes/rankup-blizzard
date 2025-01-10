package blizzard.development.mine.utils;

import blizzard.development.mine.commands.CommandRegistry;
import blizzard.development.mine.database.dao.PlayerDAO;
import blizzard.development.mine.listeners.ListenerRegistry;
import blizzard.development.visuals.utils.config.ConfigUtils;
import co.aikar.commands.Locales;
import co.aikar.commands.PaperCommandManager;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

public class PluginImpl {

    public final Plugin plugin;
    private static PluginImpl instance;
    private static PaperCommandManager commandManager;
    private PlayerDAO playerDAO;
    public ConfigUtils Config;
    public ConfigUtils Locations;
    public ConfigUtils Ranking;
    public ConfigUtils Database;

    public PluginImpl(Plugin plugin) {
        this.plugin = plugin;
        instance = this;
        playerDAO = new PlayerDAO();
        commandManager = new PaperCommandManager(plugin);
        commandManager.getLocales().setDefaultLocale(Locales.PORTUGUESE);
        Config = new ConfigUtils((JavaPlugin) plugin, "config.yml");
        Locations = new ConfigUtils((JavaPlugin) plugin, "locations.yml");
        Ranking = new ConfigUtils((JavaPlugin) plugin, "ranking.yml");
        Database = new ConfigUtils((JavaPlugin) plugin, "database.yml");
    }

    public void onEnable() {
        try {
            commandManager = new PaperCommandManager(plugin);
            commandManager.getLocales().setDefaultLocale(Locales.PORTUGUESE);
        } catch (IllegalStateException e) {
            throw new RuntimeException("Erro ao inicializar PaperCommandManager", e);
        }


        Config.saveDefaultConfig();
        Locations.saveDefaultConfig();
        Ranking.saveDefaultConfig();
        Database.saveDefaultConfig();
        registerDatabase();
        registerListeners();
        registerCommands();
        registerTasks();
    }

    public void onDisable() {
    }

    public void registerDatabase() {
        playerDAO = new PlayerDAO();
        playerDAO.initializeDatabase();
    }

    private void registerListeners() {
        ListenerRegistry listenerRegistry = new ListenerRegistry(playerDAO);
        listenerRegistry.register();
        listenerRegistry.registerPacket();
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