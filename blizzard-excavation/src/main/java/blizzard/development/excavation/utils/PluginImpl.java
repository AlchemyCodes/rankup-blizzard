package blizzard.development.excavation.utils;

import blizzard.development.excavation.Main;
import blizzard.development.excavation.commands.CommandRegistry;
import blizzard.development.excavation.commands.geral.AreaCommand;
import blizzard.development.excavation.commands.geral.ExcavateCommand;
import blizzard.development.excavation.database.cache.ExcavatorCacheManager;
import blizzard.development.excavation.database.dao.ExcavatorDAO;
import blizzard.development.excavation.database.dao.PlayerDAO;
import blizzard.development.excavation.database.storage.ExcavatorData;
import blizzard.development.excavation.listeners.ListenerRegistry;
import blizzard.development.excavation.tasks.ExcavatorSaveTask;
import blizzard.development.excavation.tasks.PlayerSaveTask;
import blizzard.development.excavation.utils.config.ConfigUtils;
import co.aikar.commands.Locales;
import co.aikar.commands.PaperCommandManager;
import com.github.retrooper.packetevents.PacketEvents;
import com.github.retrooper.packetevents.event.PacketListenerPriority;
import io.github.retrooper.packetevents.factory.spigot.SpigotPacketEventsBuilder;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import java.sql.SQLException;
import java.util.List;

public class PluginImpl {
    public final Plugin plugin;
    private static PluginImpl instance;
    private static PaperCommandManager commandManager;
    private static ExcavatorDAO excavatorDAO;
    private static PlayerDAO playerDAO;
    public ConfigUtils Config;
    public ConfigUtils Locations;
    public ConfigUtils Database;

    public PluginImpl(Plugin plugin) {
        this.plugin = plugin;
        instance = this;
        commandManager = new PaperCommandManager(plugin);
        commandManager.getLocales().setDefaultLocale(Locales.PORTUGUESE);
        excavatorDAO = new ExcavatorDAO();
        playerDAO = new PlayerDAO();
        Config = new ConfigUtils((JavaPlugin) plugin, "config.yml");
        Locations = new ConfigUtils((JavaPlugin) plugin, "locations.yml");
        Database = new ConfigUtils((JavaPlugin) plugin, "database.yml");
    }

    public void onLoad() {
        PacketEvents.setAPI(SpigotPacketEventsBuilder.build(Main.getInstance()));
        PacketEvents.getAPI().load();
    }
    public void onEnable() {
        Config.saveDefaultConfig();
        Locations.saveDefaultConfig();
        Database.saveDefaultConfig();
        registerDatabase();


        registerListeners();
        registerCommands();
        registerTasks();

        PacketEvents.setAPI(SpigotPacketEventsBuilder.build(Main.getInstance()));
        PacketEvents.getAPI().load();

        PacketEvents.getAPI().getEventManager().registerListener(
                new AreaCommand(), PacketListenerPriority.HIGHEST
        );

        PacketEvents.getAPI().init();


        try {
            List<ExcavatorData> allExcavator = excavatorDAO.getAllExcavatorData();
            for (ExcavatorData excavator : allExcavator) {
                ExcavatorCacheManager.cacheExcavatorData(excavator.getNickname(), excavator);
            }
        } catch (SQLException exception) {
            throw new RuntimeException(exception);
        }

        PlayerSaveTask playerSaveTask = new PlayerSaveTask(playerDAO);
        playerSaveTask.runTaskTimerAsynchronously(Main.getInstance(), 0L, 20 * 5);

        ExcavatorSaveTask excavatorSaveTask = new ExcavatorSaveTask(excavatorDAO);
        excavatorSaveTask.runTaskTimerAsynchronously(Main.getInstance(), 0, 20 * 5);

    }

    public void onDisable() {
        PacketEvents.getAPI().terminate();
    }

    public void registerDatabase() {
        playerDAO = new PlayerDAO();
        playerDAO.initializeDatabase();

        excavatorDAO = new ExcavatorDAO();
        excavatorDAO.initializeDatabase();
    }

    private void registerListeners() {
        ListenerRegistry listenerRegistry = new ListenerRegistry(excavatorDAO, playerDAO);
        listenerRegistry.register();
    }

    private void registerTasks() {

    }

    private void registerCommands() {
        CommandRegistry commandRegistry = new CommandRegistry();
        commandRegistry.register();
    }

    public static PluginImpl getInstance() {
        return instance;
    }
}
