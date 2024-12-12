package blizzard.development.spawners.listeners;

import blizzard.development.spawners.database.dao.PlayersDAO;
import blizzard.development.spawners.listeners.chat.AsyncChatListener;
import blizzard.development.spawners.listeners.chat.spawners.SpawnerFriendsListener;
import blizzard.development.spawners.listeners.commons.PlayersJoinListener;
import blizzard.development.spawners.listeners.commons.PlayersQuitListener;
import blizzard.development.spawners.listeners.drops.DropsAutoSellInteractListener;
import blizzard.development.spawners.listeners.limits.CommonLimitInteractListener;
import blizzard.development.spawners.listeners.limits.FriendsLimitInteractListener;
import blizzard.development.spawners.listeners.spawners.mobs.MobCommonListener;
import blizzard.development.spawners.listeners.spawners.mobs.MobDamageListener;
import blizzard.development.spawners.listeners.spawners.mobs.MobDeathListener;
import blizzard.development.spawners.listeners.spawners.spawners.SpawnerBreakListener;
import blizzard.development.spawners.listeners.spawners.spawners.SpawnerInteractListener;
import blizzard.development.spawners.listeners.spawners.spawners.SpawnerPlaceListener;
import blizzard.development.spawners.listeners.spawners.spawners.SpawnersJoinListener;
import blizzard.development.spawners.utils.PluginImpl;
import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;

import java.util.Arrays;

public class ListenerRegistry {

    private static ListenerRegistry instance;

    public void register() {
        PluginManager pluginManager = Bukkit.getPluginManager();
        PlayersDAO playersDAO = new PlayersDAO();

        Arrays.asList(
                // spawners
                new PlayersJoinListener(playersDAO),
                new PlayersQuitListener(playersDAO),
                new SpawnerPlaceListener(),
                new SpawnerBreakListener(),
                new SpawnerInteractListener(),
                new SpawnersJoinListener(),
                // mobs
                new MobDeathListener(),
                new MobDamageListener(),
                new MobCommonListener(),
                // chat
                new AsyncChatListener(),
                new SpawnerFriendsListener(),
                // limits
                new CommonLimitInteractListener(),
                new FriendsLimitInteractListener(),
                // drops
                new DropsAutoSellInteractListener()
        ).forEach(listener -> pluginManager.registerEvents(listener, PluginImpl.getInstance().plugin));
    }

    public static ListenerRegistry getInstance() {
        if (instance == null) instance = new ListenerRegistry();
        return instance;
    }
}
