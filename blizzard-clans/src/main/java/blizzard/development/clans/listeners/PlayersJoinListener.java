package blizzard.development.clans.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import blizzard.development.clans.database.cache.PlayersCacheManager;
import blizzard.development.clans.database.dao.PlayersDAO;
import blizzard.development.clans.database.storage.PlayersData;

public class PlayersJoinListener implements Listener {
    private final PlayersDAO database;
    public PlayersJoinListener(PlayersDAO database) {
        this.database = database;
    }

    @EventHandler
    public void whenPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        PlayersData playersData = database.findPlayerData(player.getUniqueId().toString());
        if(playersData == null) {
            playersData = new PlayersData(player.getUniqueId().toString(), player.getName(), null, null, null, 0, 0);
            try {
                database.createPlayerData(playersData);
            } catch(Exception err) {
                err.printStackTrace();
            }
        }
        PlayersCacheManager.cachePlayerData(player, playersData);
    }
}
