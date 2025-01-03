package blizzard.development.monsters.commands.main;

import blizzard.development.monsters.inventories.cage.CageInventory;
import blizzard.development.monsters.listeners.monsters.MonstersWorldListener;
import blizzard.development.monsters.monsters.handlers.world.MonstersWorldHandler;
import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.CommandPermission;
import co.aikar.commands.annotation.Default;
import org.bukkit.entity.Player;

@CommandAlias("cage|cages|gaiola|gaiolas")
@CommandPermission("blizzard.monsters.basic")
public class CageCommand extends BaseCommand {

    @Default
    public void onCommand(Player player) {
        if (MonstersWorldHandler.getInstance().containsPlayer(player)) {
            player.sendActionBar("§c§lEI! §cVocê só pode utilizar isso estando fora do mundo de monstros. §7(/monstros sair)");
            return;
        }

        CageInventory.getInstance().open(player, 1);
    }
}
