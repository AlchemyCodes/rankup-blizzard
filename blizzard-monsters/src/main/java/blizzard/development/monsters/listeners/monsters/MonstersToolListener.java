package blizzard.development.monsters.listeners.monsters;

import blizzard.development.monsters.monsters.handlers.world.MonstersWorldHandler;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerDropItemEvent;

public class MonstersToolListener implements Listener {

    @EventHandler
    public void onDropItem(PlayerDropItemEvent event) {
        Player player = event.getPlayer();

        if (MonstersWorldHandler.getInstance().containsPlayer(player)) {
            event.setCancelled(true);
        }
    }
}
