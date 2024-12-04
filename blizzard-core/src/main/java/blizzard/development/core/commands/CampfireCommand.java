package blizzard.development.core.commands;

import blizzard.development.core.handler.FurnaceItemHandler;
import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.Default;
import org.bukkit.entity.Player;

@CommandAlias("fogueira")
public class CampfireCommand extends BaseCommand {

    @Default
    public void onCommand(Player player) {
        FurnaceItemHandler.addCampfire(player);
    }
}
