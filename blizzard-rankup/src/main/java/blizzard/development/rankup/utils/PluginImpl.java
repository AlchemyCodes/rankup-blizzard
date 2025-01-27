package blizzard.development.rankup.utils;

import blizzard.development.rankup.Main;
import blizzard.development.rankup.commands.*;
import blizzard.development.rankup.database.DatabaseConnection;
import blizzard.development.rankup.database.cache.PlayersCacheManager;
import blizzard.development.rankup.database.dao.PlayersDAO;
import blizzard.development.rankup.database.storage.PlayersData;
import blizzard.development.rankup.extensions.nchat.RanksPlaceholderExtension;
import blizzard.development.rankup.listeners.TrafficListener;
import blizzard.development.rankup.placeholder.PlaceholderRegistry;
import blizzard.development.rankup.tasks.AutoRankup;
import blizzard.development.rankup.tasks.PlayerSaveTask;
import java.io.File;
import java.sql.SQLException;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.Locales;
import co.aikar.commands.PaperCommandManager;
import com.nickuc.chat.api.nChatAPI;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public class PluginImpl {
    public PlayersDAO playersDAO;
    public final Plugin plugin;
    private static PluginImpl instance;
    private static PaperCommandManager commandManager;
    private static PluginManager pluginManager;
    public ConfigUtils Database;
    public ConfigUtils Config;
    public ConfigUtils Messages;
    public ConfigUtils Ranks;
    public ConfigUtils Prestige;
    public ConfigUtils Inventories;

    public PluginImpl(Plugin plugin) {
        this.plugin = plugin;
        instance = this;
        commandManager = new PaperCommandManager(plugin);

        commandManager.getLocales().setDefaultLocale(Locales.PORTUGUESE);
        pluginManager = Bukkit.getPluginManager();

        this.Database = new ConfigUtils((JavaPlugin)plugin, "database.yml");
        this.Config = new ConfigUtils((JavaPlugin)plugin, "config.yml");
        this.Messages = new ConfigUtils((JavaPlugin)plugin, "messages.yml");
        this.Ranks = new ConfigUtils((JavaPlugin)plugin, "ranks.yml");
        this.Prestige = new ConfigUtils((JavaPlugin)plugin, "prestige.yml");
        this.Inventories = new ConfigUtils((JavaPlugin)plugin, "inventories.yml");
    }

    public void onLoad() {
        if (pluginManager.getPlugin("PlaceholderAPI") != null) {
            new PlaceholderRegistry((Main) plugin).register();
        }

        loadConfigs();
        registerDatabase();
        registerCommands();
        registerListeners();
        registerTasks();
        registerExtensions();
    }

    public void loadConfigs() {
        if (!this.Database.existsConfig()) {
            this.Database.saveDefaultConfig();
        }
        if (!this.Config.existsConfig()) {
            this.Config.saveDefaultConfig();
        }
        if (!this.Messages.existsConfig()) {
            this.Messages.saveDefaultConfig();
        }
        if (!this.Ranks.existsConfig()) {
            this.Ranks.saveDefaultConfig();
        }
        if (!this.Prestige.existsConfig()) {
            this.Prestige.saveDefaultConfig();
        }
        if (!this.Inventories.existsConfig()) {
            this.Inventories.saveDefaultConfig();
        }

        this.Database.reloadConfig();
        this.Config.reloadConfig();
        this.Messages.reloadConfig();
        this.Ranks.reloadConfig();
        this.Prestige.reloadConfig();
        this.Inventories.reloadConfig();
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

        (new PlayerSaveTask(this.playersDAO)).runTaskTimerAsynchronously(this.plugin, 0L, 60L);
    }

    private void registerTasks() {
        new AutoRankup(plugin);
    }

    private void registerListeners() {
        pluginManager.registerEvents(new TrafficListener(this.playersDAO), this.plugin);
    }

    private void registerCommands() {
        commandManager.registerCommand(new RankCommand());
        commandManager.registerCommand(new RanksCommand());
        commandManager.registerCommand(new RankUpCommand());
        commandManager.registerCommand(new PrestigeCommand());
        commandManager.registerCommand(new ReloadConfig());
    }

    private void registerExtensions() {
        nChatAPI api = nChatAPI.getApi();
        if (api != null) {
            api.setGlobalTag("rankup_rank", new RanksPlaceholderExtension(plugin));
        }
    }

    public static PluginImpl getInstance() {
        return instance;
    }

    public File getDataFolder() {
        return this.plugin.getDataFolder();
    }
}
