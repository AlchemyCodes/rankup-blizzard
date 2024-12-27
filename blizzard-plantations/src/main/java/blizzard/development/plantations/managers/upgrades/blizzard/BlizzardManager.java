package blizzard.development.plantations.managers.upgrades.blizzard;

import blizzard.development.plantations.Main;
import blizzard.development.plantations.database.cache.methods.ToolCacheMethod;
import blizzard.development.plantations.managers.AreaManager;
import blizzard.development.plantations.utils.TextUtils;
import net.kyori.adventure.title.Title;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.time.Duration;
import java.util.concurrent.ThreadLocalRandom;

public class BlizzardManager {

    public static final ToolCacheMethod toolCacheMethod = ToolCacheMethod.getInstance();

    public static void check(Player player, String id) {
        int blizzard = toolCacheMethod.getBlizzard(id);

        double random = ThreadLocalRandom.current().nextDouble(0, 100);

        if (BlizzardEffect.getInstance().blizzard.containsKey(player)) return;

        if (random <= activation(blizzard)) {
            new BukkitRunnable() {
                int i = 0;
                @Override
                public void run() {
                    i++;

                    BlizzardEffect.getInstance()
                            .startBlizzardEffect(player);

                    if (i >= 10) {
                        BlizzardEffect.getInstance()
                            .stopBlizzardEffect(player);
                        this.cancel();
                    }
                }
            }.runTaskTimer(Main.getInstance(), 0L, 20L);

            player.sendMessage("");
            player.sendMessage(TextUtils.parse(" <bold><#55FFFF>Ne<#72f7f7><#72f7f7>vas<#72f7f7><#72f7f7>ca!<#55FFFF></bold> <#72f7f7>Confira o relatório:<#72f7f7>"));
            player.sendMessage(" §fGanhe um bônus a cada plantação quebrada.");
            player.sendMessage("");

            player.showTitle(
                Title.title(
                    TextUtils.parse("<bold><#55FFFF>Ne<#72f7f7><#72f7f7>vas<#72f7f7><#72f7f7>ca!<#55FFFF></bold>"),
                    TextUtils.parse("<#72f7f7>O encantamento foi ativado.<#72f7f7>"),
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
