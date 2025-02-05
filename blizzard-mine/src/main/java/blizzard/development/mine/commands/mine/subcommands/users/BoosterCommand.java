package blizzard.development.mine.commands.mine.subcommands.users;

import blizzard.development.mine.inventories.booster.BoosterInventory;
import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.CommandPermission;
import co.aikar.commands.annotation.Subcommand;
import org.bukkit.entity.Player;


@CommandAlias("mina|mineracao|mine")
@CommandPermission("blizzard.mine.user")
public class BoosterCommand extends BaseCommand {

    @Subcommand("booster|boosters")
    public void onBoosterCommand(Player player) {
        new BoosterInventory().open(player);
    }
}
