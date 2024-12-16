package blizzard.development.spawners.commands.spawners.subcommands;

import blizzard.development.spawners.inventories.spawners.ranking.PurchasedInventory;
import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.CommandPermission;
import co.aikar.commands.annotation.Subcommand;
import org.bukkit.entity.Player;

@CommandAlias("spawners|spawner|geradores|gerador")
@CommandPermission("blizzard.spawners.basic")
public class RankingCommand extends BaseCommand {

    @Subcommand("ranking|tops|top|destaques|destaque")
    public void onCommand(Player player) {
        PurchasedInventory.getInstance().open(player);
    }
}
