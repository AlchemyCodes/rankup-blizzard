package blizzard.development.spawners.commands.slaughterhouses;

import blizzard.development.spawners.inventories.slaughterhouses.views.SlaughterhouseInventory;
import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.CommandPermission;
import co.aikar.commands.annotation.Default;
import org.bukkit.entity.Player;

@CommandAlias("slaughterhouses|slaughterhouse|abatedouros|abatedouro|matadouros|matadouro")
@CommandPermission("blizzard.spawners.basic")
public class SlaughterhousesCommand extends BaseCommand {

    @Default
    public void onCommand(Player player) {
        SlaughterhouseInventory.getInstance().open(player);
    }
}
