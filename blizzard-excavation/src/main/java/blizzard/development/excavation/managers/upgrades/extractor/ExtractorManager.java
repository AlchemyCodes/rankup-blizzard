package blizzard.development.excavation.managers.upgrades.extractor;


import blizzard.development.excavation.Main;
import blizzard.development.excavation.database.cache.methods.ExcavatorCacheMethod;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

import java.util.concurrent.ThreadLocalRandom;

public class ExtractorManager {

    private static final ExcavatorCacheMethod excavatorCacheMethod = new ExcavatorCacheMethod();
    private static final ExtractorBreakEffect extractorBreakEffect = new ExtractorBreakEffect(Main.getInstance());

    public static void check(Player player, Block block) {
        int extractor = excavatorCacheMethod.extractorEnchant(player.getName());

        double random = ThreadLocalRandom.current().nextDouble(0, 100);

        if (random <= activation(extractor)) {
            extractorBreakEffect.startExcavatorBreak(block, player, 3, 5);

            player.sendTitle(
                    "§4§lEXTRATOR",
                    "§4O encantamento foi ativado.",
                    10,
                    70,
                    20
            );
        }
    }

    public static double activation(int level) {

        double base = 0.002;
        double increase = 0.005;

        return Math.min(base + (increase * level), 100.0);
    }
}
