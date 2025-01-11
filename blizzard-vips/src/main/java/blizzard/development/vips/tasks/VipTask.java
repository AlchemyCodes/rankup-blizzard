package blizzard.development.vips.tasks;

import blizzard.development.vips.database.dao.PlayersDAO;
import blizzard.development.vips.database.storage.PlayersData;
import blizzard.development.vips.utils.vips.VipUtils;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.scheduler.BukkitRunnable;
import org.jetbrains.annotations.NotNull;

import java.sql.SQLException;
import java.util.List;

public class VipTask extends BukkitRunnable {

    private final PlayersDAO playersDAO;

    public VipTask(PlayersDAO playersDAO) {
        this.playersDAO = playersDAO;
    }

    @Override
    public void run() {
        if (VipUtils.getInstance().isVipTimeFrozen) return;

        for (@NotNull OfflinePlayer player : Bukkit.getOfflinePlayers()) {
            try {
                List<PlayersData> allPlayerVips = playersDAO.getAllPlayerVips(player.getName());

                for (PlayersData playerVip : allPlayerVips) {
                    long vipDuration = playerVip.getVipDuration();
                    String vipName = playerVip.getVipName();

                    if (vipDuration <= 0) continue;

                    if (vipDuration <= 2) {
                        playersDAO.deletePlayerVip(playerVip);

                        Bukkit.dispatchCommand(
                                Bukkit.getConsoleSender(), "lp user " + player.getName() + " parent remove " + vipName.toLowerCase());
                        continue;
                    }

                    playerVip.setVipDuration(vipDuration - 1);
                    playersDAO.updatePlayerData(playerVip);
                }


            } catch (SQLException e) {
                throw new RuntimeException(e);
             }
            }
        }
    }



