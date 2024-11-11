package blizzard.development.plantations.managers.upgrades.plow;

import blizzard.development.plantations.database.cache.methods.ToolCacheMethod;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

import java.util.concurrent.ThreadLocalRandom;

public class PlowManager {

    public static final ToolCacheMethod toolCacheMethod = ToolCacheMethod.getInstance();

    public static void check(Player player, Block block, String id) {
        int explosion = toolCacheMethod.getPlow(id);

        double random = ThreadLocalRandom.current().nextDouble(0, 100);

        if (random <= activation(explosion)) {
            PlowEffect.startPlowEffect(block.getLocation());
            player.sendTitle(
                    "§c§lARADOR",
                    "§cO encantamento foi ativado.",
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
