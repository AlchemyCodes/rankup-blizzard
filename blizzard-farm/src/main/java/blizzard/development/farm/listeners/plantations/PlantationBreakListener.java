package blizzard.development.farm.listeners.plantations;

import blizzard.development.farm.farm.adapters.PlantationAdapter;
import blizzard.development.farm.farm.events.PlantationBreakEvent;
import blizzard.development.farm.utils.PluginImpl;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.data.Ageable;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;

import static blizzard.development.farm.builders.item.ItemBuilder.hasPersistentData;

public class PlantationBreakListener implements Listener {

    private final PlantationAdapter plantationAdapter = PlantationAdapter.getInstance();

    @EventHandler
    public void onBreak(BlockBreakEvent event) {
        Player player = event.getPlayer();
        Block block = event.getBlock();

        PlantationBreakEvent plantationBreakEvent = new PlantationBreakEvent(player, block);

        if (plantationBreakEvent.isCancelled()) {
            event.setCancelled(true);
            return;
        }

        if (block.getType() == Material.CARROTS ||
            block.getType() == Material.POTATOES ||
            block.getType() == Material.WHEAT ||
            block.getType() == Material.MELON ||
            block.getType() == Material.CACTUS) {
            event.setDropItems(false);

            plantationBreakEvent.callEvent();
        }
    }

    @EventHandler
    public void onPlantationBreak(PlantationBreakEvent event) {
        Player player = event.getPlayer();
        Block block = event.getBlock();

        ItemStack itemStack = player.getInventory().getItemInMainHand();

        if (!hasPersistentData(PluginImpl.getInstance().plugin, itemStack, "blizzard.farm.tool")) {
            player.sendActionBar("§c§lEI! §cUse um machado para poder colher suas plantações.");
            event.setCancelled(true);
            return;
        }

        if (block.getType() != Material.MELON && block.getType() != Material.CACTUS) {
            Ageable ageable = (Ageable) block.getBlockData();
            if (ageable.getAge() < ageable.getMaximumAge()) {
                player.sendActionBar("§c§lEI! §cEspere a plantação crescer completamente!");
                event.setCancelled(true);
                return;
            }
        }

        switch (block.getType()) {
            case CARROTS:

                plantationAdapter
                    .handleBreakCarrots(
                        player,
                        block
                    );

                block.setType(Material.DIAMOND_BLOCK);
                break;
            case POTATOES:

                plantationAdapter
                    .handleBreakPotatoes(
                        player,
                        block
                    );

                break;
            case WHEAT:

                plantationAdapter
                    .handleBreakWheat(
                        player,
                        block
                    );

                break;
            case MELON:

                plantationAdapter
                    .handleBreakMelon(
                        player,
                        block
                    );

                break;
            case CACTUS:

                plantationAdapter
                    .handleBreakCactus(
                        player,
                        block
                    );

                break;
        }
    }
}
