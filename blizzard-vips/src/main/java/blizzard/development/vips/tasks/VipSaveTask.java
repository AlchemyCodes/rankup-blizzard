package blizzard.development.vips.tasks;
import blizzard.development.vips.database.cache.VipCacheManager;
import blizzard.development.vips.database.dao.VipDAO;
import org.bukkit.scheduler.BukkitRunnable;

import java.sql.SQLException;

public class VipSaveTask extends BukkitRunnable {
    private final VipDAO vipDAO;

    public VipSaveTask(VipDAO vipDAO) {
        this.vipDAO = vipDAO;
    }

    @Override
    public void run() {
        VipCacheManager.getInstance().vipCache.forEach((vipId, playersData) -> {
            try {
                vipDAO.updateVipData(playersData);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });
    }
}