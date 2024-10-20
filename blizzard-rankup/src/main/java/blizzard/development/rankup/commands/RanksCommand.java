package blizzard.development.rankup.commands;

import blizzard.development.rankup.inventories.RanksInventory;
import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.Default;
import org.bukkit.entity.Player;

@CommandAlias("ranks")
public class RanksCommand extends BaseCommand {

    @Default
    public void onCommand(Player player) {
        RanksInventory.openRanksInventory(player);
    }
}
