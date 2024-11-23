package blizzard.development.time.listeners.commons;

import blizzard.development.time.database.cache.managers.PlayersCacheManager;
import blizzard.development.time.database.dao.PlayersDAO;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

import java.sql.SQLException;

public class PlayersQuitListener implements Listener {

    private final PlayersDAO database;
    public PlayersQuitListener(PlayersDAO database) {
        this.database = database;
    }

    @EventHandler
    private void whenPlayerQuit(PlayerQuitEvent event) throws SQLException {
        Player player = event.getPlayer();
        database.updatePlayerData(PlayersCacheManager.getInstance().getPlayerData(player));
    }
}
