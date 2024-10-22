package blizzard.development.excavation.listeners.player;

import blizzard.development.excavation.database.cache.PlayerCacheManager;
import blizzard.development.excavation.database.cache.methods.PlayerCacheMethod;
import blizzard.development.excavation.database.dao.ExcavatorDAO;
import blizzard.development.excavation.database.dao.PlayerDAO;
import blizzard.development.excavation.database.storage.PlayerData;
import blizzard.development.excavation.excavation.adapters.ExcavatorAdapter;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.sql.SQLException;

public class PlayerListener implements Listener {

    private final ExcavatorDAO excavatorDAO;
    private final PlayerDAO playerDAO;

    public PlayerListener(ExcavatorDAO excavatorDAO, PlayerDAO playerDAO) {
        this.excavatorDAO = excavatorDAO;
        this.playerDAO = playerDAO;
    }

    @EventHandler
    public void onChat(AsyncPlayerChatEvent event) {
        Player player = event.getPlayer();
        String message = event.getMessage();

        ExcavatorAdapter excavatorAdapter = new ExcavatorAdapter(excavatorDAO);

        if (message.equalsIgnoreCase("escavadora")) {
            excavatorAdapter.giveWithData(player);

            event.setCancelled(true);
        }
    }
    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();

        PlayerData playerData = playerDAO.findPlayerData(player.getUniqueId().toString());

        if (playerData == null) {
            playerData = new PlayerData(player.getUniqueId().toString(), player.getName(), false, 0);

            try {
                playerDAO.createPlayerData(playerData);

                ExcavatorAdapter excavatorAdapter = new ExcavatorAdapter(excavatorDAO);
                excavatorAdapter.give(player);
            } catch (SQLException exception) {
                exception.printStackTrace();
            }
        }

        PlayerCacheManager playerCacheManager = new PlayerCacheManager();
        playerCacheManager.cachePlayerData(player, playerData);
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();

        PlayerCacheMethod playerCacheMethod = new PlayerCacheMethod();
        playerCacheMethod.removeInExcavation(player);
    }
}
