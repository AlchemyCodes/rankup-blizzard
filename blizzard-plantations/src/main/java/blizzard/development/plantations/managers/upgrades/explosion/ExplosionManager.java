package blizzard.development.plantations.managers.upgrades.explosion;

import blizzard.development.plantations.Main;
import blizzard.development.plantations.database.cache.methods.ToolCacheMethod;
import blizzard.development.plantations.managers.AreaManager;
import blizzard.development.plantations.utils.LocationUtils;
import blizzard.development.plantations.utils.TextUtils;
import blizzard.development.plantations.utils.packets.PacketUtils;
import net.kyori.adventure.title.Title;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.time.Duration;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

import static blizzard.development.plantations.utils.NumberFormat.formatNumber;

public class ExplosionManager {

    public static final ToolCacheMethod toolCacheMethod = ToolCacheMethod.getInstance();

    public static void check(Player player, Block block, String id) {
        int explosion = toolCacheMethod.getExplosion(id);

        double random = ThreadLocalRandom.current().nextDouble(0, 100);

        int area = AreaManager.getInstance().getArea(player) - 5;
        Location location = LocationUtils.getCenterLocation();

        double randomX = new Random().nextDouble(-area, area);
        double randomZ = new Random().nextDouble(-area, area);


        if (random <= activation(explosion)) {
            PacketUtils.getInstance()
                .sendEntityPacket(
                    location.getBlock().getLocation().add(randomX, 58, randomZ),
                    player
                );

            new BukkitRunnable() {
                int i = 0;
                @Override
                public void run() {
                    i++;

                    if (i == 5) {
                        ExplosionEffect.startExplosionBreak(player, location.getBlock().getLocation().add(randomX, 0, randomZ));
                        this.cancel();
                    }
                }
            }.runTaskTimer(Main.getInstance(), 0L, 20L);


            player.showTitle(
                Title.title(
                    TextUtils.parse("<bold><#d90404>Expl<#f71919><#f71919>osão!<#d90404></bold>"),
                    TextUtils.parse("<#f71919>O encantamento foi ativado.<#f71919>"),
                    Title.Times.times(Duration.ZERO, Duration.ofSeconds(2), Duration.ofSeconds(1))
                )
            );
        }
    }

    public static double activation(int level) {
        double base = 0.002;
        double increase = 0.005;

        double result = base + (increase * level);
        return Math.min(result, 100.0);
    }

}
