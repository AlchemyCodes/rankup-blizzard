package blizzard.development.vips.tasks;

import blizzard.development.vips.database.cache.PlayersCacheManager;
import blizzard.development.vips.database.cache.methods.PlayersCacheMethod;
import blizzard.development.vips.database.dao.PlayersDAO;
import blizzard.development.vips.database.storage.PlayersData;
import blizzard.development.vips.utils.vips.VipUtils;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.scheduler.BukkitRunnable;
import org.jetbrains.annotations.NotNull;

import java.sql.SQLException;
import java.util.Collection;
import java.util.List;

public class VipTask extends BukkitRunnable {

    @Override
    public void run() {
        if (VipUtils.getInstance().isVipTimeFrozen) return;

        for (@NotNull OfflinePlayer player : Bukkit.getOfflinePlayers()) {
                Collection<PlayersData> allPlayersData = PlayersCacheManager.getInstance().getAllPlayersData();

                for (PlayersData playerVip : allPlayersData) {
                    long vipDuration = playerVip.getVipDuration();
                    String vipName = playerVip.getVipName();

                    if (vipDuration <= 0) continue;

                    if (vipDuration <= 2) {
                        PlayersCacheMethod.getInstance().removeVip(playerVip.getVipId());

                        Bukkit.dispatchCommand(
                                Bukkit.getConsoleSender(), "lp user " + player.getName() + " parent remove " + vipName.toLowerCase());
                        continue;
                    }

                    playerVip.setVipDuration(vipDuration - 1);
                    PlayersCacheManager.getInstance().cachePlayerData(playerVip.getVipId(), playerVip);
                }
            }
        }
    }



