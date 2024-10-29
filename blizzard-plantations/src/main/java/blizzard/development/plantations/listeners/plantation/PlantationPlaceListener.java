package blizzard.development.plantations.listeners.plantation;

import blizzard.development.plantations.Main;
import blizzard.development.plantations.database.cache.methods.PlayerCacheMethod;
import blizzard.development.plantations.plantations.events.PlantationPlaceEvent;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.BlockState;
import org.bukkit.entity.Ageable;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.inventory.ItemStack;

import static blizzard.development.plantations.builder.ItemBuilder.hasPersistentData;

public class PlantationPlaceListener implements Listener {

    private final PlayerCacheMethod playerCacheMethod = new PlayerCacheMethod();

    @EventHandler
    public void onPlace(BlockPlaceEvent event) {
        Player player = event.getPlayer();
        Block block = event.getBlock();

        ItemStack item = player.getInventory().getItemInMainHand();

        PlantationPlaceEvent plantationPlaceEvent = new PlantationPlaceEvent(player, block);
        Bukkit.getPluginManager().callEvent(plantationPlaceEvent);

        if (playerCacheMethod.isInPlantation(player)) {

            if (!hasPersistentData(Main.getInstance(), item, "semente")) {
                if (player.isOp()) return;

                event.setCancelled(true);
            }

            if (!(block.getRelative(BlockFace.DOWN).getType() == Material.FARMLAND)) {
                event.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onPlantationPlace(PlantationPlaceEvent event) {
        Player player = event.getPlayer();
        Block block = event.getBlock();

        ItemStack item = player.getInventory().getItemInMainHand();

        if (playerCacheMethod.isInPlantation(player)) {
            if (hasPersistentData(Main.getInstance(), item, "semente")) {

                block.setType(Material.WHEAT);
                Block blockUP = block.getRelative(BlockFace.UP);
                BlockState blockState = blockUP.getState();

                if (blockState instanceof Ageable ageable) {
                    ageable.setAge(0);
                }

            }
        }
    }
}
