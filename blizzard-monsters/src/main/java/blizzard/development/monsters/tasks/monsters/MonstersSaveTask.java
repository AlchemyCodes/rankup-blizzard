package blizzard.development.monsters.tasks.monsters;

import blizzard.development.monsters.database.cache.managers.MonstersCacheManager;
import blizzard.development.monsters.database.cache.managers.PlayersCacheManager;
import blizzard.development.monsters.database.dao.MonstersDAO;
import blizzard.development.monsters.database.dao.PlayersDAO;
import org.bukkit.scheduler.BukkitRunnable;

import java.sql.SQLException;

public class MonstersSaveTask extends BukkitRunnable {
    private final MonstersDAO monstersDAO;

    public MonstersSaveTask(MonstersDAO monstersDAO) {
        this.monstersDAO = monstersDAO;
    }

    @Override
    public void run() {
        MonstersCacheManager.getInstance().monstersCache.forEach((player, monstersData) -> {
            try {
                monstersDAO.updateMonsterData(monstersData);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });
    }
}