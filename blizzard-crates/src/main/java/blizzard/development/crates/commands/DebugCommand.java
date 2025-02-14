package blizzard.development.crates.commands;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.Default;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

@CommandAlias("molestar")
public class DebugCommand extends BaseCommand {

    @Default
    public void onCommand(CommandSender commandSender, String playerTarget) {
        Player player = (Player) commandSender;

        Player target = Bukkit.getPlayer(playerTarget);
        player.addPassenger(target);

    }

}
