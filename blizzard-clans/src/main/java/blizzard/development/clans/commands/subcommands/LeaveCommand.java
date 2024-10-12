package blizzard.development.clans.commands.subcommands;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.CommandPermission;
import co.aikar.commands.annotation.Subcommand;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import blizzard.development.clans.methods.ClansMethods;
import blizzard.development.clans.utils.gradient.TextUtil;

import java.util.List;

@CommandAlias("clans|clan")
public class LeaveCommand extends BaseCommand {

    @Subcommand("sair")
    @CommandPermission("legacy.clans.basic")
    public void onCommand(Player player) {

        String clan = ClansMethods.getUserClan(player);

        if (clan == null) {
            player.sendMessage("§cVocê não está em nenhum clan!");
            return;
        }

        Boolean owner = ClansMethods.isOwner(clan, player);
        String name = ClansMethods.getClan(clan).getName();

        if (owner) {
            player.sendMessage("§cVocê não pode sair de um clan sendo dono, desfaça-o antes!");
            return;
        }

        player.sendMessage("§aVocê saiu do clan §7" + name + "§a com sucesso!");
        ClansMethods.leaveClan(clan, player);

        List<String> members = ClansMethods.getMembers(clan);

        for (Player players : Bukkit.getOnlinePlayers()) {
            if (members.contains(players.getName())) {
                players.sendMessage(
                        TextUtil.parse("<#ff0000> [<#b81414>-<#b81414>] <#ff0000> <#ff0000>O jogador " + player.getName() + " saiu do clan. <#ec5353>"));
            }
        }
    }
}
