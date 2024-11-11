package blizzard.development.essentials.commands.staff;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.*;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

@CommandAlias("feed|comer|fome")
public class FeedCommand extends BaseCommand {

    @Default
    @CommandPermission("alchemy.essentials.staff")
    @Syntax("<jogador>")
    public void onCommand(CommandSender commandSender, @Optional String playerTarget) {

        if (playerTarget == null) {
            if (!(commandSender instanceof Player)) {
                return;
            }

            Player player = (Player) commandSender;
            player.setFoodLevel(20);
            player.setSaturation(20);
            player.sendActionBar("§b§lYAY! §bVocê matou a sua fome.");

            return;
        }

        Player target = Bukkit.getPlayer(playerTarget);
        Player player = (Player) commandSender;

        if (target != null) {

            target.setFoodLevel(20);
            target.setSaturation(20);
            player.sendActionBar("§b§lYAY! §bVocê matou a fome do jogador " + player.getName() + ".");
        }
    }
}
