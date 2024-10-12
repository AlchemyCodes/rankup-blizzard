package blizzard.development.essentials.commands.staff;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.Default;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

@CommandAlias("tpall|tptodos|puxar")
public class TpallCommand extends BaseCommand {

    @Default
    public void onCommand(CommandSender commandSender) {
        if (!(commandSender instanceof Player)) {
            return;
        }

        Player player = (Player) commandSender;

        for (Player allPlayers : Bukkit.getOnlinePlayers()) {
            allPlayers.teleport(player.getLocation());
            player.sendActionBar("§b§lYAY! §bVocê teleportou todos os jogadores até você.");
        }
    }
}
