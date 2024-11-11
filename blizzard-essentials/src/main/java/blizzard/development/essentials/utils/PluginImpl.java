package blizzard.development.essentials.utils;

import blizzard.development.essentials.Main;
import blizzard.development.essentials.commands.CommandRegistry;
import blizzard.development.essentials.listeners.ListenerRegistry;
import blizzard.development.essentials.tasks.AnnounceTask;
import blizzard.development.essentials.utils.config.ConfigUtils;
import co.aikar.commands.Locales;
import co.aikar.commands.PaperCommandManager;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

public class PluginImpl {
    public final Plugin plugin;
    private static PluginImpl instance;
    private static PaperCommandManager commandManager;
    public ConfigUtils Config;
    public ConfigUtils Locations;
    public ConfigUtils Database;

    public PluginImpl(Plugin plugin) {
        this.plugin = plugin;
        instance = this;
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
        AnnounceTask announceTask = new AnnounceTask();
        announceTask.runTaskTimerAsynchronously(Main.getInstance(), 20 * 5, 60 * 20);
    }

    private void registerCommands() {
        CommandRegistry commandRegistry = new CommandRegistry();
        commandRegistry.register();
    }

    public static PluginImpl getInstance() {
        return instance;
    }
}
