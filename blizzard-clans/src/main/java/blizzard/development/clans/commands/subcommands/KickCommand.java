package blizzard.development.clans.commands.subcommands;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.*;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import blizzard.development.clans.database.cache.ClansCacheManager;
import blizzard.development.clans.database.cache.PlayersCacheManager;
import blizzard.development.clans.enums.Roles;
import blizzard.development.clans.methods.ClansMethods;
import blizzard.development.clans.utils.gradient.TextUtil;

import java.util.List;

@CommandAlias("clans|clan")
public class KickCommand extends BaseCommand {

    @Subcommand("expulsar")
    @CommandPermission("legacy.clans.basic")
    @CommandCompletion("@players")
    @Syntax("<jogador>")
    public void onCommand(Player player, String member) {

        String clan = ClansMethods.getUserClan(player);

        if (clan == null) {
            player.sendMessage("§cVocê não pertence a nenhum clan!");
            return;
        }

        String playerRole = ClansMethods.getUser(player).getRole();
        String clanOwner = ClansMethods.getClan(clan).getOwner();

        Boolean isOwner = ClansMethods.isOwner(clan, player);
        Boolean leader = playerRole.equals(Roles.LEADER.getName());
        Boolean captain = playerRole.equals(Roles.CAPTAIN.getName());

        if (!isOwner && !leader && !captain) {
            player.sendMessage("§cVocê não tem permissão para expulsar membros!");
            return;
        }

        if (!ClansCacheManager.isMemberInClan(clan, member)) {
            player.sendMessage("§cO jogador informado não está no clan!");
            return;
        }

        if (member.equals(player.getName())) {
            player.sendMessage("§cVocê não pode se expulsar!");
            return;
        }

        if (member.equals(clanOwner)) {
            player.sendMessage("§cVocê não pode expulsar o dono do clan!");
            return;
        }

        String memberRole = PlayersCacheManager.getPlayerDataByName(member).getRole();

        int playerRolePriority = Roles.fromName(playerRole).getPriority();
        int memberRolePriority = Roles.fromName(memberRole).getPriority();

        if (playerRolePriority <= memberRolePriority && !isOwner) {
            player.sendMessage("§cVocê não pode expulsar um membro com cargo maior ou igual ao seu!");
            return;
        }

        ClansMethods.leaveClanByName(clan, member);
        player.sendMessage("§aVocê expulsou o jogador §7" + member + "§a do clan!");

        List<String> members = ClansMethods.getMembers(clan);

        for (Player players : Bukkit.getOnlinePlayers()) {
            if (members.contains(players.getName())) {
                players.sendMessage(
                        TextUtil.parse("<#ff0000> [<#b81414>-<#b81414>] <#ff0000> <#ff0000>O jogador " + member + " foi retirado do clan. <#ec5353>"));
            }
        }

        Player clanMember = Bukkit.getPlayer(member);

        if (clanMember != null) {
            clanMember.sendMessage("§aVocê foi expulso do clan §7" + clan + "§a!");
        }
    }
}
