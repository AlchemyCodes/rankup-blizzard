package blizzard.development.plantations.managers.upgrades.explosion;

import blizzard.development.plantations.database.cache.methods.ToolCacheMethod;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

import java.util.concurrent.ThreadLocalRandom;

public class ExplosionManager {

    public static final ToolCacheMethod toolCacheMethod = ToolCacheMethod.getInstance();

    public static void check(Player player, Block block, String id) {
        int explosion = toolCacheMethod.getExplosion(id);

        double random = ThreadLocalRandom.current().nextDouble(0, 100);

        if (random <= activation(explosion)) {
            ExplosionBreakEffect.startExplosionBreak(player, block.getLocation());
            player.sendTitle(
                    "§4§lEXPLOSÃO",
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
