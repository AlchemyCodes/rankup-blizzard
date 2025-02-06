package blizzard.development.vips.utils;

import blizzard.development.vips.commands.CommandRegistry;
import blizzard.development.vips.database.DatabaseConnection;
import blizzard.development.vips.database.cache.KeysCacheManager;
import blizzard.development.vips.database.cache.PlayersCacheManager;
import blizzard.development.vips.database.dao.KeysDao;
import blizzard.development.vips.database.dao.PlayersDAO;
import blizzard.development.vips.database.storage.KeysData;
import blizzard.development.vips.database.storage.PlayersData;
import blizzard.development.vips.listeners.ListenerRegistry;
import blizzard.development.vips.tasks.PlayerSaveTask;
import blizzard.development.vips.tasks.VipTask;
import co.aikar.commands.Locales;
import co.aikar.commands.PaperCommandManager;
import lombok.Getter;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.sql.SQLException;
import java.util.List;

public class PluginImpl {
    public PlayersDAO playersDAO;
    public KeysDao keysDAO;
    public final Plugin plugin;

    @Getter
    private static PluginImpl instance;
    private static PaperCommandManager commandManager;

    public ConfigUtils Database;
    public ConfigUtils Config;
    public ConfigUtils Messages;
    public ConfigUtils Inventories;


    public PluginImpl(Plugin plugin) {
        this.plugin = plugin;
        instance = this;

        commandManager = new PaperCommandManager(plugin);
        commandManager.getLocales().setDefaultLocale(Locales.PORTUGUESE);

        this.Database = new ConfigUtils((JavaPlugin)plugin, "database.yml");
        this.Config = new ConfigUtils((JavaPlugin)plugin, "config.yml");
        this.Messages = new ConfigUtils((JavaPlugin)plugin, "messages.yml");
        this.Inventories = new ConfigUtils((JavaPlugin)plugin, "inventories.yml");
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
        if (!this.Messages.existsConfig()) {
            this.Messages.saveDefaultConfig();
        }
        if (!this.Inventories.existsConfig()) {
            this.Inventories.saveDefaultConfig();
        }

        this.Database.reloadConfig();
        this.Config.reloadConfig();
        this.Messages.reloadConfig();
        this.Inventories.reloadConfig();
    }

    public void onUnload() {
        KeysCacheManager.getInstance().keysCache.forEach((key, keysData) -> {
            try {
                this.keysDAO.updateKeyData(keysData);
            } catch (SQLException exception) {
                throw new RuntimeException(exception);
            }
        });
    }

    public void registerDatabase() {
        DatabaseConnection.getInstance();
        this.playersDAO = new PlayersDAO();
        this.keysDAO = new KeysDao();
        this.keysDAO.initializeDatabase();
        this.playersDAO.initializeDatabase();

        new PlayerSaveTask(playersDAO).runTaskTimerAsynchronously(plugin, 0L, 20L * 3);

        List<KeysData> keys = keysDAO.getAllKeysData();
        for (KeysData key : keys) {
            KeysCacheManager.getInstance().cacheKeyData(key.getKey(), key);
        }
    }

    private void registerTasks() {
        new VipTask().runTaskTimer(plugin, 20L, 20L);
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
