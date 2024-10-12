package blizzard.development.essentials.commands.essentials;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.*;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

@CommandAlias("heal|feed")
public class HealCommand extends BaseCommand {

    @Default
    @CommandPermission("alchemy.core.admin")
    @Syntax("<player>")
    public void onDefault(CommandSender sender, @Optional String target) {
        if (target == null) {
            if (!(sender instanceof Player)) {
                sender.sendMessage("§cApenas jogadores podem utilizar este comando!");
                return;
            }

            Player player = (Player) sender;
            player.setHealth(20);
            player.setFoodLevel(20);
            sender.sendMessage("§7Sua vida e fome foram regeneradas!");
            return;
        }

        Player targetPlayer = Bukkit.getPlayer(target);
        if (targetPlayer != null) {
            targetPlayer.setHealth(20);
            targetPlayer.setFoodLevel(20);
            sender.sendMessage("§7A vida e fome do jogador " + targetPlayer.getName() + " foram regeneradas!");
        } else {
            sender.sendMessage("§7O jogador fornecido está offline ou é inválido!");
        }
    }
}
