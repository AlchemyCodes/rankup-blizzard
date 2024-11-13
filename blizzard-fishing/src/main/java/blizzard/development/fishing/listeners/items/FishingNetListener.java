package blizzard.development.fishing.listeners.items;

import blizzard.development.fishing.handlers.FishingNetHandler;
import blizzard.development.fishing.handlers.FishingRodHandler;
import blizzard.development.fishing.inventories.items.FishingNetInventory;
import blizzard.development.fishing.tasks.FishingNetTask;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

import java.util.List;

public class FishingNetListener implements Listener {

    @EventHandler
    public void onInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        Action action = event.getAction();

        if (event.getItem() == null) return;

        if (!(FishingNetHandler.isNet(player))) return;

        if (action == Action.RIGHT_CLICK_BLOCK || action == Action.RIGHT_CLICK_AIR) {
            List<Block> los = player.getLineOfSight(null, 5);
            for (Block b : los) {
                    if (b.getType() == Material.WATER) {
                        if (FishingNetTask.isCatchingTrash) {
                            FishingNetTask.isCatchingTrash = false;
                            player.sendTitle("§cParou de coletar.", "");
                        } else {
                            FishingNetTask.isCatchingTrash = true;
                            player.sendTitle("§bColetando lixo!", "");
                        }
                    }
                    event.setCancelled(true);
            }
        } else {
            FishingNetInventory.openNet(player);
            event.setCancelled(true);
            return;
        }

        event.setCancelled(true);
    }
}
