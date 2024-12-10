package blizzard.development.rankup.listeners;

import blizzard.development.rankup.database.cache.PlayersCacheManager;
import blizzard.development.rankup.database.dao.PlayersDAO;
import blizzard.development.rankup.database.storage.PlayersData;
import blizzard.development.rankup.utils.RanksUtils;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public final class TrafficListener implements Listener {
    private final PlayersDAO playersDAO;

    public TrafficListener(PlayersDAO playersDAO) {
        this.playersDAO = playersDAO;
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event) {}

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        String name = player.getName();
        String uuid = player.getUniqueId().toString();

        PlayersData playersData = this.playersDAO.findPlayerData(uuid);

        if (playersData == null) {
            playersData = new PlayersData(uuid, name, RanksUtils.getRankWithMinOrder(), 0);
            try {
                this.playersDAO.createPlayerData(playersData);
            } catch(Exception err) {
                err.printStackTrace();
            }
        }

        PlayersCacheManager.getInstance().cachePlayerData(player, playersData);
    }
}
