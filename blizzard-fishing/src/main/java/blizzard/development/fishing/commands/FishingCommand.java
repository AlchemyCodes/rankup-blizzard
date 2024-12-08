package blizzard.development.fishing.commands;

import blizzard.development.fishing.inventories.FishingInventory;
import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.Default;
import org.bukkit.entity.Player;

@CommandAlias("pesca|pescar|pescca|peesca")
public class FishingCommand extends BaseCommand {

    @Default
    public void onCommand(Player player) {
        FishingInventory.openFishing(player);
    }
}
