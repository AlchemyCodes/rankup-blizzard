package blizzard.development.clans.utils;

import blizzard.development.clans.commands.subcommands.*;
import blizzard.development.clans.listeners.clans.*;
import blizzard.development.clans.managers.ChatManager;
import blizzard.development.clans.managers.EconomyManager;
import co.aikar.commands.Locales;
import co.aikar.commands.PaperCommandManager;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import blizzard.development.clans.Main;
import blizzard.development.clans.commands.chat.ClansChatCommand;
import blizzard.development.clans.commands.ClansCommand;
import blizzard.development.clans.commands.subcommands.staff.GiveLimitsCommand;
import blizzard.development.clans.commands.subcommands.staff.ChatSpyCommand;
import blizzard.development.clans.commands.subcommands.staff.ForceDisbandCommand;
import blizzard.development.clans.commands.subcommands.staff.ForceJoinCommand;
import blizzard.development.clans.commands.subcommands.staff.ReloadCommand;
import blizzard.development.clans.commands.subcommands.viewers.InvitesViewCommand;
import blizzard.development.clans.commands.subcommands.viewers.RankingViewCommand;
import blizzard.development.clans.commands.subcommands.viewers.RolesViewCommand;
import blizzard.development.clans.database.DatabaseConnection;
import blizzard.development.clans.database.cache.ClansCacheManager;
import blizzard.development.clans.database.dao.ClansDAO;
import blizzard.development.clans.database.dao.PlayersDAO;
import blizzard.development.clans.database.storage.ClansData;
import blizzard.development.clans.listeners.PlayerQuitListener;
import blizzard.development.clans.listeners.PlayersJoinListener;
import blizzard.development.clans.managers.LPermsRegistry;
import blizzard.development.clans.managers.PlaceholderRegistry;
import blizzard.development.clans.tasks.ClansSaveTask;
import blizzard.development.clans.tasks.KillDeathRatioTask;
import blizzard.development.clans.tasks.PlayerSaveTask;
import blizzard.development.clans.utils.config.ConfigUtils;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class PluginImpl {
    public final Plugin plugin;
    public PlayersDAO playersDAO;
    public ClansDAO clansDAO;
    private static PluginImpl instance;
    private static PluginManager pluginManager;
    private static PaperCommandManager commandManager;
    private PlaceholderRegistry placeHolderManager;

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

        if (Bukkit.getPluginManager().getPlugin("PlaceholderAPI") != null) {
            new PlaceholderRegistry((Main) plugin).register();
        }

        Config.saveDefaultConfig();
        Database.saveDefaultConfig();
        registerDatabase();
        registerListeners();
        registerTasks();
        registerCommands();
        LPermsRegistry.register();
        EconomyManager.setupEconomy();

        List<ClansData> allClans = clansDAO.getAllClans();
        for (ClansData clan : allClans) {
            ClansCacheManager.cacheClansData(clan.getClan(), clan);
        }
    }

    public void onUnload() {
        ClansCacheManager.clansCache.forEach((clan, clansData) -> {
            try {
                clansDAO.updateClansData(clansData);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });
        DatabaseConnection.getInstance().close();
    }

    public void registerDatabase() {
        DatabaseConnection.getInstance();
        playersDAO = new PlayersDAO();
        playersDAO.initializeDatabase();

        clansDAO = new ClansDAO();
        clansDAO.initializeDatabase();

        new PlayerSaveTask(playersDAO).runTaskTimerAsynchronously(plugin, 0, 20L * 3);
        new ClansSaveTask(clansDAO).runTaskTimerAsynchronously(plugin, 0, 20L * 3);
    }

    private void registerListeners() {
        pluginManager.registerEvents(new PlayersJoinListener(playersDAO), plugin);
        pluginManager.registerEvents(new PlayerQuitListener(playersDAO), plugin);
        pluginManager.registerEvents(new ClansCreationListener(), plugin);
        pluginManager.registerEvents(new ClansBankListener(), plugin);
        pluginManager.registerEvents(new ClansChangesListener(), plugin);
        pluginManager.registerEvents(new FriendlyFireListener(), plugin);
        pluginManager.registerEvents(new ClansInviteListener(), plugin);
        pluginManager.registerEvents(new KillDeathRatioListener(), plugin);
        pluginManager.registerEvents(new LimitListener(), plugin);
        pluginManager.registerEvents(new ChatManager(), plugin);
    }

    private void registerTasks() {
        new KillDeathRatioTask().runTaskTimerAsynchronously(plugin, 0, 20L * 5);
    }

    private void registerCommands() {

        // Members

        commandManager.registerCommand(new ClansCommand());
        commandManager.registerCommand(new CreateCommand());
        commandManager.registerCommand(new InviteCommand());
        commandManager.registerCommand(new LeaveCommand());
        commandManager.registerCommand(new KickCommand());
        commandManager.registerCommand(new DisbandCommand());
        commandManager.registerCommand(new RolesCommand());
        commandManager.registerCommand(new InfoCommand());
        commandManager.registerCommand(new InvitesViewCommand());
        commandManager.registerCommand(new RankingViewCommand());
        commandManager.registerCommand(new RolesViewCommand());
        commandManager.registerCommand(new HelpCommand());
        commandManager.registerCommand(new ClansChatCommand());

        // Staffs

        commandManager.registerCommand(new ForceJoinCommand());
        commandManager.registerCommand(new ForceDisbandCommand());
        commandManager.registerCommand(new ChatSpyCommand());
        commandManager.registerCommand(new ReloadCommand());
        commandManager.registerCommand(new GiveLimitsCommand());

        // Completion

        commandManager.getCommandCompletions().registerCompletion("clans", c -> {
            List<ClansData> clans = ClansCacheManager.getAllClans();
            return clans.stream().map(ClansData::getTag).collect(Collectors.toList());
        });

        commandManager.getCommandCompletions().registerCompletion("types", c -> {
            ArrayList<String> types = new ArrayList<>();
            types.add("limite");
            return types;
        });

        commandManager.getCommandCompletions().registerCompletion("limits", c -> {
            ArrayList<String> limits = new ArrayList<>();
            limits.add("1");
            limits.add("5");
            return limits;
        });
    }

    public static PluginImpl getInstance() {
        return instance;
    }
}
