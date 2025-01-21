package blizzard.development.mine.listeners.mine;

import blizzard.development.core.Main;
import blizzard.development.mine.builders.item.ItemBuilder;
import blizzard.development.mine.database.cache.ToolCacheManager;
import blizzard.development.mine.database.cache.methods.PlayerCacheMethods;
import blizzard.development.mine.database.cache.methods.ToolCacheMethods;
import blizzard.development.mine.managers.mine.BlockManager;
import blizzard.development.mine.mine.adapters.EnchantmentAdapter;
import blizzard.development.mine.mine.events.mine.MineBlockBreakEvent;
import blizzard.development.mine.mine.item.ToolBuildItem;
import blizzard.development.mine.utils.packets.MinePacketUtils;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;

public class MineBlockBreakListener implements Listener {

    @EventHandler
    public void onMineBlockBreak(MineBlockBreakEvent event) {
        Player player = event.getPlayer();
        Block block = event.getBlock();

        MineBlockBreakEvent blockBreakEvent = new MineBlockBreakEvent(player, block);

        if (!BlockManager.getInstance().isBlock(block.getX(), block.getY(), block.getZ())) return;

        if (blockBreakEvent.isCancelled()) return;

        ItemStack item = player.getInventory().getItemInMainHand();

        if (!ItemBuilder.hasPersistentData(
                Main.getInstance(),
                item,
                "blizzard.mine.tool"
        )) {
            player.sendActionBar("§c§lEI! §cVocê precisa de uma picareta para fazer isso.");
            return;
        }

        MinePacketUtils.getInstance().sendAirBlock(
                player,
                block
        );

        ToolCacheMethods cacheMethods = ToolCacheMethods.getInstance();
        cacheMethods.setBlocks(
                player,
                cacheMethods.getBlocks(player) + 1
        );

        PlayerCacheMethods playerCacheMethods = PlayerCacheMethods.getInstance();
        playerCacheMethods.setBlocks(
            player,
            playerCacheMethods.getBlocks(player) + 1
        );

        player.getInventory().setItem(4, ToolBuildItem.tool(player));

        EnchantmentAdapter.getInstance()
                .meteor(
                    player
                );

        player.sendActionBar("§e§lMina! §7• §f§l+§a§l$§f1M §f§l+§b§l❒§b1 §8✷ §eBooster ativo §l2.2x §8✩ §7Bônus de 5.0§l%");
    }
}
