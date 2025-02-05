package blizzard.development.mine.listeners.mine;

import blizzard.development.core.Main;
import blizzard.development.currencies.api.CurrenciesAPI;
import blizzard.development.currencies.enums.Currencies;
import blizzard.development.mine.builders.item.ItemBuilder;
import blizzard.development.mine.database.cache.methods.BoosterCacheMethods;
import blizzard.development.mine.database.cache.methods.PlayerCacheMethods;
import blizzard.development.mine.database.cache.methods.ToolCacheMethods;
import blizzard.development.mine.managers.events.AvalancheManager;
import blizzard.development.mine.managers.mine.BlockManager;
import blizzard.development.mine.mine.adapters.EnchantmentAdapter;
import blizzard.development.mine.mine.adapters.ExtractorAdapter;
import blizzard.development.mine.mine.enums.BlockEnum;
import blizzard.development.mine.mine.enums.VipEnum;
import blizzard.development.mine.mine.events.mine.MineBlockBreakEvent;
import blizzard.development.mine.mine.item.ToolBuildItem;
import blizzard.development.mine.utils.packets.MinePacketUtils;
import blizzard.development.mine.utils.text.NumberUtils;
import blizzard.development.mine.utils.text.ProgressBarUtils;
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

        if (!BlockManager.getInstance().hasBlock(block.getX(), block.getY(), block.getZ())) return;
        if (!hasValidTool(player)) return;

        updateBlockState(player, block);
        processRewards(player, block);
    }

    private boolean hasValidTool(Player player) {
        ItemStack item = player.getInventory().getItemInMainHand();
        if (!ItemBuilder.hasPersistentData(Main.getInstance(), item, "blizzard.mine.tool")) {
            player.sendActionBar("§c§lEI! §cVocê precisa de uma picareta para fazer isso.");
            return false;
        }
        return true;
    }

    private void updateBlockState(Player player, Block block) {
        MinePacketUtils.getInstance().sendAirBlock(player, block);

        ToolCacheMethods toolCacheMethods = ToolCacheMethods.getInstance();
        toolCacheMethods.setBlocks(player, toolCacheMethods.getBlocks(player) + 1);
    }

    private void processRewards(Player player, Block block) {
        PlayerCacheMethods playerCacheMethods = PlayerCacheMethods.getInstance();
        BoosterCacheMethods boosterCacheMethods = BoosterCacheMethods.getInstance();
        double booster = boosterCacheMethods.getBoosterMultiplier(boosterCacheMethods.getBoosterName(player));
        double bonus = getBonus(player);

        double moneyToAdd = getBlockPrice(player, playerCacheMethods) * booster * bonus;
        double blockToAdd = 1 * booster * bonus;

        addBalance(player, moneyToAdd, blockToAdd);

        ExtractorAdapter.getInstance().activeExtractor(
                player,
                block,
                moneyToAdd,
                blockToAdd);

        player.getInventory().setItem(4, ToolBuildItem.tool(player));

        EnchantmentAdapter.getInstance().meteor(player);

        sendActionBarMessage(player, moneyToAdd, blockToAdd, booster, bonus);
    }

    private double getBlockPrice(Player player, PlayerCacheMethods playerCacheMethods) {
        if (AvalancheManager.isAvalancheActive) {
            return BlockEnum.AVALANCHE_SNOW.getValue();
        } else {
            return BlockEnum.valueOf(playerCacheMethods.getAreaBlock(player)).getValue();
        }
    }

    private void addBalance(Player player, double coins, double blocks) {
        CurrenciesAPI currenciesAPI = CurrenciesAPI.getInstance();
        currenciesAPI.addBalance(player, Currencies.COINS, coins);
        currenciesAPI.addBalance(player, Currencies.BLOCKS, blocks);
    }

    private double getBonus(Player player) {
        for (VipEnum vip : VipEnum.values()) {
            if (player.hasPermission(vip.getPermission())) {
                return vip.getBonus();
            }
        }
        return 1.0;
    }

    private void sendActionBarMessage(Player player, double money, double blocks, double booster, double bonus) {
        String formattedMoney = NumberUtils.getInstance().formatNumber(money);
        String formattedBlocks = NumberUtils.getInstance().formatNumber(blocks);
        String boosterName = BoosterCacheMethods.getInstance().getBoosterName(player);

        StringBuilder message = new StringBuilder("§e§lMina! §7• §f§l+§a§l$§f" + formattedMoney +
                " §f§l+§b§l❒§b" + formattedBlocks +
                "§8 ✷ §dExtratora " + ProgressBarUtils.getInstance().extractor(player));

        if (!boosterName.isEmpty()) {
            message.append(" §8✦ §eBooster ativo §l").append(booster).append("x");
        }

        if (bonus > 1) {
            message.append(" §8✩ §7Bônus de ").append(bonus).append("§l%");
        }

        player.sendActionBar(message.toString());
    }
}
