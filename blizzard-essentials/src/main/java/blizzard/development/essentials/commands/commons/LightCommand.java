package blizzard.development.essentials.commands.commons;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.Default;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

@CommandAlias("luz|light|acender")
public class LightCommand extends BaseCommand {

    @Default
    public void onCommand(CommandSender commandSender) {
        if (!(commandSender instanceof Player)) {
            return;
        }

        Player player = (Player) commandSender;

        player.addPotionEffect(new PotionEffect(PotionEffectType.NIGHT_VISION, PotionEffect.INFINITE_DURATION, 1));
        player.sendActionBar("§b§lYAY! §bVocê ativou o modo visão noturna.");
    }
}
