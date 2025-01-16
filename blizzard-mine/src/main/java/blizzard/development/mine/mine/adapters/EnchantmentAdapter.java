package blizzard.development.mine.mine.adapters;

import blizzard.development.mine.mine.factory.EnchantmentFactory;
import blizzard.development.mine.utils.text.TextUtils;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.title.Title;
import org.bukkit.Location;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;

import java.time.Duration;
import java.util.concurrent.ThreadLocalRandom;

public class EnchantmentAdapter implements EnchantmentFactory {

    private static final EnchantmentAdapter instance = new EnchantmentAdapter();

    public static EnchantmentAdapter getInstance() {
        return instance;
    }

    @Override
    public void digger(Player player) {
        int digger = 10; // nível do encantamento

        double random = ThreadLocalRandom.current().nextDouble(0, 100);

        int area = 10; // tamanho da mina
        Location location = player.getLocation(); // pegar a localização do bloco que o player quebrou.
        player.getWorld().spawnEntity(location.getBlock().getLocation().add(0, 1, 0), EntityType.CHICKEN);

        if (random <= activation(digger)) {

            // lógica do DiggerManager aqui;

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
        int meteor = 10; // nível do encantamento

        double random = ThreadLocalRandom.current().nextDouble(0, 100);

        int area = 10; // tamanho da mina
        Location location = player.getLocation(); // pegar a localização do bloco que o player quebrou.
        player.getWorld().spawnEntity(location.getBlock().getLocation().add(0, 1, 0), EntityType.CHICKEN);

        if (random <= activation(meteor)) {

            // lógica do MeteorManager aqui;

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
        double base = 0.002;
        double increase = 0.005;

        double result = base + (increase * level);
        return Math.min(result, 100.0);
    }
}
