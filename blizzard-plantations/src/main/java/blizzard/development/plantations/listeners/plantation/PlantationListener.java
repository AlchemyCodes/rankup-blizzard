package blizzard.development.plantations.listeners.plantation;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.data.type.Farmland;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityChangeBlockEvent;

public class PlantationListener implements Listener {

    @EventHandler
    public void onFarmlandTrample(EntityChangeBlockEvent event) {
        Block block = event.getBlock();

        if (block.getType() == Material.FARMLAND) {
            Farmland farmland = (Farmland) block.getBlockData();

            if (farmland.getMoisture() == farmland.getMaximumMoisture()) {
                event.setCancelled(true);
            }
        }
    }
}
