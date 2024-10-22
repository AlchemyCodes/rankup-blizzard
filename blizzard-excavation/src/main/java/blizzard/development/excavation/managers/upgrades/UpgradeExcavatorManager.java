package blizzard.development.excavation.managers.upgrades;

import blizzard.development.excavation.database.cache.methods.ExcavatorCacheMethod;
import blizzard.development.excavation.database.cache.methods.PlayerCacheMethod;
import blizzard.development.excavation.excavation.item.ExcavatorBuildItem;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.concurrent.ThreadLocalRandom;

public class UpgradeExcavatorManager {

    private final ExcavatorCacheMethod excavatorCacheMethod = new ExcavatorCacheMethod();
    private final PlayerCacheMethod playerCacheMethod = new PlayerCacheMethod();
    private final ExcavatorBuildItem excavatorBuildItem = new ExcavatorBuildItem();

    public void check(Player player) {
        int blocks = playerCacheMethod.getBlocks(player);

        if (blocks >= 100) {
            randomEnchant(player);
            playerCacheMethod.setBlocks(player, 0);
        }
    }

    private void randomEnchant(Player player) {
        int upgrade = ThreadLocalRandom.current().nextInt(1, 4); // 1-3 inclusive

        // Obter níveis atuais antes de atualizar
        int currentEfficiency = excavatorCacheMethod.effiencyEnchant(player.getName());
        int currentAgility = excavatorCacheMethod.agilityEnchant(player.getName());
        int currentExtractor = excavatorCacheMethod.extractorEnchant(player.getName());

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
                excavatorCacheMethod.setAgilityEnchant(player.getName(), currentAgility + 1);
                player.sendTitle(
                        "§6§lAGILIDADE!",
                        "§6O encantamento foi aprimorado.",
                        10,
                        70,
                        20
                );
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