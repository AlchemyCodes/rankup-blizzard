package blizzard.development.essentials.commands.staff;

import blizzard.development.essentials.tasks.VanishTask;
import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.CommandPermission;
import co.aikar.commands.annotation.Default;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

@CommandAlias("vanish|invisivel|v")
public class VanishCommand extends BaseCommand {

    public static final List<Player> vanishedPlayers = new ArrayList<>();

    @Default
    @CommandPermission("alchemy.essentials.staff")
    public void onCommand(CommandSender commandSender) {
        if (!(commandSender instanceof Player)) {
            return;
        }

        Player player = (Player) commandSender;

        if (vanishedPlayers.contains(player)) {
            for (Player allPlayers : Bukkit.getOnlinePlayers()) {
                allPlayers.showPlayer(player);
            }

            VanishTask.cancel(player);
            vanishedPlayers.remove(player);
        } else {
            for (Player allPlayers : Bukkit.getOnlinePlayers()) {
                if (!allPlayers.hasPermission("alchemy.essentials.staff")) {
                    allPlayers.hidePlayer(player);
                }
            }

            VanishTask.create(player);
            vanishedPlayers.add(player);
        }
    }
}
