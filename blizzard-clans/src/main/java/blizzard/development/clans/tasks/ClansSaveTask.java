package blizzard.development.clans.tasks;

import org.bukkit.scheduler.BukkitRunnable;
import blizzard.development.clans.database.cache.ClansCacheManager;
import blizzard.development.clans.database.dao.ClansDAO;

import java.sql.SQLException;

public class ClansSaveTask extends BukkitRunnable {
    private final ClansDAO clansDAO;

    public ClansSaveTask(ClansDAO clansDAO) {
        this.clansDAO = clansDAO;
    }

    @Override
    public void run() {
        ClansCacheManager.clansCache.forEach((clan, clansData) -> {
            try {
                clansDAO.updateClansData(clansData);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });
    }
}
