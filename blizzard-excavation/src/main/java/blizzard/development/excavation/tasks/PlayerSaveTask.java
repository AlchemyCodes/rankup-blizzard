package blizzard.development.excavation.tasks;

import blizzard.development.excavation.database.cache.PlayerCacheManager;
import blizzard.development.excavation.database.dao.PlayerDAO;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.sql.SQLException;

public class PlayerSaveTask extends BukkitRunnable {

    private final PlayerDAO playerDAO;

    public PlayerSaveTask(PlayerDAO playerDAO) {
        this.playerDAO = playerDAO;
    }

    @Override
    public void run() {
        PlayerCacheManager.playerCache.forEach((player, playerData) -> {
            try {
                playerDAO.updatePlayerData(playerData);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });

        Bukkit.getConsoleSender().sendMessage("§a[Escavação] §aSalvando " + Bukkit.getOnlinePlayers().size() + " jogadores do cache na database.");
    }
}
