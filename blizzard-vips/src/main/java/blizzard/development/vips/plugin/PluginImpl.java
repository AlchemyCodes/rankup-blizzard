package blizzard.development.vips.plugin;

import blizzard.development.vips.commands.CommandRegistry;
import blizzard.development.vips.database.DatabaseConnection;
import blizzard.development.vips.database.cache.KeyCacheManager;
import blizzard.development.vips.database.cache.VipCacheManager;
import blizzard.development.vips.database.dao.KeyDAO;
import blizzard.development.vips.database.dao.VipDAO;
import blizzard.development.vips.database.storage.KeyData;
import blizzard.development.vips.database.storage.VipData;
import blizzard.development.vips.tasks.VipSaveTask;
import blizzard.development.vips.tasks.VipTimeTask;
import blizzard.development.vips.utils.ConfigUtils;
import co.aikar.commands.Locales;
import co.aikar.commands.PaperCommandManager;
import lombok.Getter;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.sql.SQLException;
import java.util.List;
import java.util.UUID;

public class PluginImpl {
    public VipDAO vipDAO;
    public KeyDAO keysDAO;
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
        registerTasks();

        List<VipData> vipData;
        try {
            vipData = vipDAO.getAllVipData();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        for (VipData data : vipData) {
            VipCacheManager.getInstance().cacheVipData(data.getVipId(), data);
        }
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
        KeyCacheManager.getInstance().keysCache.forEach((key, keysData) -> {
            try {
                this.keysDAO.updateKeyData(keysData);
            } catch (SQLException exception) {
                throw new RuntimeException(exception);
            }
        });
    }

    public void registerDatabase() {
        DatabaseConnection.getInstance();
        this.vipDAO = new VipDAO();
        this.keysDAO = new KeyDAO();
        this.keysDAO.initializeDatabase();
        this.vipDAO.initializeDatabase();

        new VipSaveTask(vipDAO).runTaskTimerAsynchronously(plugin, 0L, 20L * 3);

        List<KeyData> keys = keysDAO.getAllKeysData();
        for (KeyData key : keys) {
            KeyCacheManager.getInstance().cacheKeyData(key.getKey(), key);
        }
    }

    private void registerTasks() {
        new VipTimeTask().runTaskTimer(plugin, 20L, 20L);
    }

    private void registerCommands() {
        CommandRegistry commandRegistry = new CommandRegistry();
        commandRegistry.register();
    }

    public File getDataFolder() {
        return this.plugin.getDataFolder();
    }
}