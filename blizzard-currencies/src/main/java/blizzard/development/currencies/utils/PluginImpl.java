package blizzard.development.currencies.utils;

import blizzard.development.currencies.commands.CurrenciesCommand;
import blizzard.development.currencies.commands.currencies.bosses.SoulsCommand;
import blizzard.development.currencies.commands.currencies.bosses.subcommands.SoulsExchangeCommand;
import blizzard.development.currencies.database.DatabaseConnection;
import blizzard.development.currencies.database.dao.PlayersDAO;
import blizzard.development.currencies.listeners.PlayersJoinListener;
import blizzard.development.currencies.listeners.PlayersQuitListener;
import blizzard.development.currencies.tasks.PlayersSaveTask;
import blizzard.development.currencies.utils.config.ConfigUtils;
import co.aikar.commands.Locales;
import co.aikar.commands.PaperCommandManager;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;

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
    }

    public void onUnload() {
        DatabaseConnection.getInstance().close();
    }

    public void registerDatabase() {
        DatabaseConnection.getInstance();
        playersDAO = new PlayersDAO();
        playersDAO.initializeDatabase();

        new PlayersSaveTask(playersDAO).runTaskTimerAsynchronously(plugin, 0, 20L * 3);
    }

    private void registerListeners() {
        pluginManager.registerEvents(new PlayersJoinListener(playersDAO), plugin);
        pluginManager.registerEvents(new PlayersQuitListener(playersDAO), plugin);
    }

    private void registerCommands() {
        // Commands
        commandManager.registerCommand(new CurrenciesCommand());
        commandManager.registerCommand(new SoulsCommand());

        // Subcommands
        commandManager.registerCommand(new SoulsExchangeCommand());

        // Completions
        commandManager.getCommandCompletions().registerCompletion("amount", c -> {
            ArrayList<String> array = new ArrayList<>();
            for (int i = 0; i < 1000001; i++) {
                array.add(String.valueOf(i));
            }
            return array;
        });
    }

    public static PluginImpl getInstance() {
        return instance;
    }
}
