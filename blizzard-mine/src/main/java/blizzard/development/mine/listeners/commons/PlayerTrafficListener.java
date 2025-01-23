package blizzard.development.mine.listeners.commons;

import blizzard.development.mine.database.cache.PlayerCacheManager;
import blizzard.development.mine.database.cache.ToolCacheManager;
import blizzard.development.mine.database.dao.PlayerDAO;
import blizzard.development.mine.database.dao.ToolDAO;
import blizzard.development.mine.database.storage.PlayerData;
import blizzard.development.mine.database.storage.ToolData;
import blizzard.development.mine.mine.enums.BlockEnum;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.sql.SQLException;
import java.util.ArrayList;

public class PlayerTrafficListener implements Listener {

    private final PlayerDAO playerDAO;
    private final ToolDAO toolDAO;

    public PlayerTrafficListener(PlayerDAO playerDAO, ToolDAO toolDAO) {
        this.playerDAO = playerDAO;
        this.toolDAO = toolDAO;
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();

        PlayerData playerData = playerDAO.findPlayerData(player.getUniqueId().toString());

        if (playerData == null) {
            playerData = new PlayerData(
                    player.getUniqueId().toString(),
                    player.getName(),
                    15,
                    BlockEnum.STONE.name(),
                    false,
                    new ArrayList<>()
            );
            try {
                playerDAO.createPlayerData(playerData);
            } catch (SQLException exception) {
                exception.printStackTrace();
            }
        }

        PlayerCacheManager.getInstance().cachePlayerData(
                player.getUniqueId(),
                playerData
        );

        ToolData toolData = toolDAO.findToolData(player.getUniqueId().toString());

        if (toolData == null) {
            toolData = new ToolData(
                    player.getUniqueId().toString(),
                    player.getName(),
                    0,
                    0
            );
            try {
                toolDAO.createToolData(toolData);
            } catch (SQLException exception) {
                exception.printStackTrace();
            }
        }

        ToolCacheManager.getInstance().cacheToolData(
                player.getUniqueId(),
                toolData
        );
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event) throws SQLException {
        Player player = event.getPlayer();

        PlayerData playerData = PlayerCacheManager.getInstance().getPlayerData(player);
        playerData.setInMine(false);
        playerDAO.updatePlayerData(playerData);

        ToolData toolData = ToolCacheManager.getInstance().getToolData(player);
        toolDAO.updateToolData(toolData);
    }
}
