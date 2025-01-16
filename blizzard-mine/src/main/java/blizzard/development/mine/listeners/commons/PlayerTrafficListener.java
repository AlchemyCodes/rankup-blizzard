package blizzard.development.mine.listeners.commons;

import blizzard.development.mine.database.cache.PlayerCacheManager;
import blizzard.development.mine.database.dao.PlayerDAO;
import blizzard.development.mine.database.storage.PlayerData;
import blizzard.development.mine.mine.enums.BlockEnum;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import java.sql.SQLException;
import java.util.ArrayList;

public class PlayerTrafficListener implements Listener {

    private final PlayerDAO playerDAO;

    public PlayerTrafficListener(PlayerDAO playerDAO) {
        this.playerDAO = playerDAO;
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();

        PlayerData playerData = playerDAO.findPlayerData(player.getUniqueId().toString());

        if (playerData == null) {
            playerData = new PlayerData(player.getUniqueId().toString(), player.getName(), 15, BlockEnum.STONE.name(), 0,false, new ArrayList<>());

            try {
                playerDAO.createPlayerData(playerData);
            } catch (SQLException exception) {
                exception.printStackTrace();
            }
        }

        PlayerCacheManager.getInstance().cachePlayerData(
                player.getUniqueId().toString(),
                playerData
        );
    }
}
