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
public class RolesCommand extends BaseCommand {

    @Subcommand("promover")
    @CommandPermission("legacy.clans.basic")
    @CommandCompletion("@players")
    @Syntax("<jogador>")
    public void onPromote(Player player, String target) {

        String clan = ClansMethods.getUserClan(player);

        if (clan == null) {
            player.sendMessage("§cVocê não pertence a nenhum clan!");
            return;
        }

        String playerRole = ClansMethods.getUser(player).getRole();
        String clanOwner = ClansMethods.getClan(clan).getOwner();

        Boolean isOwner = ClansMethods.isOwner(clan, player);
        Boolean isLeader = playerRole.equals(Roles.LEADER.getName());
        Boolean isCaptain = playerRole.equals(Roles.CAPTAIN.getName());

        if (!isOwner && !isLeader && !isCaptain) {
            player.sendMessage("§cVocê não tem permissão para promover membros!");
            return;
        }

        if (!ClansCacheManager.isMemberInClan(clan, target)) {
            player.sendMessage("§cO jogador informado não está no clan!");
            return;
        }

        String memberRole = PlayersCacheManager.getPlayerDataByName(target).getRole();

        Boolean leader = memberRole.equals(Roles.LEADER.getName());
        Boolean captain = memberRole.equals(Roles.CAPTAIN.getName());
        Boolean reliable = memberRole.equals(Roles.RELIABLE.getName());
        Boolean member = memberRole.equals(Roles.MEMBER.getName());

        if (target.equals(player.getName())) {
            player.sendMessage("§cVocê não pode se promover!");
            return;
        }

        if (target.equals(clanOwner)) {
            player.sendMessage("§cVocê não pode gerenciar o dono do clan!");
            return;
        }

        int playerRolePriority = Roles.fromName(playerRole).getPriority();
        int memberRolePriority = Roles.fromName(memberRole).getPriority();

        if (playerRolePriority <= memberRolePriority && !isOwner) {
            player.sendMessage("§cVocê não pode promover um membro com cargo maior ou igual ao seu!");
            return;
        }

        if (isLeader && captain && !isOwner) {
            player.sendMessage("§cVocê não pode promover um Capitão para Líder!");
            return;
        }

        if (isCaptain && reliable && !isOwner) {
            player.sendMessage("§cVocê não pode promover um Confiável para Capitão!");
            return;
        }

        if (member) {
            PlayersCacheManager.setRole(target, Roles.RELIABLE.getName());
            player.sendMessage("§aVocê promoveu o jogador §7" + target + "§a para §7Confiável§a!");
            promoteMessage(player, target, "Confiável");
        } else if (reliable) {
            PlayersCacheManager.setRole(target, Roles.CAPTAIN.getName());
            player.sendMessage("§aVocê promoveu o jogador §7" + target + "§a para §7Capitão§a!");
            promoteMessage(player, target, "Capitão");
        } else if (captain) {
            PlayersCacheManager.setRole(target, Roles.LEADER.getName());
            player.sendMessage("§aVocê promoveu o jogador §7" + target + "§a para §7Líder§a!");
            promoteMessage(player, target, "Líder");
        } else if (leader) {
            player.sendMessage("§aO jogador §7" + target + "§a já está em cargo máximo§a!");
        } else {
            player.sendMessage("§aO jogador §7" + target + "§a já está em cargo máximo§a!");
        }
    }

    @Subcommand("rebaixar")
    @CommandCompletion("@players")
    @Syntax("<jogador>")
    public void onDemote(Player player, String target) {

        String clan = ClansMethods.getUserClan(player);

        if (clan == null) {
            player.sendMessage("§cVocê não pertence a nenhum clan!");
            return;
        }

        String playerRole = ClansMethods.getUser(player).getRole();
        String clanOwner = ClansMethods.getClan(clan).getOwner();

        Boolean isOwner = ClansMethods.isOwner(clan, player);
        Boolean isLeader = playerRole.equals(Roles.LEADER.getName());
        Boolean isCaptain = playerRole.equals(Roles.CAPTAIN.getName());

        if (!isOwner && !isLeader && !isCaptain) {
            player.sendMessage("§cVocê não tem permissão para rebaixar membros!");
            return;
        }

        if (!ClansCacheManager.isMemberInClan(clan, target)) {
            player.sendMessage("§cO jogador informado não está no clan!");
            return;
        }

        String memberRole = PlayersCacheManager.getPlayerDataByName(target).getRole();

        Boolean leader = memberRole.equals(Roles.LEADER.getName());
        Boolean captain = memberRole.equals(Roles.CAPTAIN.getName());
        Boolean reliable = memberRole.equals(Roles.RELIABLE.getName());
        Boolean member = memberRole.equals(Roles.MEMBER.getName());

        if (target.equals(player.getName())) {
            player.sendMessage("§cVocê não pode se rebaixar!");
            return;
        }

        if (target.equals(clanOwner)) {
            player.sendMessage("§cVocê não pode gerenciar o dono do clan!");
            return;
        }

        int playerRolePriority = Roles.fromName(playerRole).getPriority();
        int memberRolePriority = Roles.fromName(memberRole).getPriority();

        if (playerRolePriority <= memberRolePriority && !isOwner) {
            player.sendMessage("§cVocê não pode rebaixar um membro com cargo maior ou igual ao seu!");
            return;
        }

        if (member) {
            player.sendMessage("§aO jogador §7" + target + "§a já está no menor cargo§a!");
        } else if (reliable) {
            PlayersCacheManager.setRole(target, Roles.MEMBER.getName());
            player.sendMessage("§aVocê rebaixou o jogador §7" + target + "§a para §7Membro§a!");
            demoteMessage(player, target, "Membro");
        } else if (captain) {
            PlayersCacheManager.setRole(target, Roles.RELIABLE.getName());
            player.sendMessage("§aVocê rebaixou o jogador §7" + target + "§a para §7Confiável§a!");
            demoteMessage(player, target, "Confiável");
        } else if (leader) {
            PlayersCacheManager.setRole(target, Roles.CAPTAIN.getName());
            player.sendMessage("§aVocê rebaixou o jogador §7" + target + "§a para §7Capitão§a!");
            demoteMessage(player, target, "Capitão");
        } else {
            player.sendMessage("§aO jogador §7" + target + "§a já está no menor cargo§a!");
        }
    }

    public static void promoteMessage(Player player, String target, String role) {
        String clan = ClansMethods.getUserClan(player);

        if (clan != null) {
            List<String> members = ClansMethods.getMembers(clan);

            for (Player players : Bukkit.getOnlinePlayers()) {
                if (members.contains(players.getName())) {
                    players.sendMessage(
                            TextUtil.parse("<#469536> [<#469536>><#469536>] <#469536> <#469536>O jogador " + target + " foi promovido para " + role + " <#55ff55>")
                    );
                }
            }
        }

    }

    public static void demoteMessage(Player player, String target, String role) {
        String clan = ClansMethods.getUserClan(player);

        if (clan != null) {
            List<String> members = ClansMethods.getMembers(clan);

            for (Player players : Bukkit.getOnlinePlayers()) {
                if (members.contains(players.getName())) {
                    players.sendMessage(
                            TextUtil.parse("<#ff0000> [<#b81414><<#b81414>] <#ff0000> <#ff0000>O jogador " + target + " foi rebaixado para " + role + " <#ec5353>")
                    );
                }
            }
        }

    }
}
