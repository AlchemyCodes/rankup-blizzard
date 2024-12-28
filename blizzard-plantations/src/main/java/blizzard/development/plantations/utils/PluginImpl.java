package blizzard.development.plantations.utils;

import blizzard.development.plantations.Main;
import blizzard.development.plantations.commands.CommandRegistry;
import blizzard.development.plantations.database.DatabaseConnection;
import blizzard.development.plantations.database.cache.PlayerCacheManager;
import blizzard.development.plantations.database.cache.ToolCacheManager;
import blizzard.development.plantations.database.dao.PlayerDAO;
import blizzard.development.plantations.database.dao.ToolDAO;
import blizzard.development.plantations.database.storage.PlayerData;
import blizzard.development.plantations.database.storage.ToolData;
import blizzard.development.plantations.listeners.ListenerRegistry;
import blizzard.development.plantations.managers.BatchManager;
import blizzard.development.plantations.tasks.PlantationRegenTask;
import blizzard.development.plantations.tasks.PlayerSaveTask;
import blizzard.development.plantations.tasks.ToolSaveTask;
import blizzard.development.plantations.utils.config.ConfigUtils;
import blizzard.development.plantations.utils.displayentity.DisplayEntityUtils;
import blizzard.development.plantations.utils.placeholder.PlaceholderRegistry;
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
    public ConfigUtils Shop;
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
        Shop = new ConfigUtils((JavaPlugin) plugin, "shop.yml");
        Database = new ConfigUtils((JavaPlugin) plugin, "database.yml");
    }

    public void onEnable() {
        try {
            commandManager = new PaperCommandManager(plugin);
            commandManager.getLocales().setDefaultLocale(Locales.PORTUGUESE);
        } catch (IllegalStateException e) {
            throw new RuntimeException("Erro ao inicializar PaperCommandManager", e);
        }


        Config.saveDefaultConfig();
        Locations.saveDefaultConfig();
        Ranking.saveDefaultConfig();
        Shop.saveDefaultConfig();
        Database.saveDefaultConfig();
        registerDatabase();
        registerListeners();
        registerCommands();
        BatchManager.initialize(Main.getInstance());

        new PlaceholderRegistry().register();

        try {
            List<PlayerData> allPlayers = playerDAO.getAllPlayersData();
            List<ToolData> allTools = toolDAO.getAllToolData();

            for (PlayerData player : allPlayers) {
                playerCacheManager.cachePlayerData(player.getUuid(), player);
            }

            for (ToolData tool : allTools) {
                ToolCacheManager.cacheToolData(tool.getId(), tool);
            }

        } catch (SQLException exception) {
            throw new RuntimeException(exception);
        }

        registerTasks();
        DisplayEntityUtils.initialize();
        PlantationRegenTask.start();
    }

    public void onDisable() {

        playerCacheManager.playerCache.forEach((player, playerData) -> {
            try {
                playerDAO.updatePlayerData(playerData);
            } catch (SQLException exception) {
                throw new RuntimeException("Erro ao atualizar dados do jogador " + playerData.getNickname(), exception);
            }
        });

        DisplayEntityUtils.removeDisplay();
        DatabaseConnection.getInstance().close();

    }


    public void registerDatabase() {
        playerDAO = new PlayerDAO();
        playerDAO.initializeDatabase();

        toolDAO = new ToolDAO();
        toolDAO.initializeDatabase();

//        plantationDAO = new PlantationDAO();
//        plantationDAO.initializeDatabase();
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
