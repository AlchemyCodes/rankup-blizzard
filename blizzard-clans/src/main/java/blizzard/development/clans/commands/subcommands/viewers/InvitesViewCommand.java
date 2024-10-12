package blizzard.development.clans.commands.subcommands.viewers;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.CommandPermission;
import co.aikar.commands.annotation.Subcommand;
import org.bukkit.entity.Player;
import blizzard.development.clans.inventories.secondary.InvitesInventory;

@CommandAlias("clans|clan")
public class InvitesViewCommand extends BaseCommand {

    @Subcommand("convites")
    @CommandPermission("legacy.clans.basic")
    public void onCommand(Player player) {
        InvitesInventory.open(player);
    }
}
