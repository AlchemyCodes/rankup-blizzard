package blizzard.development.currencies.listeners.commons;

import blizzard.development.currencies.database.cache.PlayersCacheManager;
import blizzard.development.currencies.database.dao.PlayersDAO;
import blizzard.development.currencies.database.storage.PlayersData;
import blizzard.development.currencies.utils.PluginImpl;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

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

            final FileConfiguration config = PluginImpl.getInstance().Config.getConfig();

            playersData = new PlayersData(
                    player.getUniqueId().toString(),
                    player.getName(),
                    config.getDouble("currencies.initial-balance.souls"),
                    config.getDouble("currencies.initial-balance.flakes"),
                    config.getDouble("currencies.initial-balance.fossils"),
                    config.getDouble("currencies.initial-balance.spawners-limit")
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
