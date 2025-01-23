package blizzard.development.mine.listeners.mine;

import blizzard.development.core.Main;
import blizzard.development.currencies.api.CurrenciesAPI;
import blizzard.development.mine.builders.item.ItemBuilder;
import blizzard.development.mine.database.cache.ToolCacheManager;
import blizzard.development.mine.database.cache.methods.PlayerCacheMethods;
import blizzard.development.mine.database.cache.methods.ToolCacheMethods;
import blizzard.development.mine.managers.mine.BlockManager;
import blizzard.development.mine.mine.adapters.EnchantmentAdapter;
import blizzard.development.mine.mine.events.mine.MineBlockBreakEvent;
import blizzard.development.mine.mine.item.ToolBuildItem;
import blizzard.development.mine.utils.packets.MinePacketUtils;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;

public class MineBlockBreakListener implements Listener {

    public HashMap<Player, Integer> extractorBlocks = new HashMap<>();

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

        int currentExtractorCount = extractorBlocks.getOrDefault(player, 0) + 1;
        extractorBlocks.put(player, currentExtractorCount);
        player.sendMessage("Extrator: " + currentExtractorCount + "/200");

        if (currentExtractorCount >= 200) {
            breakBlocksInRadius(player, block.getLocation(), 15);
            extractorBlocks.put(player, 0);
        }

        player.getInventory().setItem(4, ToolBuildItem.tool(player));

        EnchantmentAdapter.getInstance()
                .meteor(
                        player
                );

        player.sendActionBar("§e§lMina! §7• §f§l+§a§l$§f1M §f§l+§b§l❒§b1 §8✷ §eBooster ativo §l2.2x §8✩ §7Bônus de 5.0§l%");
    }

    private void breakBlocksInRadius(Player player, Location center, int radius) {
        int startX = center.getBlockX() - radius;
        int endX = center.getBlockX() + radius;
        int startY = Math.max(center.getBlockY() - radius, 0);
        int endY = Math.min(center.getBlockY() + radius, center.getWorld().getMaxHeight());
        int startZ = center.getBlockZ() - radius;
        int endZ = center.getBlockZ() + radius;

        for (int x = startX; x <= endX; x++) {
            for (int y = startY; y <= endY; y++) {
                for (int z = startZ; z <= endZ; z++) {
                    Block block = center.getWorld().getBlockAt(x, y, z);
                    if (BlockManager.getInstance().isBlock(x, y, z)) {
                        block.breakNaturally(player.getInventory().getItemInMainHand());
                        MinePacketUtils.getInstance().sendAirBlock(player, block);
                    }
                }
            }
        }
    }
}
