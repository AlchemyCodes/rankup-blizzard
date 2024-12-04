package blizzard.development.core.listener.campfire;

import blizzard.development.core.api.CoreAPI;
import blizzard.development.core.handler.FurnaceItemHandler;
import blizzard.development.core.managers.CampfireManager;
import blizzard.development.core.managers.SchematicManager;
import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.wrappers.BlockPosition;
import com.comphenix.protocol.wrappers.WrappedBlockData;
import org.bukkit.Location;
import org.bukkit.Material;
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

            if (CampfireManager.hasCampfire(player)) {
                event.setCancelled(true);
                player.sendMessage("Você já tem uma fogueira!");
                return;
            }

            Block blockBelow = block.getLocation().add(0, -1, 0).getBlock();

            if (!CampfireManager.isBlockLegit(blockBelow.getWorld().getName(), blockBelow.getX(), blockBelow.getY(), blockBelow.getZ())) {
                return;
            }

            CampfireManager.placeCampfire(player, campfirePosition);

            SchematicManager schematicManager = new SchematicManager();
            schematicManager.pasteSchematicForPlayer(player, block.getLocation());

            player.getInventory().setItem(player.getInventory().getHeldItemSlot(), null);
        }
    }
}
