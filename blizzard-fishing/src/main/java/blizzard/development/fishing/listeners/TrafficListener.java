package blizzard.development.fishing.listeners;

import blizzard.development.fishing.database.cache.FishingCacheManager;
import blizzard.development.fishing.database.cache.PlayersCacheManager;
import blizzard.development.fishing.database.dao.PlayersDAO;
import blizzard.development.fishing.database.dao.RodsDAO;
import blizzard.development.fishing.database.storage.PlayersData;
import blizzard.development.fishing.database.storage.RodsData;
import blizzard.development.fishing.enums.RodMaterials;
import blizzard.development.fishing.handlers.FishBucketHandler;
import blizzard.development.fishing.handlers.FishingNetHandler;
import blizzard.development.fishing.handlers.FishingRodHandler;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.sql.SQLException;
import java.util.Arrays;

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
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();

        FishingRodHandler.setRod(player, 0);
        FishingNetHandler.setNet(player, 4);
        FishBucketHandler.setBucket(player, 8);

        PlayersData playerData = playersDAO.findPlayerData(player.getUniqueId().toString());
        RodsData rodsData = rodsDAO.findRodsData(player.getUniqueId().toString());

        if (playerData == null) {
            playerData = new PlayersData
                    (player.getUniqueId().toString(), player.getName(),
                            0, 0, 0, 0, 0,
                            0, 0, 0, 0, 0);
            try {
                playersDAO.createPlayerData(playerData);
            } catch (SQLException exception) {
                exception.printStackTrace();
            }

            if (rodsData == null) {
                rodsData = new RodsData
                        (player.getUniqueId().toString(), player.getName(),
                                0, 0, 0, 0, Arrays.asList(RodMaterials.BAMBOO));
                try {
                    rodsDAO.createRodsData(rodsData);
                } catch (SQLException exception) {
                    exception.printStackTrace();
                }
            }

            PlayersCacheManager.cachePlayerData(player, playerData);
        }
    }
}
