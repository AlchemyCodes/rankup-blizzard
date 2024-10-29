package blizzard.development.plantations.tasks;

import blizzard.development.plantations.database.cache.PlayerCacheManager;
import blizzard.development.plantations.database.dao.PlayerDAO;
import org.bukkit.Bukkit;
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
