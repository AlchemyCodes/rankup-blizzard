package blizzard.development.vips.listeners;

import blizzard.development.vips.database.cache.PlayersCacheManager;
import blizzard.development.vips.database.dao.PlayersDAO;
import blizzard.development.vips.database.storage.PlayersData;
import blizzard.development.vips.utils.vips.VipUtils;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import java.sql.SQLException;


public class TrafficListener implements Listener {

    private final PlayersDAO playersDAO;

    public TrafficListener(PlayersDAO playersDAO) {
        this.playersDAO = playersDAO;
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();

        PlayersData playersData = playersDAO.findPlayerData(player.getUniqueId().toString());

        if (playersData == null) {
            playersData = new PlayersData(player.getUniqueId().toString(), 1, player.getName(), "", "", "", 0);
            try {
                playersDAO.createPlayerData(playersData);
            } catch(Exception err) {
                err.printStackTrace();
            }
        }

        PlayersCacheManager.getInstance().cachePlayerData(player.getName(), playersData);

        VipUtils vipUtils = VipUtils.getInstance();


        try {
            if (vipUtils.hasVip(player.getName(), "alchemy")) {
                vipUtils.setActiveVip(player, "alchemy");
            } else if (vipUtils.hasVip(player.getName(), "blizzard")) {
                vipUtils.setActiveVip(player, "blizzard");
            } else if (vipUtils.hasVip(player.getName(), "esmeralda")) {
                vipUtils.setActiveVip(player, "esmeralda");
            } else if (vipUtils.hasVip(player.getName(), "diamante")) {
                vipUtils.setActiveVip(player, "diamante");
            } else if (vipUtils.hasVip(player.getName(), "ouro")) {
                vipUtils.setActiveVip(player, "ouro");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
