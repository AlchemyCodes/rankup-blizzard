package blizzard.development.plantations.managers.upgrades.lightning;

import blizzard.development.plantations.Main;
import blizzard.development.plantations.database.cache.methods.ToolCacheMethod;
import blizzard.development.plantations.managers.AreaManager;
import blizzard.development.plantations.utils.LocationUtils;
import blizzard.development.plantations.utils.TextUtils;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.title.Title;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.time.Duration;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class LightningManager {

    public static final ToolCacheMethod toolCacheMethod = ToolCacheMethod.getInstance();

    public static void check(Player player, Block block, String id) {
        int thunderstorm = toolCacheMethod.getThunderstorm(id);

        int area = AreaManager.getInstance().getArea(player) - 3;
        Location location = LocationUtils.getCenterLocation();

        double random = ThreadLocalRandom.current().nextDouble(0, 100);

        if (random <= activation(thunderstorm)) {
            new BukkitRunnable() {
                int i = 0;
                @Override
                public void run() {
                    i++;

                    double randomX = new Random().nextDouble(-area, area);
                    double randomZ = new Random().nextDouble(-area, area);

                    LightningEffect.startLightningEffect(player, location.getBlock().getLocation().add(randomX, 0, randomZ));
                    if (i >= 5) {
                        this.cancel();
                    }
                }
            }.runTaskTimer(Main.getInstance(), 0L, 20L);


            Component hoverText = TextUtils.parse("§a15 plantações quebradas.");
            Component mainMessage = TextUtils.parse("§8(Passe o mouse para mais detalhes)")
                .hoverEvent(hoverText);

            Component fullMessage = TextUtils.parse(" <bold><#00aaaa>Tr<#02c9c9><#02c9c9>ovo<#02c9c9><#02c9c9>ada!<#00aaaa></bold> §8✈ §f§l+§a15§l✿ §7♦ §fBônus: §71.1§lx ")
                .append(mainMessage);

            player.sendMessage(fullMessage);


            player.showTitle(
                Title.title(
                    TextUtils.parse("<bold><#00aaaa>Tr<#02c9c9><#02c9c9>ovo<#02c9c9><#02c9c9>ada!<#00aaaa></bold>"),
                    TextUtils.parse("<#02c9c9>O encantamento foi ativado.<#02c9c9>"),
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
