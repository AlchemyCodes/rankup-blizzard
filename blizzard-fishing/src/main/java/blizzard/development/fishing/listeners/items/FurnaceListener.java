package blizzard.development.fishing.listeners.items;

import blizzard.development.fishing.handlers.FurnaceItemHandler;
import blizzard.development.fishing.inventories.items.FurnaceInventory;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

public class FurnaceListener implements Listener {

    @EventHandler
    public void onInteractWithFurnaceItem(PlayerInteractEvent event) {
        Player player = event.getPlayer();

        if (!(FurnaceItemHandler.isFurnace(player))) return;

        if (event.getItem() == null) return;

        FurnaceInventory.openFurnace(player);
        event.setCancelled(true);
    }

    @EventHandler
    public void onInteractWithFurnaceBuild(PlayerInteractEvent event) {
        if (event.getAction() == Action.RIGHT_CLICK_BLOCK || event.getAction() == Action.LEFT_CLICK_BLOCK) {
            Block block = event.getClickedBlock();

            if (block != null) {
                Location loc = block.getLocation();

                if (isInRegion(loc)) {
                    FurnaceInventory.openFurnace(event.getPlayer());
                }
            }
        }
    }

    private boolean isInRegion(Location loc) {
        int x = loc.getBlockX();
        int y = loc.getBlockY();
        int z = loc.getBlockZ();

        return x >= -11 && x <= -10 &&
                y >= -5 && y <= -1 &&
                z >= -25 && z <= -21;
    }
}
