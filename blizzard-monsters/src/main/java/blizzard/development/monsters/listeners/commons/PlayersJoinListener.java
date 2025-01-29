package blizzard.development.monsters.listeners.commons;

import blizzard.development.monsters.database.cache.managers.PlayersCacheManager;
import blizzard.development.monsters.database.dao.PlayersDAO;
import blizzard.development.monsters.database.storage.PlayersData;
import blizzard.development.monsters.utils.PluginImpl;
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
    public void whenPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        PlayersData playersData = database.findPlayerData(player.getUniqueId().toString());
        if (playersData == null) {
            playersData = new PlayersData(
                    player.getUniqueId().toString(),
                    player.getName(),
                    0,
                    PluginImpl.getInstance().Config.getInt("monsters.initial-monsters-limit"),
                    new ArrayList<>()
                    );
            try {
                database.createPlayerData(playersData);
            } catch(Exception err) {
                err.printStackTrace();
            }
        }
        PlayersCacheManager.getInstance().cachePlayerData(player, playersData);
    }
}
