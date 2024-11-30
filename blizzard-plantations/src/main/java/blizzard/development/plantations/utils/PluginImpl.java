package blizzard.development.plantations.utils;

import blizzard.development.plantations.Main;
import blizzard.development.plantations.commands.CommandRegistry;
import blizzard.development.plantations.database.cache.PlayerCacheManager;
import blizzard.development.plantations.database.cache.ToolCacheManager;
import blizzard.development.plantations.database.dao.PlayerDAO;
import blizzard.development.plantations.database.dao.ToolDAO;
import blizzard.development.plantations.database.storage.PlayerData;
import blizzard.development.plantations.database.storage.ToolData;
import blizzard.development.plantations.listeners.ListenerRegistry;
import blizzard.development.plantations.managers.BatchManager;
import blizzard.development.plantations.tasks.PlayerSaveTask;
import blizzard.development.plantations.tasks.ToolSaveTask;
import blizzard.development.plantations.utils.config.ConfigUtils;
import co.aikar.commands.Locales;
import co.aikar.commands.PaperCommandManager;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import java.sql.SQLException;
import java.util.List;

public class PluginImpl {
    public final Plugin plugin;
    private static PluginImpl instance;
    private static PaperCommandManager commandManager;
    private PlayerDAO playerDAO;
    private ToolDAO toolDAO;
    public ConfigUtils Config;
    public ConfigUtils Locations;
    public ConfigUtils Ranking;
    public ConfigUtils Database;
    private final PlayerCacheManager playerCacheManager = PlayerCacheManager.getInstance();

    public PluginImpl(Plugin plugin) {
        this.plugin = plugin;
        instance = this;
        playerDAO = new PlayerDAO();
        toolDAO = new ToolDAO();
        commandManager = new PaperCommandManager(plugin);
        commandManager.getLocales().setDefaultLocale(Locales.PORTUGUESE);
        Config = new ConfigUtils((JavaPlugin) plugin, "config.yml");
        Locations = new ConfigUtils((JavaPlugin) plugin, "locations.yml");
        Ranking = new ConfigUtils((JavaPlugin) plugin, "ranking.yml");
        Database = new ConfigUtils((JavaPlugin) plugin, "database.yml");
    }

    public void onEnable() {
        Config.saveDefaultConfig();
        Locations.saveDefaultConfig();
        Ranking.saveDefaultConfig();
        Database.saveDefaultConfig();
        registerDatabase();
        registerListeners();
        registerCommands();
        BatchManager.initialize(Main.getInstance());

        try {
            List<PlayerData> allPlayers = playerDAO.getAllPlayersData();
            List<ToolData> allTools = toolDAO.getAllToolData();

            for (PlayerData player : allPlayers) {
                playerCacheManager.cachePlayerData(player.getNickname(), player);
            }

            for (ToolData tool : allTools) {
                ToolCacheManager.cacheToolData(tool.getId(), tool);
            }

        } catch (SQLException exception) {
            throw new RuntimeException(exception);
        }

        registerTasks();
    }

    public void onDisable() {

        playerCacheManager.playerCache.forEach((player, playerData) -> {
            try {
                playerDAO.updatePlayerData(playerData);
            } catch (SQLException exception) {
                throw new RuntimeException("Erro ao atualizar dados do jogador " + playerData.getNickname(), exception);
            }
        });
    }


    public void registerDatabase() {
        playerDAO = new PlayerDAO();
        playerDAO.initializeDatabase();

        toolDAO = new ToolDAO();
        toolDAO.initializeDatabase();
    }

    private void registerListeners() {
        ListenerRegistry listenerRegistry = new ListenerRegistry(playerDAO);
        listenerRegistry.register();
        listenerRegistry.registerPacket();
    }

    private void registerTasks() {
        PlayerSaveTask playerSaveTask = new PlayerSaveTask(playerDAO);
        playerSaveTask.runTaskTimerAsynchronously(Main.getInstance(), 0L, 20 * 5);

        ToolSaveTask toolSaveTask = new ToolSaveTask(toolDAO);
        toolSaveTask.runTaskTimerAsynchronously(Main.getInstance(), 0L, 20 * 5);
    }

    private void registerCommands() {
        CommandRegistry commandRegistry = new CommandRegistry();
        commandRegistry.register();
    }

    public static PluginImpl getInstance() {
        return instance;
    }
}
