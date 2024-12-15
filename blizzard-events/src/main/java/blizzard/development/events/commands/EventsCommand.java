package blizzard.development.events.commands;

import blizzard.development.events.inventories.EventsInventory;
import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.Default;
import org.bukkit.entity.Player;

@CommandAlias("events")
public class EventsCommand extends BaseCommand {

    @Default
    public void onCommand(Player player) {
        EventsInventory.openEventsInventory(player);
    }
}
