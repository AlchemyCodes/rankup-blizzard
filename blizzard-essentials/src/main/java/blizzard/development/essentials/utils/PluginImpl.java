package blizzard.development.essentials.utils;

import blizzard.development.essentials.commands.EssentialsCommand;
import blizzard.development.essentials.commands.essentials.*;
import blizzard.development.essentials.commands.commons.*;
import blizzard.development.essentials.commands.staff.*;
import blizzard.development.essentials.listeners.ListenerRegistry;
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
        Config.saveDefaultConfig();
        Database.saveDefaultConfig();
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
        commandManager.registerCommand(new EssentialsCommand());
        commandManager.registerCommand(new PingCommand());
        commandManager.registerCommand(new HealCommand());
        commandManager.registerCommand(new GamemodeCommand());
        commandManager.registerCommand(new FlyCommand());
        commandManager.registerCommand(new ClearChatCommand());
        commandManager.registerCommand(new GameModeCommand());
        commandManager.registerCommand(new ClearChatCommand());
        commandManager.registerCommand(new ClearCommand());
        commandManager.registerCommand(new HealCommand());
        commandManager.registerCommand(new SetSpawnCommand());
        commandManager.registerCommand(new SpawnCommand());
        commandManager.registerCommand(new SetWarpCommand());
        commandManager.registerCommand(new WarpCommand());
        commandManager.registerCommand(new TrashCommand());
        commandManager.registerCommand(new CraftCommand());
        commandManager.registerCommand(new TpallCommand());
        commandManager.registerCommand(new PingCommand());
        commandManager.registerCommand(new VanishCommand());
        commandManager.registerCommand(new EchestCommand());
    }

    public static PluginImpl getInstance() {
        return instance;
    }
}
