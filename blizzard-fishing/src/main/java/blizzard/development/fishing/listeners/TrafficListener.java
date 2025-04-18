package blizzard.development.fishing.listeners;

import blizzard.development.fishing.database.cache.FishingCacheManager;
import blizzard.development.fishing.database.cache.PlayersCacheManager;
import blizzard.development.fishing.database.cache.RodsCacheManager;
import blizzard.development.fishing.database.cache.methods.PlayersCacheMethod;
import blizzard.development.fishing.database.cache.methods.RodsCacheMethod;
import blizzard.development.fishing.database.dao.PlayersDAO;
import blizzard.development.fishing.database.dao.RodsDAO;
import blizzard.development.fishing.database.storage.PlayersData;
import blizzard.development.fishing.database.storage.RodsData;
import blizzard.development.fishing.enums.RodMaterials;
import blizzard.development.fishing.handlers.FishBucketHandler;
import blizzard.development.fishing.handlers.FishingNetHandler;
import blizzard.development.fishing.handlers.FishingRodHandler;
import blizzard.development.fishing.handlers.FurnaceItemHandler;
import blizzard.development.fishing.listeners.items.FishBucketListener;
import blizzard.development.fishing.tasks.items.FishingNetTask;
import blizzard.development.fishing.tasks.items.FurnaceTask;
import blizzard.development.fishing.utils.PluginImpl;
import blizzard.development.fishing.utils.fish.FishesUtils;
import org.bukkit.World;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerChangedWorldEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.ItemStack;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

public class TrafficListener implements Listener {

    private final PlayersDAO playersDAO;
    private final RodsDAO rodsDAO;

    public TrafficListener(PlayersDAO playersDAO, RodsDAO rodsDAO) {
        this.playersDAO = playersDAO;
        this.rodsDAO = rodsDAO;
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        FishingCacheManager.removeFisherman(event.getPlayer().getUniqueId());
        FurnaceTask.removePlayer(event.getPlayer());
        FishingNetTask.setCatchingTrash(event.getPlayer(), false);
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();

        PlayersData playersData = playersDAO.findPlayerData(player.getUniqueId().toString());
        RodsData rodsData = rodsDAO.findRodsData(player.getUniqueId().toString());

        if (playersData == null) {
            playersData = new PlayersData(player.getUniqueId().toString(), player.getName(),0,0,0,0,
                    0,0,0,0,0, 50, 0);
            try {
                playersDAO.createPlayerData(playersData);
            } catch(Exception err) {
                err.printStackTrace();
            }
        }

        PlayersCacheManager.getInstance().cachePlayerData(player, playersData);

        if (rodsData == null) {
            rodsData = new RodsData(player.getUniqueId().toString(), player.getName(),1,0,0,0, 0
                    , List.of(RodMaterials.BAMBOO));
            try {
                rodsDAO.createRodsData(rodsData);
            } catch(Exception err) {
                err.printStackTrace();
            }
        }

        RodsCacheManager.getInstance().cachePlayerData(player, rodsData);
        FishesUtils.getInstance().setActiveSkin(player, RodsCacheMethod.getInstance().getBestMaterial(player));
    }

    @EventHandler
    public void onChangeWorld(PlayerChangedWorldEvent event) {
        World from = event.getFrom();
        if (from.getName().equals("pesca")) {
            Player player = event.getPlayer();
            player.getInventory().clear();
            FishingCacheManager.removeFisherman(player.getUniqueId());
            FishingNetTask.removeCatchingTrash(player);
            FurnaceTask.removePlayer(player);
        }
    }
}
