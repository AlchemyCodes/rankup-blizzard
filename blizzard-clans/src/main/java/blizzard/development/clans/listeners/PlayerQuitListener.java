package blizzard.development.clans.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;
import blizzard.development.clans.database.cache.PlayersCacheManager;
import blizzard.development.clans.database.dao.PlayersDAO;

import java.sql.SQLException;

public class PlayerQuitListener implements Listener {

    private final PlayersDAO database;
    public PlayerQuitListener(PlayersDAO database) {
        this.database = database;
    }

    @EventHandler
    public void whenPlayerQuit(PlayerQuitEvent event) throws SQLException {
        Player player = event.getPlayer();
        database.updatePlayerData(PlayersCacheManager.getPlayerData(player));
    }
}
