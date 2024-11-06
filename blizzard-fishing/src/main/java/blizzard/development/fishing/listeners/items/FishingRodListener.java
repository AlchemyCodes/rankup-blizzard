package blizzard.development.fishing.listeners.items;

import blizzard.development.fishing.handlers.FishingRodHandler;
import blizzard.development.fishing.inventories.items.FishingRodInventory;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

public class FishingRodListener implements Listener {

    @EventHandler
    public void onInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();

        if (!(FishingRodHandler.isRod(player))) return;

        if (event.getItem() == null) return;

        if (player.isSneaking()) {
            FishingRodInventory.openRod(player);
            return;
        }

        player.sendMessage("é vara");
    }


}
