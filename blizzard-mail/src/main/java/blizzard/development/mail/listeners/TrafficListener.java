package blizzard.development.mail.listeners;

import blizzard.development.mail.database.cache.PlayerCacheManager;
import blizzard.development.mail.database.dao.PlayerDao;
import blizzard.development.mail.database.storage.PlayerData;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerChangedWorldEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.ArrayList;
import java.util.List;

public class TrafficListener implements Listener {

    private final PlayerDao playerDAO;

    public TrafficListener(PlayerDao playerDAO) {
        this.playerDAO = playerDAO;
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();

        PlayerData playersData = playerDAO.findPlayerData(player.getUniqueId().toString());

        List<String> list = new ArrayList<>();

        if (playersData == null) {
            playersData = new PlayerData(player.getUniqueId().toString(), player.getName(), list);
            try {
                playerDAO.createPlayerData(playersData);
            } catch(Exception err) {
                err.printStackTrace();
            }
        }

        PlayerCacheManager.getInstance().cachePlayerData(player.getName(), playersData);
    }
}
