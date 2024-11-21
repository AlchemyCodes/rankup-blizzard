package blizzard.development.spawners.listeners.commons;

import blizzard.development.spawners.database.cache.managers.PlayersCacheManager;
import blizzard.development.spawners.database.dao.PlayersDAO;
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
