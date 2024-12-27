package blizzard.development.monsters.utils;

import blizzard.development.monsters.commands.CommandRegistry;
import blizzard.development.monsters.database.DatabaseConnection;
import blizzard.development.monsters.database.cache.managers.PlayersCacheManager;
import blizzard.development.monsters.database.dao.PlayersDAO;
import blizzard.development.monsters.database.storage.PlayersData;
import blizzard.development.monsters.listeners.ListenerRegistry;
import blizzard.development.monsters.tasks.players.PlayersSaveTask;
import blizzard.development.monsters.utils.config.ConfigUtils;
import co.aikar.commands.Locales;
import co.aikar.commands.PaperCommandManager;
import lombok.Getter;
import lombok.SneakyThrows;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.List;

public class PluginImpl {
    public final Plugin plugin;
    private static @Getter PluginImpl instance;
    public PlayersDAO playersDAO;

    public ConfigUtils Config;
    public ConfigUtils Database;
    public ConfigUtils Monsters;
    public ConfigUtils Rewards;

    public PluginImpl(Plugin plugin) {
        this.plugin = plugin;
        instance = this;
        Config = new ConfigUtils((JavaPlugin) plugin, "config.yml");
        Database = new ConfigUtils((JavaPlugin) plugin, "database.yml");
        Monsters = new ConfigUtils((JavaPlugin) plugin, "monsters.yml");
        Rewards = new ConfigUtils((JavaPlugin) plugin, "rewards.yml");
    }

    public void onEnable() {
        Config.saveDefaultConfig();
        Database.saveDefaultConfig();
        Monsters.saveDefaultConfig();
        Rewards.saveDefaultConfig();

        PaperCommandManager commandManager = new PaperCommandManager(plugin);
        commandManager.getLocales().setDefaultLocale(Locales.PORTUGUESE);

        registerDatabase();
        registerListeners();
        registerCommands();
    }

    public void onDisable() {
        DatabaseConnection.getInstance().close();
    }

    public void registerDatabase() {
        DatabaseConnection.getInstance();
        playersDAO = new PlayersDAO();
        playersDAO.initializeDatabase();

        new PlayersSaveTask(playersDAO).runTaskTimerAsynchronously(plugin, 0, 20L * 3);

        setupData();
    }

    @SneakyThrows
    public void setupData() {
        List<PlayersData> players = playersDAO.getAllPlayersData();
        for (PlayersData player : players) {
            PlayersCacheManager.getInstance().cachePlayerData(player.getNickname(), player);
        }
    }

    private void registerListeners() {
        ListenerRegistry.getInstance().register();
    }

    private void registerCommands() {
        CommandRegistry.getInstance().register();
    }
}