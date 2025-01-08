package blizzard.development.plantations.managers.upgrades.xray;

import blizzard.development.plantations.database.cache.methods.ToolCacheMethod;
import blizzard.development.plantations.managers.AreaManager;
import blizzard.development.plantations.utils.LocationUtils;
import blizzard.development.plantations.utils.TextUtils;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.title.Title;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

import java.time.Duration;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class XrayManager {


    public static final ToolCacheMethod toolCacheMethod = ToolCacheMethod.getInstance();

    public static void check(Player player, Block block, String id) {
        int xray = toolCacheMethod.getXray(id);

        double random = ThreadLocalRandom.current().nextDouble(0, 100);

        int area = AreaManager.getInstance().getArea(player) - 7;
        Location location = LocationUtils.getCenterLocation();

        double randomX = new Random().nextDouble(-area, area);
        double randomZ = new Random().nextDouble(-area, area);

        if (random <= activation(xray)) {

            XrayEffect.startXrayEffect(player, LocationUtils.getCenterLocation().getBlock().getLocation().add(randomX, 0, randomZ));


            Component hoverText = TextUtils.parse("§a32 plantações quebradas.");
            Component mainMessage = TextUtils.parse("§8(Passe o mouse para mais detalhes)")
                .hoverEvent(hoverText);

            Component fullMessage = TextUtils.parse(" <bold><#555555>Ra<#737373><#737373>io-X!<#555555></bold> §7✈ §f§l+§a32§l✿ §7♦ §fBônus: §71.1§lx ")
                .append(mainMessage);

            player.sendMessage(fullMessage);


            player.showTitle(
                Title.title(
                    TextUtils.parse("<bold><#555555>Ra<#737373><#737373>io-X!<#555555></bold>"),
                    TextUtils.parse("<#737373>O encantamento foi ativado.<#737373>"),
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
