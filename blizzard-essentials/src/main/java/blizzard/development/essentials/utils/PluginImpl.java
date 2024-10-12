package blizzard.development.essentials.utils;

import blizzard.development.essentials.commands.EssentialsCommand;
import blizzard.development.essentials.commands.essentials.*;
import blizzard.development.essentials.commands.commons.*;
import blizzard.development.essentials.commands.staff.GameModeCommand;
import blizzard.development.essentials.utils.config.ConfigUtils;
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

    public ConfigUtils Config;
    public ConfigUtils Database;
    public PluginImpl(Plugin plugin) {
        this.plugin = plugin;
        instance = this;
        pluginManager = Bukkit.getPluginManager();
        commandManager = new PaperCommandManager(plugin);
        commandManager.getLocales().setDefaultLocale(Locales.PORTUGUESE);
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
        Config.saveDefaultConfig();
        Database.saveDefaultConfig();
    }

    public void onUnload() {
    }

    public void registerDatabase() {
    }

    private void registerListeners() {
        // pluginManager.registerEvents(new LimitListener(), plugin);
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
        commandManager.registerCommand(new PingCommand());
    }

    public static PluginImpl getInstance() {
        return instance;
    }
}
