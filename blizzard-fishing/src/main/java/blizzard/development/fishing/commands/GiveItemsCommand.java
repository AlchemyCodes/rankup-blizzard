package blizzard.development.fishing.commands;

import blizzard.development.fishing.handlers.FishBucketHandler;
import blizzard.development.fishing.handlers.FishingNetHandler;
import blizzard.development.fishing.handlers.FishingRodHandler;
import blizzard.development.fishing.handlers.FurnaceItemHandler;
import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.Default;
import org.bukkit.entity.Player;

@CommandAlias("itenspesca")
public class GiveItemsCommand extends BaseCommand {

    @Default
    public void onCommand(Player player) {
        FishingRodHandler.setRod(player, 0);
        FishingNetHandler.setNet(player, 3);
        FurnaceItemHandler.setFurnace(player, 5);
        FishBucketHandler.setBucket(player, 8);
    }
}
