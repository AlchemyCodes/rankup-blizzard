package blizzard.development.excavation.managers.upgrades;

import blizzard.development.excavation.Main;
import blizzard.development.excavation.database.cache.methods.ExcavatorCacheMethod;
import blizzard.development.excavation.database.cache.methods.PlayerCacheMethod;
import blizzard.development.excavation.excavation.item.ExcavatorBuildItem;
import blizzard.development.excavation.managers.upgrades.extractor.ExcavatorBreakEffect;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.concurrent.ThreadLocalRandom;

public class UpgradeExcavatorManager {

    private final ExcavatorCacheMethod excavatorCacheMethod = new ExcavatorCacheMethod();
    private final PlayerCacheMethod playerCacheMethod = new PlayerCacheMethod();
    private final ExcavatorBuildItem excavatorBuildItem = new ExcavatorBuildItem();

    public void check(Player player, Block block) {
        int totalBlocks = playerCacheMethod.getBlocks(player);

        if (totalBlocks >= 300) {
            randomEnchant(player, block);
        }
    }


    private void randomEnchant(Player player, Block block) {
        int upgrade = ThreadLocalRandom.current().nextInt(1, 4);

        int currentEfficiency = excavatorCacheMethod.effiencyEnchant(player.getName());
        int currentAgility = excavatorCacheMethod.agilityEnchant(player.getName());
        int currentExtractor = excavatorCacheMethod.extractorEnchant(player.getName());

        ExcavatorBreakEffect excavatorBreakEffect = new ExcavatorBreakEffect(Main.getInstance());

        switch (upgrade) {
            case 1:
                excavatorCacheMethod.setEfficiencyEnchant(player.getName(), currentEfficiency + 1);
                player.sendTitle(
                        "§d§lEFICIÊNCIA!",
                        "§dO encantamento foi aprimorado.",
                        10,
                        70,
                        20
                );
                break;
            case 2:
                if (currentAgility >= 2) {
                    excavatorCacheMethod.setEfficiencyEnchant(player.getName(), currentEfficiency + 1);
                    player.sendTitle(
                            "§d§lEFICIÊNCIA!",
                            "§dO encantamento foi aprimorado.",
                            10,
                            70,
                            20
                    );
                } else {
                    excavatorCacheMethod.setAgilityEnchant(player.getName(), currentAgility + 1);
                    player.sendTitle(
                            "§6§lAGILIDADE!",
                            "§6O encantamento foi aprimorado.",
                            10,
                            70,
                            20
                    );
                }
                break;
            case 3:
                excavatorCacheMethod.setExtractorEnchant(player.getName(), currentExtractor + 1);
                player.sendTitle(
                        "§4§lEXTRATOR!",
                        "§4O encantamento foi aprimorado.",
                        10,
                        70,
                        20
                );
                excavatorBreakEffect.startExcavatorBreak(block, player, 3, 5);
                break;
        }

        ItemStack newItem = excavatorBuildItem.buildExcavator(
                player,
                excavatorCacheMethod.effiencyEnchant(player.getName()),
                excavatorCacheMethod.agilityEnchant(player.getName()),
                excavatorCacheMethod.extractorEnchant(player.getName())
        );
        player.getInventory().setItemInMainHand(newItem);
        player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 3F, 1F);
    }
}
