package blizzard.development.vips.tasks;

import blizzard.development.vips.database.cache.VipCacheManager;
import blizzard.development.vips.database.cache.methods.VipCacheMethod;
import blizzard.development.vips.database.dao.VipDAO;
import blizzard.development.vips.database.storage.VipData;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.scheduler.BukkitRunnable;
import org.jetbrains.annotations.NotNull;

import java.sql.SQLException;
import java.util.Collection;

public class VipTimeTask extends BukkitRunnable {

    @Override
    public void run() {
        Collection<VipData> vipDataCollection = VipCacheManager.getInstance().vipCache.values();
        VipCacheMethod vipCacheMethod = VipCacheMethod.getInstance();

        for (VipData vipData : vipDataCollection) {
            String vipId = vipData.getVipId();
            long vipDuration = vipCacheMethod.getVipDuration(vipId);
            String vipName = vipCacheMethod.getVipName(vipId);

            if (vipDuration <= 1) {
                continue;
            }

            if (vipDuration <= 2) {
                try {
                    Bukkit.dispatchCommand(
                            Bukkit.getConsoleSender(), "lp user " + vipCacheMethod.getNickname(vipId) + " parent remove " + vipName.toLowerCase());
                    new VipDAO().deleteVipData(vipData);
                    VipCacheManager.getInstance().vipCache.remove(vipId);
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
                continue;
            }

            vipData.setVipDuration(vipDuration - 1);
            VipCacheManager.getInstance().cacheVipData(vipCacheMethod.getVipId(vipId), vipData);
        }
    }
}


