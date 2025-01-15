package blizzard.development.monsters.utils;

import blizzard.development.monsters.builders.ItemBuilder;
import blizzard.development.monsters.builders.hologram.HologramBuilder;
import blizzard.development.monsters.commands.CommandRegistry;
import blizzard.development.monsters.database.DatabaseConnection;
import blizzard.development.monsters.database.cache.managers.MonstersCacheManager;
import blizzard.development.monsters.database.cache.managers.PlayersCacheManager;
import blizzard.development.monsters.database.cache.managers.ToolsCacheManager;
import blizzard.development.monsters.database.dao.MonstersDAO;
import blizzard.development.monsters.database.dao.PlayersDAO;
import blizzard.development.monsters.database.dao.ToolsDAO;
import blizzard.development.monsters.database.storage.MonstersData;
import blizzard.development.monsters.database.storage.PlayersData;
import blizzard.development.monsters.database.storage.ToolsData;
import blizzard.development.monsters.listeners.ListenerRegistry;
import blizzard.development.monsters.listeners.packets.monsters.MonstersDamageListener;
import blizzard.development.monsters.managers.DataBatchManager;
import blizzard.development.monsters.monsters.managers.world.MonstersWorldManager;
import blizzard.development.monsters.tasks.monsters.MonstersSaveTask;
import blizzard.development.monsters.tasks.players.PlayersSaveTask;
import blizzard.development.monsters.tasks.tools.ToolsSaveTask;
import blizzard.development.monsters.utils.config.ConfigUtils;
import co.aikar.commands.Locales;
import co.aikar.commands.PaperCommandManager;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;
import lombok.Getter;
import lombok.SneakyThrows;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.List;
import java.util.UUID;

public class PluginImpl {
    public final Plugin plugin;

    private static @Getter PluginImpl instance;
    public PlayersDAO playersDAO;
    public MonstersDAO monstersDAO;
    public ToolsDAO toolsDAO;

    public ProtocolManager protocolManager;

    public ConfigUtils Config;
    public ConfigUtils Database;
    public ConfigUtils Monsters;
    public ConfigUtils Rewards;
    public ConfigUtils Locations;

    public PluginImpl(Plugin plugin) {
        this.plugin = plugin;
        instance = this;
        protocolManager = ProtocolLibrary.getProtocolManager();
        Config = new ConfigUtils((JavaPlugin) plugin, "config.yml");
        Database = new ConfigUtils((JavaPlugin) plugin, "database.yml");
        Monsters = new ConfigUtils((JavaPlugin) plugin, "monsters.yml");
        Rewards = new ConfigUtils((JavaPlugin) plugin, "rewards.yml");
        Locations = new ConfigUtils((JavaPlugin) plugin, "locations.yml");
    }

    public void onEnable() {
        Config.saveDefaultConfig();
        Database.saveDefaultConfig();
        Monsters.saveDefaultConfig();
        Rewards.saveDefaultConfig();
        Locations.saveDefaultConfig();

        PaperCommandManager commandManager = new PaperCommandManager(plugin);
        commandManager.getLocales().setDefaultLocale(Locales.PORTUGUESE);

        registerDatabase();
        registerListeners();
        registerCommands();

        DataBatchManager.initialize(plugin);
    }

    public void onDisable() {
        disableMonsters();
        DatabaseConnection.getInstance().close();
    }

    public void registerDatabase() {
        DatabaseConnection.getInstance();

        playersDAO = new PlayersDAO();
        playersDAO.initializeDatabase();
        new PlayersSaveTask(playersDAO).runTaskTimerAsynchronously(plugin, 0, 20L * 3);

        monstersDAO = new MonstersDAO();
        monstersDAO.initializeDatabase();
        new MonstersSaveTask(monstersDAO).runTaskTimerAsynchronously(plugin, 0, 20L * 3);

        toolsDAO = new ToolsDAO();
        toolsDAO.initializeDatabase();
        new ToolsSaveTask(toolsDAO).runTaskTimerAsynchronously(plugin, 0, 20L * 3);

        setupData();
    }

    @SneakyThrows
    private void setupData() {
        List<PlayersData> players = playersDAO.getAllPlayersData();
        for (PlayersData player : players) {
            PlayersCacheManager.getInstance().cachePlayerData(UUID.fromString(player.getUuid()), player);
        }

        List<MonstersData> monsters = monstersDAO.getAllMonstersData();
        for (MonstersData monster : monsters) {
            MonstersCacheManager.getInstance().cacheMonsterData(monster.getUuid(), monster);
        }

        List<ToolsData> tools = toolsDAO.getAllToolsData();
        for (ToolsData tool : tools) {
            ToolsCacheManager.getInstance().cacheToolData(tool.getId(), tool);
        }
    }

    private void disableMonsters() {
        for (Player player : Bukkit.getOnlinePlayers()) {
            if (MonstersWorldManager.getInstance().containsPlayer(player)) {

                Location exit = LocationUtils.getInstance().getLocation(blizzard.development.monsters.monsters.enums.Locations.EXIT.getName());
                if (exit != null) {
                    player.teleportAsync(
                            LocationUtils.getInstance().getLocation(blizzard.development.monsters.monsters.enums.Locations.EXIT.getName())
                    );
                }
            }

            for (MonstersData monstersData : MonstersCacheManager.getInstance().monstersCache.values()) {
                if (monstersData.getOwner().equals(player.getName())) {
                    HologramBuilder.getInstance().removeHologram(UUID.fromString(monstersData.getUuid()));
                }
            }

            for (ItemStack item : player.getInventory().getContents()) {
                if (item == null) continue;

                if (ItemBuilder.hasPersistentData(PluginImpl.getInstance().plugin, item, "blizzard.monsters.compass")) {
                    player.getInventory().remove(item);
                }

            }
        }

        protocolManager.removePacketListeners(plugin);
    }

    private void registerListeners() {
        ListenerRegistry.getInstance().register();
        MonstersDamageListener.getInstance(plugin);
    }

    private void registerCommands() {
        CommandRegistry.getInstance().register();
    }
}