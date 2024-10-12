package blizzard.development.clans.commands.subcommands.staff;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.*;
import org.bukkit.entity.Player;
import blizzard.development.clans.methods.ClansMethods;
import blizzard.development.clans.database.storage.ClansData;

@CommandAlias("clans|clan")
public class ForceJoinCommand extends BaseCommand {

    @Subcommand("entrar")
    @CommandPermission("legacy.clans.admin")
    @CommandCompletion("@clans")
    @Syntax("<clan>")
    public void onCommand(Player player, String clan) {

        String userClan = ClansMethods.getUserClan(player);

        ClansData data = ClansMethods.getClan(clan);

        if (data != null) {
            String clanCase = data.getClan();

            if (!clanCase.equals(clan)) {
                player.sendMessage("§cO clã informado não existe!");
                return;
            }

            if (userClan != null) {

                if (userClan.equals(clan)) {
                    player.sendMessage("§cVocê já está nesse clã!");
                    return;
                }

                if (ClansMethods.isOwner(userClan, player)) {
                    ClansMethods.deleteClan(userClan);
                } else {
                    ClansMethods.leaveClan(userClan, player);
                }
            }

            ClansMethods.joinClanIfStaff(clan, player);
            player.sendMessage("§aVocê entrou no clã §7" + clan + "§a com sucesso!");

        } else {
            player.sendMessage("§cO clã informado não existe!");
        }
    }
}
