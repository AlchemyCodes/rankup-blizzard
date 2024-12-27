package blizzard.development.monsters.commands.main;

import blizzard.development.monsters.inventories.main.MonstersInventory;
import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.CommandPermission;
import co.aikar.commands.annotation.Default;
import org.bukkit.entity.Player;

@CommandAlias("monsters|monster|monstros|monstro|bosses|boss")
@CommandPermission("blizzard.monsters.basic")
public class MonstersCommand extends BaseCommand {

    @Default
    public void onCommand(Player player) {
        MonstersInventory.getInstance().open(player);
    }

}
