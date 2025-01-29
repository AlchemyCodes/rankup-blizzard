package blizzard.development.farm.listeners.storage;

import blizzard.development.farm.database.cache.methods.StorageCacheMethod;
import blizzard.development.farm.utils.apis.PlotSquaredAPI;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPhysicsEvent;
import org.bukkit.event.player.PlayerDropItemEvent;

public class CactusDropListener implements Listener {

    @EventHandler
    public void onCactusPhysic(BlockPhysicsEvent event) {
        Block block = event.getBlock();
        Player player = PlotSquaredAPI.getPlayerFromPlot(block);

        if (player == null) return;

        if (block.getType() == Material.CACTUS) {
            Block blockAbove = block.getRelative(BlockFace.UP);
            blockAbove.setType(Material.AIR);
            StorageCacheMethod.getInstance().addCactusAmount(player, 1);
        }
    }

    @EventHandler
    public void onCactusDrop(PlayerDropItemEvent event) {
        Player player = event.getPlayer();

        if (event.getItemDrop().getItemStack().getType() == Material.CACTUS) {
            player.sendMessage("dropado cacto entao foi pro seu armazem");
            StorageCacheMethod.getInstance().addCactusAmount(player, 1);

            event.getItemDrop().remove();
        }
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
        if (event.getBlock().getType() == Material.CACTUS) {
            event.setDropItems(false);
        }
    }
}
