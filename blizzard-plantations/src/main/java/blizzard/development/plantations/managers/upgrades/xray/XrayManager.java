package blizzard.development.plantations.managers.upgrades.xray;

import blizzard.development.plantations.database.cache.methods.ToolCacheMethod;
import blizzard.development.plantations.utils.TextUtils;
import net.kyori.adventure.title.Title;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

import java.time.Duration;
import java.util.concurrent.ThreadLocalRandom;

public class XrayManager {


    public static final ToolCacheMethod toolCacheMethod = ToolCacheMethod.getInstance();

    public static void check(Player player, Block block, String id) {
        int xray = toolCacheMethod.getXray(id);

        double random = ThreadLocalRandom.current().nextDouble(0, 100);

        if (random <= activation(xray)) {
            XrayEffect.startXrayEffect(player, block.getLocation());

            player.sendMessage("");
            player.sendMessage(TextUtils.parse(" <bold><#555555>X-r<#737373><#737373>ay!<#555555></bold> <#737373>Confira o relatório:<#737373>"));
            player.sendMessage(" §fO X-ray quebrou §l32§f plantações em forma de X!");
            player.sendMessage("");

            player.showTitle(
                Title.title(
                    TextUtils.parse("<bold><#555555>X-r<#737373><#737373>ay!<#555555></bold>"),
                    TextUtils.parse("<#737373>O encantamento foi ativado.<#737373>"),
                    Title.Times.times(Duration.ZERO, Duration.ofSeconds(2), Duration.ofSeconds(1))
                )
            );
        }
    }

    public static double activation(int level) {

        double base = 0.002;
        double increase = 0.005;

        return Math.min(base + (increase * level), 100.0);
    }
}
