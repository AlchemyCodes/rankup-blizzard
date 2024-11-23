package blizzard.development.time.listeners.commons;

import blizzard.development.time.database.cache.managers.PlayersCacheManager;
import blizzard.development.time.database.dao.PlayersDAO;
import blizzard.development.time.database.storage.PlayersData;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import java.util.ArrayList;

public class PlayersJoinListener implements Listener {
    private final PlayersDAO database;
    public PlayersJoinListener(PlayersDAO database) {
        this.database = database;
    }

    @EventHandler
    private void whenPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        PlayersData playersData = database.findPlayerData(player.getUniqueId().toString());
        if (playersData == null) {
            playersData = new PlayersData(player.getUniqueId().toString(), player.getName(), 0, new ArrayList<>(), new ArrayList<>());
            try {
                database.createPlayerData(playersData);
            } catch(Exception err) {
                err.printStackTrace();
            }
        }
        PlayersCacheManager.getInstance().cachePlayerData(player, playersData);
    }
}
