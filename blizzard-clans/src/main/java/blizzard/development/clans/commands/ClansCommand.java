package blizzard.development.clans.commands;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.CommandPermission;
import co.aikar.commands.annotation.Default;
import org.bukkit.entity.Player;
import blizzard.development.clans.database.storage.PlayersData;
import blizzard.development.clans.inventories.primary.ClansInventory;
import blizzard.development.clans.inventories.primary.DefaultInventory;
import blizzard.development.clans.methods.ClansMethods;

@CommandAlias("clans|clan")
public class ClansCommand extends BaseCommand {

    @Default
    @CommandPermission("legacy.clans.basic")
    public void onCommand(Player player) {

        PlayersData data = ClansMethods.getUser(player);

        if (data == null) {
            player.sendMessage("§cOcorreu um erro, entre em contato com um administrador!");
            return;
        }

        Boolean hasClan = data.getClan() != null;

        if (hasClan) {
            ClansInventory.open(player);
        } else {
            DefaultInventory.open(player);
        }

    }
}
