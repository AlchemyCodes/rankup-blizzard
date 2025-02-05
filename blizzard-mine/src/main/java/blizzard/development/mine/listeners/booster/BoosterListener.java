package blizzard.development.mine.listeners.booster;

import blizzard.development.mine.mine.adapters.BoosterAdapter;
import blizzard.development.mine.mine.enums.BoosterEnum;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

public class BoosterListener implements Listener {
    @EventHandler
    public void onInteract(PlayerInteractEvent event) {
        BoosterAdapter boosterAdapter = BoosterAdapter.getInstance();

        if (event.getAction() != Action.RIGHT_CLICK_AIR && event.getAction() != Action.RIGHT_CLICK_BLOCK) {
            return;
        }

        ItemStack item = event.getItem();
        if (item == null) return;

        Player player = event.getPlayer();
        BoosterEnum booster = boosterAdapter.getBooster(item);
        if (booster != null) {
            boosterAdapter.applyBooster(player, booster);
            event.setCancelled(true);
        }
    }
}
