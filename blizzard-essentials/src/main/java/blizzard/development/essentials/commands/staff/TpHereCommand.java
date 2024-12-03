package blizzard.development.essentials.commands.staff;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.CommandCompletion;
import co.aikar.commands.annotation.CommandPermission;
import co.aikar.commands.annotation.Default;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

@CommandAlias("tphere|puxar")
public class TpHereCommand extends BaseCommand {

    @Default
    @CommandPermission("alchemy.essentials.staff")
    @CommandCompletion("@players")
    public void onCommand(CommandSender commandSender, String playerTarget) {

        Player player = (Player) commandSender;
        Player target = Bukkit.getPlayer(playerTarget);

        if (target == null) {
            player.sendActionBar("§c§lEI! §cO jogador §f´§7" + playerTarget + "§f´§c não existe.");
            return;
        }

        if (target.equals(player)) {
            player.sendActionBar("§c§lEI! §cVocê não pode se auto puxar.");
            return;
        }

        player.sendActionBar("§a§lYAY! §aVocê puxou o jogador " + target.getName() + "§a até você.");

        target.teleport(player);
    }
}
