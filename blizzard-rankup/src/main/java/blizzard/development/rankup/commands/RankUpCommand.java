package blizzard.development.rankup.commands;

import blizzard.development.rankup.inventories.ConfirmationInventory;
import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.CommandPermission;
import co.aikar.commands.annotation.Default;
import org.bukkit.entity.Player;

@CommandAlias("rankup")
@CommandPermission("rankup.use")
public class RankUpCommand extends BaseCommand {

    @Default
    public void onCommand(Player player) {
        if (player == null || !player.isOnline()) {
            return;
        }

        ConfirmationInventory.openConfirmationInventory(player);
    }
}
