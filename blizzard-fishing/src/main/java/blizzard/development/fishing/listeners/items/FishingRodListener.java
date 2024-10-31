package blizzard.development.fishing.listeners.items;

import blizzard.development.fishing.handlers.FishingRodHandler;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

public class FishingRodListener implements Listener {

    @EventHandler
    public void onInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        Action action = event.getAction();

        if (!(FishingRodHandler.isRod(player))) return;

        if (event.getItem() == null) return;

        if (player.isSneaking()) {
            player.sendMessage("abrir inventario");
            return;
        }

        event.setCancelled(true);
        player.sendMessage("é vara");
    }


}
