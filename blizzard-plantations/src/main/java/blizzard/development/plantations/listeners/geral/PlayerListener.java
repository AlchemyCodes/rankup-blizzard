package blizzard.development.plantations.listeners.geral;

import blizzard.development.plantations.database.cache.PlayerCacheManager;
import blizzard.development.plantations.database.dao.PlayerDAO;
import blizzard.development.plantations.database.storage.PlayerData;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import java.sql.SQLException;

public class PlayerListener implements Listener {

    private final PlayerDAO playerDAO;

    public PlayerListener(PlayerDAO playerDAO) {
        this.playerDAO = playerDAO;
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();

        PlayerData playerData = playerDAO.findPlayerData(player.getUniqueId().toString());

        if (playerData == null) {
            playerData = new PlayerData(player.getName(), player.getUniqueId().toString(), 0, false);

            try {
                playerDAO.createPlayerData(playerData);

            } catch (SQLException exception) {
                exception.printStackTrace();
            }
        }

        PlayerCacheManager.cachePlayerData(player.getUniqueId().toString(), playerData);
    }
}
