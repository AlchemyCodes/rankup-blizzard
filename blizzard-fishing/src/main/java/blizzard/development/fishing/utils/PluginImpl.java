package blizzard.development.fishing.utils;

import blizzard.development.fishing.commands.CommandRegistry;
import blizzard.development.fishing.database.DatabaseConnection;
import blizzard.development.fishing.database.cache.PlayersCacheManager;
import blizzard.development.fishing.database.dao.PlayersDAO;
import blizzard.development.fishing.database.dao.RodsDAO;
import blizzard.development.fishing.listeners.ListenerRegistry;
import blizzard.development.fishing.tasks.FishingNetTask;
import blizzard.development.fishing.tasks.FishingTask;
import blizzard.development.fishing.tasks.PlayerSaveTask;
import blizzard.development.fishing.tasks.RodSaveTask;
import co.aikar.commands.Locales;
import co.aikar.commands.PaperCommandManager;
import lombok.Getter;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.sql.SQLException;

public class PluginImpl {
    public PlayersDAO playersDAO;
    public RodsDAO rodsDAO;
    public final Plugin plugin;

    @Getter
    private static PluginImpl instance;
    private static PaperCommandManager commandManager;

    public ConfigUtils Database;
    public ConfigUtils Config;
    public ConfigUtils Enchantments;

    public PluginImpl(Plugin plugin) {
        this.plugin = plugin;
        instance = this;

        commandManager = new PaperCommandManager(plugin);
        commandManager.getLocales().setDefaultLocale(Locales.PORTUGUESE);

        this.Database = new ConfigUtils((JavaPlugin)plugin, "database.yml");
        this.Config = new ConfigUtils((JavaPlugin)plugin, "config.yml");
        this.Enchantments = new ConfigUtils((JavaPlugin)plugin, "enchantments.yml");
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
        if (!this.Enchantments.existsConfig()) {
            this.Enchantments.saveDefaultConfig();
        }


        this.Database.reloadConfig();
        this.Config.reloadConfig();
        this.Enchantments.reloadConfig();

    }

    public void onUnload() {
        PlayersCacheManager.getInstance().playerCache.forEach((player, playersData) -> {
            try {
                this.playersDAO.updatePlayerData(playersData);
            } catch (SQLException exception) {
                throw new RuntimeException(exception);
            }
        });
    }

    public void registerDatabase() {
        DatabaseConnection.getInstance();
        this.playersDAO = new PlayersDAO();
        this.playersDAO.initializeDatabase();
        this.rodsDAO = new RodsDAO();
        this.rodsDAO.initializeDatabase();

        new PlayerSaveTask(playersDAO).runTaskTimerAsynchronously(plugin, 0L, 20L * 3);
        new RodSaveTask(rodsDAO).runTaskTimerAsynchronously(plugin, 0L, 20L * 3);
    }

    private void registerTasks() {
        new FishingTask(plugin);
        new FishingNetTask(plugin);
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
