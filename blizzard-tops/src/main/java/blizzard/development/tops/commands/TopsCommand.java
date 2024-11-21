package blizzard.development.tops.commands;

import blizzard.development.tops.inventories.TopsInventory;
import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.CommandPermission;
import co.aikar.commands.annotation.Default;
import co.aikar.commands.annotation.Optional;
import org.bukkit.entity.Player;

@CommandAlias("tops")
public class TopsCommand extends BaseCommand {

    @Default
    @CommandPermission("blizzard.tops.basic")
    public void onCommand(Player player, @Optional String currencie) {
        TopsInventory.getInstance().open(player);
    }
}
