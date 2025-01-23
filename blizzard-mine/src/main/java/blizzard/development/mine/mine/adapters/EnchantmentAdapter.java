package blizzard.development.mine.mine.adapters;

import blizzard.development.mine.managers.enchantments.meteor.MeteorEffect;
import blizzard.development.mine.mine.enums.LocationEnum;
import blizzard.development.mine.mine.factory.EnchantmentFactory;
import blizzard.development.mine.utils.PluginImpl;
import blizzard.development.mine.utils.locations.LocationUtils;
import blizzard.development.mine.utils.packets.MinePacketUtils;
import blizzard.development.mine.utils.text.TextUtils;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.title.Title;
import org.bukkit.Location;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.time.Duration;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class EnchantmentAdapter implements EnchantmentFactory {

    private static final EnchantmentAdapter instance = new EnchantmentAdapter();
    public static EnchantmentAdapter getInstance() {
        return instance;
    }

    @Override
    public void digger(Player player) {
        int digger = 10;

        double random = ThreadLocalRandom.current().nextDouble(0, 100);

        int area = 32 - 5;

        double randomX = new Random().nextDouble(-area, area);
        double randomZ = new Random().nextDouble(-area, area);

        Location location = player.getLocation();

        int entityId = (int) Math.round(Math.random() * Integer.MAX_VALUE);

        if (random <= activation(digger)) {
            MinePacketUtils.getInstance()
                .sendEntityPacket(
                    location.getBlock().getLocation().add(randomX, 58, randomZ),
                    player,
                    EntityType.MINECART_TNT,
                    entityId
                );

            Component hoverText = TextUtils.parse("§a32 blocos quebrados.");
            Component mainMessage = TextUtils.parse("§8(Passe o mouse para mais detalhes)")
                .hoverEvent(hoverText);

            Component fullMessage = TextUtils.parse(" <bold><#d90404>Esc<#f71919><#f71919>av<#f71919><#f71919>ador!<#d90404></bold> §8✈ §f§l+§a32§l$ §7♦ §fBônus: §71.1§lx ")
                .append(mainMessage);

            player.sendMessage(fullMessage);


            player.showTitle(
                Title.title(
                    TextUtils.parse("<bold><#d90404>Esc<#f71919><#f71919>av<#f71919><#f71919>ador!<#d90404></bold>"),
                    TextUtils.parse("<#737373>O encantamento foi ativado.<#737373>"),
                    Title.Times.times(Duration.ZERO, Duration.ofSeconds(2), Duration.ofSeconds(1))
                )
            );
        }
    }

    @Override
    public void meteor(Player player) {
        int meteor = 10;

        double random = ThreadLocalRandom.current().nextDouble(0, 100);

        int area = 20 - 5;

        double randomX = new Random().nextDouble(-area, area);
        double randomZ = new Random().nextDouble(-area, area);

        Location location = LocationUtils.getLocation(LocationEnum.CENTER.getName());

        int entityId = (int) Math.round(Math.random() * Integer.MAX_VALUE);

        if (random <= activation(meteor)) {
            MinePacketUtils.getInstance()
                .sendEntityPacket(
                    location.getBlock().getLocation().add(randomX, 30, randomZ),
                    player,
                    EntityType.FIREBALL,
                    entityId
                );

            new BukkitRunnable() {
                int i = 0;
                @Override
                public void run() {
                    i++;

                    if (i == 2) {
                        MeteorEffect.startMeteorBreak(player, location.getBlock().getLocation().add(randomX, 0, randomZ), 3, 4, 3);
                        MinePacketUtils.getInstance()
                            .removeEntity(
                                player,
                                entityId
                            );

                        this.cancel();
                    }
                }
            }.runTaskTimer(PluginImpl.getInstance().plugin, 0L, 20L);

            Component hoverText = TextUtils.parse("§a25 blocos quebrados.");
            Component mainMessage = TextUtils.parse("§8(Passe o mouse para mais detalhes)")
                .hoverEvent(hoverText);

            Component fullMessage = TextUtils.parse(" <bold><#FFAA00>Me<#ffb624><#ffb624>teo<#ffb624><#ffb624>ro!<#FFAA00></bold> §7✈ §f§l+§a32§l$ §7♦ §fBônus: §71.1§lx ")
                .append(mainMessage);

            player.sendMessage(fullMessage);


            player.showTitle(
                Title.title(
                    TextUtils.parse("<bold><#FFAA00>Me<#ffb624><#ffb624>teo<#ffb624><#ffb624>ro!<#FFAA00></bold>"),
                    TextUtils.parse("<#FFAA00>O encantamento foi ativado.<#FFAA00>"),
                    Title.Times.times(Duration.ZERO, Duration.ofSeconds(2), Duration.ofSeconds(1))
                )
            );
        }
    }


    private double activation(int level) {
        double base = 0.002; //0.002
        double increase = 0.005;

        double result = base + (increase * level);
        return Math.min(result, 100.0);
    }
}
