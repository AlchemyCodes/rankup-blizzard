package blizzard.development.clans.commands.subcommands;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.CommandPermission;
import co.aikar.commands.annotation.Subcommand;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import blizzard.development.clans.methods.ClansMethods;
import blizzard.development.clans.database.storage.ClansData;
import blizzard.development.clans.utils.gradient.TextUtil;

@CommandAlias("clans|clan")
public class DisbandCommand extends BaseCommand {

    @Subcommand("desfazer")
    @CommandPermission("legacy.clans.basic")
    public void onCommand(Player player) {

        String clan = ClansMethods.getUserClan(player);
        if (clan == null) {
            player.sendMessage("§cVocê não pertence a nenhum clan!");
            return;
        }

        Boolean isOwner = ClansMethods.isOwner(clan, player);
        if (!isOwner) {
            player.sendMessage("§cVocê não é dono desse clan!");
            return;
        }

        ClansData clanData = ClansMethods.getClan(clan);

        if (clanData.getMoney() > 0) {
            player.sendMessage("§cVocê precisa tirar o dinheiro do banco antes de desfazer o clan!");
            return;
        }

        String name = clanData.getName();
        String tag = clanData.getTag();

        for (Player broadcast : Bukkit.getOnlinePlayers()) {
            broadcast.sendActionBar(
                    TextUtil.parse("<bold><#ff0000> YAY! <#ff0000></bold><#ff0000>O clan " + name + " [" + tag + "] foi deletado. <#ff5555>"));
        }

        player.sendMessage("§aVocê desfez o clan §7" + name + "§a!");

        ClansMethods.deleteClan(clan);

    }
}
