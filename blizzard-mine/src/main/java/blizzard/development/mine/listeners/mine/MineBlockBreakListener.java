package blizzard.development.mine.listeners.mine;

import blizzard.development.core.Main;
import blizzard.development.currencies.api.CurrenciesAPI;
import blizzard.development.currencies.enums.Currencies;
import blizzard.development.mine.builders.item.ItemBuilder;
import blizzard.development.mine.database.cache.ToolCacheManager;
import blizzard.development.mine.database.cache.methods.PlayerCacheMethods;
import blizzard.development.mine.database.cache.methods.ToolCacheMethods;
import blizzard.development.mine.managers.events.Avalanche;
import blizzard.development.mine.managers.mine.BlockManager;
import blizzard.development.mine.mine.adapters.EnchantmentAdapter;
import blizzard.development.mine.mine.enums.BlockEnum;
import blizzard.development.mine.mine.events.mine.MineBlockBreakEvent;
import blizzard.development.mine.mine.item.ToolBuildItem;
import blizzard.development.mine.utils.Countdown;
import blizzard.development.mine.utils.PluginImpl;
import blizzard.development.mine.utils.packets.MinePacketUtils;
import blizzard.development.mine.utils.text.TextUtils;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.concurrent.TimeUnit;

public class MineBlockBreakListener implements Listener {

    public static HashMap<Player, Integer> extractorBlocks = new HashMap<>();

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

        // GIVE MONEY AND BLOCKS
        PlayerCacheMethods playerCacheMethods = PlayerCacheMethods.getInstance();
        CurrenciesAPI currenciesAPI = CurrenciesAPI.getInstance();
        int blockToAdd = 1;
        double moneyToAdd;
        if (Avalanche.isAvalancheActive) {
            moneyToAdd = BlockEnum.AVALANCHE_SNOW.getValue();
        } else {
            moneyToAdd = BlockEnum.valueOf(playerCacheMethods.getAreaBlock(player)).getValue();
        }
        currenciesAPI.addBalance(player, Currencies.BLOCKS, blockToAdd);
        currenciesAPI.addBalance(player, Currencies.COINS, moneyToAdd);
        // GIVE MONEY AND BLOCKS

        // EXTRATORA
        int currentExtractorCount = extractorBlocks.getOrDefault(player, 0) + 1;
        addExtractorBlocks(player, currentExtractorCount);
        giveExtractorRewards(currentExtractorCount, player, block, currenciesAPI, blockToAdd, moneyToAdd);
        // EXTRATORA

        player.getInventory().setItem(4, ToolBuildItem.tool(player));

        EnchantmentAdapter.getInstance()
                .meteor(
                        player
                );

        double booster = 2.2;
        double bonus = 5.0;
        player.sendActionBar(
                "§e§lMina! §7• §f§l+§a§l$§f" + moneyToAdd +
                " §f§l+§b§l❒§b" + blockToAdd +
                "§8✷ §eBooster ativo §l" + booster +
                "x §8✩ §7Bônus de " + bonus + "§l%");
    }

    private static void addExtractorBlocks(Player player, int currentExtractorCount) {
        extractorBlocks.put(player, currentExtractorCount);

        Countdown countdown = Countdown.getInstance();
        if (countdown.isInCountdown(player, "extratora")) return;

        player.sendMessage("Extrator: " + currentExtractorCount + "/200");

        countdown.createCountdown(player, "extratora", 1, TimeUnit.SECONDS);
    }

    private void giveExtractorRewards(int currentExtractorCount, Player player, Block block, CurrenciesAPI currenciesAPI ,int blocks, double money) {
        int blocksNecessary = PluginImpl.getInstance().Config.getConfig().getInt("mine.extractor");

        if (currentExtractorCount >= blocksNecessary) {
            int brokenBlocks = breakBlocksInRadius(player, block.getLocation(), 15);
            int blocksPrice = blocks * brokenBlocks;
            double moneyToAdd = money * brokenBlocks;

            breakBlocksInRadius(player, block.getLocation(), 15);

            currenciesAPI.addBalance(player, Currencies.BLOCKS, blocksPrice);
            currenciesAPI.addBalance(player, Currencies.COINS, moneyToAdd);

            player.sendMessage(TextUtils.parse(" <bold><#FFAA00>Ext<#ffb624><#ffb624>rat<#ffb624><#ffb624>ora!<#FFAA00></bold> " +
                    "§8✈ §f§l+§a" + moneyToAdd + "§l$ §7♦ §fBlocos: §7" + brokenBlocks));
            extractorBlocks.put(player, 0);
        }
    }

    private int breakBlocksInRadius(Player player, Location center, int radius) {
        int startX = center.getBlockX() - radius;
        int endX = center.getBlockX() + radius;
        int startY = Math.max(center.getBlockY() - radius, 0);
        int endY = Math.min(center.getBlockY() + radius, center.getWorld().getMaxHeight());
        int startZ = center.getBlockZ() - radius;
        int endZ = center.getBlockZ() + radius;

        int blockCount = 0;

        for (int x = startX; x <= endX; x++) {
            for (int y = startY; y <= endY; y++) {
                for (int z = startZ; z <= endZ; z++) {
                    Block block = center.getWorld().getBlockAt(x, y, z);
                    if (BlockManager.getInstance().isBlock(x, y, z)) {
                        blockCount++;
                        MinePacketUtils.getInstance().sendAirBlock(player, block);
                    }
                }
            }
        }

        return blockCount;
    }
}
