package blizzard.development.crates.utils;

import blizzard.development.crates.commands.CommandRegistry;
import blizzard.development.crates.listeners.ListenerRegistry;
import blizzard.development.crates.utils.config.ConfigUtils;
import co.aikar.commands.Locales;
import co.aikar.commands.PaperCommandManager;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

public class PluginImpl {
    public final Plugin plugin;
    private static PluginImpl instance;
    private static PaperCommandManager commandManager;
    public ConfigUtils Config;
    public ConfigUtils Crates;
    public ConfigUtils Locations;
    public ConfigUtils Database;

    public PluginImpl(Plugin plugin) {
        this.plugin = plugin;
        instance = this;
        commandManager = new PaperCommandManager(plugin);
        commandManager.getLocales().setDefaultLocale(Locales.PORTUGUESE);
        Config = new ConfigUtils((JavaPlugin) plugin, "config.yml");
        Crates = new ConfigUtils((JavaPlugin) plugin, "crates.yml");
        Locations = new ConfigUtils((JavaPlugin) plugin, "locations.yml");
        Database = new ConfigUtils((JavaPlugin) plugin, "database.yml");
    }

    public void onLoad() {
        Config.saveDefaultConfig();
        Crates.saveDefaultConfig();
        Locations.saveDefaultConfig();
        Database.saveDefaultConfig();
        registerDatabase();
        registerListeners();
        registerTasks();
        registerCommands();
    }

    public void onUnload() {
    }

    public void registerDatabase() {
    }

    private void registerListeners() {
        ListenerRegistry listenerRegistry = new ListenerRegistry();
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
