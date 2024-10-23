package blizzard.development.core.listener.campfire;

import blizzard.development.core.managers.CampfireManager;
import blizzard.development.core.tasks.BlizzardTask;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;

public class CampfirePlace implements Listener {
    @EventHandler
    public void onBlockPlace(BlockPlaceEvent event) {
        Player player = event.getPlayer();
        Block block = event.getBlock();

        if (block.getType() == Material.CAMPFIRE) {
            if (!BlizzardTask.isSnowing) {
                event.setCancelled(true);
                player.sendMessage("Você não precisa se aquecer agora!");
                return;
            }

            if (CampfireManager.hasCampfire(player)) {
                event.setCancelled(true);
                player.sendMessage("Você já tem uma fogueira!");
                return;
            }

            CampfireManager.placeCampfire(player, block);
        }
    }
}
