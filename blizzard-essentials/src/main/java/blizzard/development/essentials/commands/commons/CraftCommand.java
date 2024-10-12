package blizzard.development.essentials.commands.commons;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.Default;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

@CommandAlias("craft|workbench|craftar")
public class CraftCommand extends BaseCommand {

    @Default
    public void onCommand(CommandSender commandSender) {
        if (!(commandSender instanceof Player)) {
            return;
        }

        Player player = (Player) commandSender;

        player.openWorkbench(player.getLocation(), true);
    }
}
