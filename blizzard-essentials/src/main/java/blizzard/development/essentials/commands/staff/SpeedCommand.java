package blizzard.development.essentials.commands.staff;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.*;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

@CommandAlias("speed|velocidade")
public class SpeedCommand extends BaseCommand {

    @Default
    @CommandPermission("alchemy.essentials.staff")
    @Syntax("<level> <segundos> <jogador>")
    public void onCommand(CommandSender commandSender, int level, int seconds, @Optional String playerTarget) {
        if (playerTarget == null) {
            if (!(commandSender instanceof Player)) {
                return;
            }

            Player player = (Player) commandSender;

            switch (seconds) {
                case 0:
                    player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, PotionEffect.INFINITE_DURATION, level));
                    player.sendActionBar("§b§lYAY! §bVocê agora está com o efeito SPEED por tempo ilimitado.");
                    break;
                default:
                    player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, seconds * 20, level));
                    player.sendActionBar("§b§lYAY! §bVocê agora está com o efeito SPEED por §l" + seconds + "§b segundos.");
            }
            return;
        }

        Player target = Bukkit.getPlayer(playerTarget);
        Player player = (Player) commandSender;
        if (target != null) {

            switch (seconds) {
                case 0:
                    target.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, PotionEffect.INFINITE_DURATION, level));
                    player.sendActionBar("§b§lYAY! §bVocê adicionou o efeito SPEED para o jogador §l" + target.getName() + "§b por tempo ilimitado!");

                    break;
                default:
                    player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, seconds * 20, level));
                    player.sendActionBar("§b§lYAY! §bVocê adicionou o efeito SPEED para o jogador §l" + target.getName() + "§b com sucesso!");
            }

        }
    }
}
