package blizzard.development.core.listener.campfire;

import blizzard.development.core.api.CoreAPI;
import blizzard.development.core.handler.FurnaceItemHandler;
import blizzard.development.core.managers.GeneratorManager;
import blizzard.development.core.managers.SchematicManager;
import com.comphenix.protocol.wrappers.BlockPosition;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;

public class CampfirePlace implements Listener {
    @EventHandler
    public void onBlockPlace(BlockPlaceEvent event) {
        Player player = event.getPlayer();
        Block block = event.getBlock();

        BlockPosition campfirePosition = new BlockPosition(
                block.getLocation().add(0, 0, 0).getBlockX(),
                block.getLocation().add(0, 0, 0).getBlockY(),
                block.getLocation().add(0, 0, 0).getBlockZ()
        );

        if (FurnaceItemHandler.isCampfire(player)) {
            event.setCancelled(true);
            if (!CoreAPI.getInstance().isIsBlizzard()) {
                event.setCancelled(true);
                player.sendMessage("Você não precisa se aquecer agora!");
                return;
            }

            if (GeneratorManager.hasGenerator(player)) {
                event.setCancelled(true);
                player.sendMessage("Você já tem uma fogueira!");
                return;
            }

            Block blockBelow = block.getLocation().add(0, -1, 0).getBlock();

            if (!GeneratorManager.isBlockLegit(blockBelow.getWorld().getName(), blockBelow.getX(), blockBelow.getY(), blockBelow.getZ())) {
                return;
            }

            SchematicManager schematicManager = new SchematicManager();
            schematicManager.pasteSchematicForPlayer(player, block.getLocation().add(-5,-1, -5));
            player.teleport(block.getLocation().add(-10,-1, -10));

            GeneratorManager.placeGenerator(player, campfirePosition);

            player.getInventory().setItem(player.getInventory().getHeldItemSlot(), null);

            event.setCancelled(true);
        }
    }
}
