package blizzard.development.excavation.managers.upgrades;

import blizzard.development.excavation.database.cache.methods.ExcavatorCacheMethod;
import blizzard.development.excavation.database.cache.methods.PlayerCacheMethod;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

import java.util.concurrent.ThreadLocalRandom;

public class UpgradeExcavatorManager {

    private final ExcavatorCacheMethod excavatorCacheMethod = new ExcavatorCacheMethod();
    private final PlayerCacheMethod playerCacheMethod = new PlayerCacheMethod();

    public void check(Player player) {
        int blocks = playerCacheMethod.getBlocks(player);

        if (blocks >= 100) {
            randomEnchant(player);
            playerCacheMethod.setBlocks(player, 0);
        }
    }

    private void randomEnchant(Player player) {
        int upgrade = ThreadLocalRandom.current().nextInt(1, 3);

        switch (upgrade) {
            case 1:
                excavatorCacheMethod.setEfficiencyEnchant(player.getName(), 1);
                player.sendTitle(
                        "§d§lEFICIÊNCIA!",
                        "§dO encantamento foi aprimorado.",
                        10,
                        70,
                        20
                );
                player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 3F, 1F);
                break;
            case 2:
                excavatorCacheMethod.setAgilityEnchant(player.getName(), 1);
                player.sendTitle(
                        "§6§lAGILIDADE!",
                        "§6O encantamento foi aprimorado.",
                        10,
                        70,
                        20
                );
                player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 3F, 1F);
                break;
            case 3:
                excavatorCacheMethod.setExtractorEnchant(player.getName(), 1);
                player.sendTitle(
                        "§4§lEXTRATOR!",
                        "§4O encantamento foi aprimorado.",
                        10,
                        70,
                        20
                );
                player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 3F, 1F);
                break;
            default:
                throw new IllegalStateException("Valor inesperado: " + upgrade);

        }
    }
}
