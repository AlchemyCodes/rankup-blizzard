package blizzard.development.currencies.utils;

import blizzard.development.currencies.Main;
import blizzard.development.currencies.commands.CommandRegistry;
import blizzard.development.currencies.currencies.CoinsCurrency;
import blizzard.development.currencies.database.DatabaseConnection;
import blizzard.development.currencies.database.cache.PlayersCacheManager;
import blizzard.development.currencies.database.dao.PlayersDAO;
import blizzard.development.currencies.database.storage.PlayersData;
import blizzard.development.currencies.extensions.PlaceholderAPIExtension;
import blizzard.development.currencies.listeners.ListenerRegistry;
import blizzard.development.currencies.tasks.PlayersSaveTask;
import blizzard.development.currencies.utils.config.ConfigUtils;
import co.aikar.commands.Locales;
import co.aikar.commands.PaperCommandManager;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.sql.SQLException;
import java.util.List;

public class PluginImpl {
    public final Plugin plugin;
    private static PluginImpl instance;
    private static PluginManager pluginManager;
    private static PaperCommandManager commandManager;
    public PlayersDAO playersDAO;

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
        registerCommands();
        CoinsCurrency.getInstance().setupEconomy();
        registerExtensions();
    }

    public void onUnload() {
        DatabaseConnection.getInstance().close();
    }

    public void registerDatabase() {
        DatabaseConnection.getInstance();
        playersDAO = new PlayersDAO();
        playersDAO.initializeDatabase();

        List<PlayersData> players = playersDAO.getAllPlayers();
        for (PlayersData player : players) {
            PlayersCacheManager.getInstance().cachePlayerData(player.getNickname(), player);
        }

        new PlayersSaveTask(playersDAO).runTaskTimerAsynchronously(plugin, 0, 20L * 3);
    }

    private void registerListeners() {
        ListenerRegistry.getInstance().register();
    }

    private void registerCommands() {
        CommandRegistry.getInstance().register();
    }

    public void registerExtensions() {
        if (pluginManager.getPlugin("PlaceholderAPI") != null) {
            new PlaceholderAPIExtension((Main) plugin).register();
        }
    }

    public static PluginImpl getInstance() {
        return instance;
    }
}
