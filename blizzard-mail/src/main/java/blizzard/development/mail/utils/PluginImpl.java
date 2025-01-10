package blizzard.development.mail.utils;

import blizzard.development.mail.commands.CommandRegistry;
import blizzard.development.mail.database.DatabaseConnection;
import blizzard.development.mail.database.PlayerSaveTask;
import blizzard.development.mail.database.dao.PlayerDao;
import blizzard.development.mail.listeners.ListenerRegistry;
import co.aikar.commands.Locales;
import co.aikar.commands.PaperCommandManager;
import lombok.Getter;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.sql.SQLException;

public class PluginImpl {
    public final Plugin plugin;

    @Getter
    private static PluginImpl instance;
    private static PaperCommandManager commandManager;

    public PlayerDao playersDAO;

    public ConfigUtils Database;
    public ConfigUtils Config;

    public PluginImpl(Plugin plugin) {
        this.plugin = plugin;
        instance = this;

        commandManager = new PaperCommandManager(plugin);
        commandManager.getLocales().setDefaultLocale(Locales.PORTUGUESE);

        this.Database = new ConfigUtils((JavaPlugin)plugin, "database.yml");
        this.Config = new ConfigUtils((JavaPlugin)plugin, "config.yml");
    }

    public void onLoad() {
        loadConfigs();
        registerDatabase();
        registerCommands();
        registerListeners();
        registerTasks();
    }

    public void loadConfigs() {
        if (!this.Database.existsConfig()) {
            this.Database.saveDefaultConfig();
        }
        if (!this.Config.existsConfig()) {
            this.Config.saveDefaultConfig();
        }

        this.Database.reloadConfig();
        this.Config.reloadConfig();
    }

    public void onUnload() {

    }

    public void registerDatabase() {
        DatabaseConnection.getInstance();
        this.playersDAO = new PlayerDao();
        this.playersDAO.initializeDatabase();

        new PlayerSaveTask(playersDAO).runTaskTimerAsynchronously(plugin, 0L, 20L * 3);
    }

    private void registerTasks() {
    }

    private void registerListeners() {
        ListenerRegistry listenerRegistry = new ListenerRegistry();
        listenerRegistry.register();
    }

    private void registerCommands() {
        CommandRegistry commandRegistry = new CommandRegistry();
        commandRegistry.register();
    }

    public File getDataFolder() {
        return this.plugin.getDataFolder();
    }
}
