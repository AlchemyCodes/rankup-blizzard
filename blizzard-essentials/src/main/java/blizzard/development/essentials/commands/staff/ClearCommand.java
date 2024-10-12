package blizzard.development.essentials.commands.staff;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.*;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

@CommandAlias("clear|limpar")
public class ClearCommand extends BaseCommand {

    @Default
    @CommandPermission("alchemy.essentials.staff")
    @Syntax("<jogador>")
    public void onCommand(CommandSender commandSender, @Optional String playerTarget) {

        if (playerTarget == null) {
            if (!(commandSender instanceof Player)) {
                commandSender.sendMessage("§cApenas jogadores podem utilizar este comando!");
                return;
            }

            Player player = (Player) commandSender;
            player.getInventory().clear();
            player.sendActionBar("§b§lYAY! §bVocê limpou o seu inventário.");

            return;
        }

        Player target = Bukkit.getPlayer(playerTarget);
        Player player = (Player) commandSender;

        if (target != null) {

            target.getInventory().clear();
            player.sendActionBar("§b§lYAY! §bVocê limpou o inventário do jogador " + player.getName() + "!");
        }
    }
}
