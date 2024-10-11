package blizzard.development.core.commands.essentials;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.*;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

@CommandAlias("ping")
public class PingCommand extends BaseCommand {

    @Default
    @CommandPermission("alchemy.core.basic")
    @Syntax("<player>")
    public void onDefault(CommandSender sender, @Optional String target) {
        if (target == null) {
            if (!(sender instanceof Player)) {
                sender.sendMessage("§cApenas jogadores podem utilizar este comando!");
                return;
            }

            Player player = (Player) sender;
            sender.sendMessage("§7Seu ping: §a" + player.getPing());
            return;
        }

        Player targetPlayer = Bukkit.getPlayer(target);
        if (targetPlayer != null) {
            sender.sendMessage("§7O ping do jogador " + targetPlayer.getName() + " é: §a" + targetPlayer.getPing());
        } else {
            sender.sendMessage("§7O jogador fornecido está offline ou é inválido!");
        }
    }
}
