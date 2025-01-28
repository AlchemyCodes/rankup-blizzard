package blizzard.development.mine.listeners.mine;

import blizzard.development.core.Main;
import blizzard.development.currencies.api.CurrenciesAPI;
import blizzard.development.currencies.enums.Currencies;
import blizzard.development.mine.builders.item.ItemBuilder;
import blizzard.development.mine.database.cache.methods.PlayerCacheMethods;
import blizzard.development.mine.database.cache.methods.ToolCacheMethods;
import blizzard.development.mine.managers.events.AvalancheManager;
import blizzard.development.mine.managers.mine.BlockManager;
import blizzard.development.mine.mine.adapters.EnchantmentAdapter;
import blizzard.development.mine.mine.adapters.ExtractorAdapter;
import blizzard.development.mine.mine.enums.BlockEnum;
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

        MineBlockBreakEvent blockBreakEvent = new MineBlockBreakEvent(player, block);

        if (!BlockManager.getInstance().hasBlock(block.getX(), block.getY(), block.getZ())) return;

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

        ToolCacheMethods toolCacheMethods = ToolCacheMethods.getInstance();
        toolCacheMethods.setBlocks(
                player,
                toolCacheMethods.getBlocks(player) + 1
        );

        PlayerCacheMethods playerCacheMethods = PlayerCacheMethods.getInstance();

        double moneyToAdd; // dps calcular com os enchant e pa
        int blockToAdd = 1; // dps calcular com os enchant e pa

        if (AvalancheManager.isAvalancheActive) {
            moneyToAdd = BlockEnum.AVALANCHE_SNOW.getValue();
        } else {
            moneyToAdd = BlockEnum.valueOf(playerCacheMethods.getAreaBlock(player)).getValue();
        }

        addBalance(player, moneyToAdd, blockToAdd);

        ExtractorAdapter.getInstance()
                .activeExtractor(
                        player,
                        block,
                        moneyToAdd,
                        blockToAdd
                );

        player.getInventory().setItem(4, ToolBuildItem.tool(player));

        EnchantmentAdapter.getInstance()
                .meteor(
                        player
                );

        double booster = 2.2;
        double bonus = 5.0;

        String formattedMoney = NumberUtils.getInstance().formatNumber(moneyToAdd);
        String formattedBlocks = NumberUtils.getInstance().formatNumber(blockToAdd);

        player.sendActionBar(
                "§e§lMina! §7• §f§l+§a§l$§f" + formattedMoney +
                        " §f§l+§b§l❒§b" + formattedBlocks +
                        "§8 ✷ §dExtratora " + ProgressBarUtils.getInstance().extractor(player) + " §8✦ §eBooster ativo §l" + booster +
                        "x §8✩ §7Bônus de " + bonus + "§l%"
        );
    }

    private void addBalance(Player player, double coins, double blocks) {
        CurrenciesAPI currenciesAPI = CurrenciesAPI.getInstance();
        currenciesAPI.addBalance(player, Currencies.COINS, coins);
        currenciesAPI.addBalance(player, Currencies.BLOCKS, blocks);
    }
}
