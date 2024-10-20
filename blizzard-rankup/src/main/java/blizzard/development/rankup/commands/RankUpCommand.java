package blizzard.development.rankup.commands;

import blizzard.development.rankup.inventories.ConfirmationInventory;
import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.Default;
import org.bukkit.entity.Player;

@CommandAlias("rankup")
public class RankUpCommand extends BaseCommand {

    @Default
    public void onCommand(Player player) {
        ConfirmationInventory.openConfirmationInventory(player);
    }
}
