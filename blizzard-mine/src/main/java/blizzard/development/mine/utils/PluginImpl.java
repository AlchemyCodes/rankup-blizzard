package blizzard.development.mine.utils;

import blizzard.development.mine.builders.display.PickaxeBuilder;
import blizzard.development.mine.builders.display.ExtractorBuilder;
import blizzard.development.mine.builders.hologram.HologramBuilder;
import blizzard.development.mine.commands.CommandRegistry;
import blizzard.development.mine.database.DatabaseConnection;
import blizzard.development.mine.database.cache.PlayerCacheManager;
import blizzard.development.mine.database.cache.ToolCacheManager;
import blizzard.development.mine.database.dao.PlayerDAO;
import blizzard.development.mine.database.dao.ToolDAO;
import blizzard.development.mine.database.storage.PlayerData;
import blizzard.development.mine.database.storage.ToolData;
import blizzard.development.mine.expansions.PlaceholderExpansion;
import blizzard.development.mine.listeners.ListenerRegistry;
import blizzard.development.mine.mine.adapters.PodiumAdapter;
import blizzard.development.mine.tasks.PlayerSaveTask;
import blizzard.development.mine.tasks.ToolSaveTask;
import blizzard.development.mine.tasks.mine.ExtractorUpdateTask;
import blizzard.development.mine.tasks.mine.PodiumUpdateTask;
import blizzard.development.mine.utils.config.ConfigUtils;
import co.aikar.commands.Locales;
import co.aikar.commands.PaperCommandManager;
import net.citizensnpcs.api.CitizensAPI;
import net.citizensnpcs.api.npc.NPC;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import java.sql.SQLException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

public class PluginImpl {

    public final Plugin plugin;
    private static PluginImpl instance;

    private PlayerDAO playerDAO;
    private ToolDAO toolDAO;

    public ConfigUtils Config;
    public ConfigUtils Locations;
    public ConfigUtils Ranking;
    public ConfigUtils Database;

    public PluginImpl(Plugin plugin) {
        this.plugin = plugin;
        instance = this;
        playerDAO = new PlayerDAO();
        toolDAO = new ToolDAO();
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

        PaperCommandManager commandManager = new PaperCommandManager(plugin);
        commandManager.getLocales().setDefaultLocale(Locales.PORTUGUESE);

        registerDatabase();
        registerListeners();
        registerCommands();

        registerExpansions();
    }

    public void onDisable() {
        new PodiumUpdateTask().cancelTask();
        new ExtractorUpdateTask().cancelTask();

        Set<UUID> npcsToRemove = new HashSet<>(PodiumAdapter.getInstance().getPodiumNPCs());
        for (UUID uuid : npcsToRemove) {
            NPC npc = CitizensAPI.getNPCRegistry().getByUniqueId(uuid);
            if (npc != null) {
                npc.destroy();
            }
        }

        PickaxeBuilder.getInstance().removePickaxe();
        HologramBuilder.getInstance().removeAllHolograms();
        ExtractorBuilder.getInstance().removeExtractor();
        setupDisableData();
    }

    public void registerDatabase() {
        playerDAO = new PlayerDAO();
        playerDAO.initializeDatabase();
        toolDAO = new ToolDAO();
        toolDAO.initializeDatabase();

        setupEnableData();

        new PlayerSaveTask(playerDAO).runTaskTimerAsynchronously(plugin, 0, 20L * 3);
        new ToolSaveTask(toolDAO).runTaskTimerAsynchronously(plugin, 0, 20L * 3);
    }

    private void setupEnableData() {
        List<PlayerData> players;
        try {
            players = playerDAO.getAllPlayersData();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        for (PlayerData player : players) {
            PlayerCacheManager.getInstance().cachePlayerData(UUID.fromString(player.getUuid()), player);
        }

        List<ToolData> tools;
        try {
            tools = toolDAO.getAllToolsData();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        for (ToolData tool : tools) {
            ToolCacheManager.getInstance().cacheToolData(UUID.fromString(tool.getUuid()), tool);
        }
    }

    private void setupDisableData() {
        PlayerCacheManager.getInstance().playerCache.forEach((player, playerData) -> {
            try {
                playerData.setInMine(false);
                playerDAO.updatePlayerData(playerData);
            } catch (SQLException exception) {
                throw new RuntimeException("Error when update player date " + playerData.getNickname(), exception);
            }
        });

        ToolCacheManager.getInstance().toolCache.forEach((player, toolData) -> {
            try {
                toolDAO.updateToolData(toolData);
            } catch (SQLException exception) {
                throw new RuntimeException("Error when update player date " + toolData.getNickname(), exception);
            }
        });

        DatabaseConnection.getInstance().close();
    }

    private void registerListeners() {
        ListenerRegistry listenerRegistry = new ListenerRegistry(playerDAO, toolDAO);
        listenerRegistry.register();
        listenerRegistry.registerPacket();
    }

    private void registerCommands() {
        CommandRegistry commandRegistry = new CommandRegistry();
        commandRegistry.register();
    }

    public void registerExpansions() {
        if (Bukkit.getPluginManager().getPlugin("PlaceholderAPI") != null) {
            new PlaceholderExpansion().register();
        }
    }

    public static PluginImpl getInstance() {
        return instance;
    }
}