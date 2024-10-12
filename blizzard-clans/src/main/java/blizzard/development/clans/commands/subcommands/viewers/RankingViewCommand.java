package blizzard.development.clans.commands.subcommands.viewers;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.CommandPermission;
import co.aikar.commands.annotation.Subcommand;
import org.bukkit.entity.Player;
import blizzard.development.clans.inventories.secondary.ranking.MoneyRankingInventory;

@CommandAlias("clans|clan")
public class RankingViewCommand extends BaseCommand {

    @Subcommand("destaques")
    @CommandPermission("legacy.clans.basic")
    public void onCommand(Player player) {
        MoneyRankingInventory.open(player, 0);
    }
}
