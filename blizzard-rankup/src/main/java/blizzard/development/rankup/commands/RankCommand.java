package blizzard.development.rankup.commands;

import blizzard.development.rankup.database.cache.method.PlayersCacheMethod;
import blizzard.development.rankup.inventories.RankInventory;
import blizzard.development.rankup.utils.PluginImpl;
import blizzard.development.rankup.utils.RanksUtils;
import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.Default;
import org.bukkit.entity.Player;

@CommandAlias("rank")
public class RankCommand extends BaseCommand {

    @Default
    public void onCommand(Player player) {
        RankInventory.openRankInventory(player);
    }
}
