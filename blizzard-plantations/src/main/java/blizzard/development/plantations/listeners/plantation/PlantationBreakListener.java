package blizzard.development.plantations.listeners.plantation;

import blizzard.development.plantations.database.cache.methods.PlayerCacheMethod;
import blizzard.development.plantations.plantations.enums.SeedEnum;
import blizzard.development.plantations.plantations.events.PlantationBreakEvent;
import blizzard.development.plantations.tasks.PlantationRegenTask;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.data.BlockData;
import org.bukkit.entity.Ageable;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

import java.util.HashMap;
import java.util.Map;

import static blizzard.development.plantations.tasks.HologramTask.initializeHologramTask;

public class PlantationBreakListener implements Listener {

    private final PlayerCacheMethod playerCacheMethod = new PlayerCacheMethod();
    public static Map<Player, Block> plantations = new HashMap<>();


    @EventHandler
    public void onBreak(BlockBreakEvent event) {
        Player player = event.getPlayer();
        Block block = event.getBlock();

        PlantationBreakEvent plantationBreakEvent = new PlantationBreakEvent(player, block);
        Bukkit.getPluginManager().callEvent(plantationBreakEvent);
    }


    @EventHandler
    public void onPlantationBreak(PlantationBreakEvent event) {
        Player player = event.getPlayer();
        Block block = event.getBlock();


        if (playerCacheMethod.isInPlantation(player)) {
            if (block.getType() == Material.WHEAT) {
                plantations.put(player, block);
                initializeHologramTask(player, block, SeedEnum.POTATO);


                block.setType(Material.WHEAT);
                BlockData blockData = block.getBlockData();

                if (blockData instanceof Ageable ageable) {
                    ageable.setAge(0);
                    block.setBlockData((BlockData) ageable);
                }

                PlantationBreakListener.plantations.forEach(((players, plantations) -> {
                    PlantationRegenTask.create(plantations, Material.WHEAT, 5);
                }));

                playerCacheMethod.setPlantations(player, playerCacheMethod.getPlantations(player) + 1);
                block.setType(Material.AIR);
            }
        }
    }
}
