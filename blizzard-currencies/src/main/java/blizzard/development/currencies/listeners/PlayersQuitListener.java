package blizzard.development.currencies.listeners;

import blizzard.development.currencies.database.cache.PlayersCacheManager;
import blizzard.development.currencies.database.dao.PlayersDAO;
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
    public void whenPlayerQuit(PlayerQuitEvent event) throws SQLException {
        Player player = event.getPlayer();
        database.updatePlayerData(PlayersCacheManager.getInstance().getPlayerData(player));
    }
}
