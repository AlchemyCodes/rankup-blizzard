package blizzard.development.plantations.listeners.geral;

import blizzard.development.plantations.database.cache.PlayerCacheManager;
import blizzard.development.plantations.database.cache.methods.PlayerCacheMethod;
import blizzard.development.plantations.database.dao.PlayerDAO;
import blizzard.development.plantations.database.storage.PlayerData;
import blizzard.development.plantations.plantations.adapters.ToolAdapter;
import blizzard.development.plantations.plantations.enums.PlantationEnum;
import io.papermc.paper.event.player.PlayerFailMoveEvent;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerTeleportEvent;

import java.sql.SQLException;
import java.util.ArrayList;

public class PlayerListener implements Listener{

    private final PlayerDAO playerDAO;
    private final PlayerCacheManager playerCacheManager = PlayerCacheManager.getInstance();

    public PlayerListener(PlayerDAO playerDAO) {
        this.playerDAO = playerDAO;
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();

        PlayerData playerData = playerDAO.findPlayerData(player.getUniqueId().toString());

        if (playerData == null) {
            playerData = new PlayerData(player.getUniqueId().toString(), player.getName(), 10, PlantationEnum.POTATO.getName(), 0,0, false, new ArrayList<>());

            try {
                playerDAO.createPlayerData(playerData);

                ToolAdapter toolAdapter = new ToolAdapter();
                toolAdapter.giveTool(player);
            } catch (SQLException exception) {
                exception.printStackTrace();
            }
        }

        playerCacheManager.cachePlayerData(player.getUniqueId().toString(), playerData);
    }

    @EventHandler
    public void onTeleport(PlayerTeleportEvent event) {
        Player player = event.getPlayer();

        PlayerCacheMethod playerCacheMethod = PlayerCacheMethod.getInstance();

        if (playerCacheMethod.isInPlantation(player)) {
            player.sendTitle("§c§lEstufa!", "§cVocê saiu da estufa.", 10, 70, 20);
           playerCacheMethod.removeInPlantation(player);
        }
    }

    @EventHandler
    public void onChat(AsyncPlayerChatEvent event) {
        Player player = event.getPlayer();
        String message = event.getMessage();

        if (message.equalsIgnoreCase("cultivar")) {
            ToolAdapter toolAdapter = new ToolAdapter();
            toolAdapter.giveTool(player);
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onFailMove(PlayerFailMoveEvent event) {
        if(event.getFailReason() == PlayerFailMoveEvent.FailReason.CLIPPED_INTO_BLOCK) {
            event.setAllowed(true);
        }
    }
}