package blizzard.development.plantations.tasks;

import blizzard.development.plantations.database.cache.PlayerCacheManager;
import blizzard.development.plantations.database.dao.PlayerDAO;
import org.bukkit.scheduler.BukkitRunnable;

import java.sql.SQLException;

public class PlayerSaveTask extends BukkitRunnable {

    private final PlayerDAO playerDAO;
    private final PlayerCacheManager playerCacheManager = PlayerCacheManager.getInstance();

    public PlayerSaveTask(PlayerDAO playerDAO) {
        this.playerDAO = playerDAO;
    }

    @Override
    public void run() {
        playerCacheManager.playerCache.forEach((player, playerData) -> {
            try {
                playerDAO.updatePlayerData(playerData);
            } catch (SQLException exception) {
                throw new RuntimeException("Erro ao atualizar dados do jogador " + playerData.getNickname(), exception);
            }
        });
    }
}
