package blizzard.development.essentials.commands.commons;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.Default;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.ArrayList;
import java.util.List;

@CommandAlias("luz|light|acender")
public class LightCommand extends BaseCommand {

    private final List<Player> lightPlayers = new ArrayList<>();
    @Default
    public void onCommand(CommandSender commandSender) {
        if (!(commandSender instanceof Player)) {
            return;
        }

        Player player = (Player) commandSender;

        if (lightPlayers.contains(player)) {
            player.removePotionEffect(PotionEffectType.NIGHT_VISION);
            lightPlayers.remove(player);
        } else {
            player.addPotionEffect(new PotionEffect(PotionEffectType.NIGHT_VISION, PotionEffect.INFINITE_DURATION, 1));
            player.sendActionBar("§b§lYAY! §bVocê ativou o modo visão noturna.");
            lightPlayers.add(player);
        }
    }
}
