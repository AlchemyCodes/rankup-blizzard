package blizzard.development.clans.commands.subcommands;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.*;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import blizzard.development.clans.database.dao.PlayersDAO;
import blizzard.development.clans.database.storage.PlayersData;
import blizzard.development.clans.enums.Roles;
import blizzard.development.clans.methods.ClansMethods;

@CommandAlias("clans|clan")
public class InviteCommand extends BaseCommand {

    @Subcommand("convidar")
    @CommandPermission("legacy.clans.basic")
    @CommandCompletion("@players")
    @Syntax("<jogador>")
    public void onCommand(Player player, String playerName) {
        String clan = ClansMethods.getUserClan(player);
        if (clan == null) {
            player.sendMessage("§cVocê não pertence a nenhum clan!");
            return;
        }

        PlayersDAO playersDAO = new PlayersDAO();
        PlayersData playersData = playersDAO.findPlayerDataByName(playerName);

        if (playersData == null || !playerName.equals(playersData.getNickname())) {
            player.sendMessage("§cO jogador informado não foi encontrado!");
            return;
        }

        String playerRole = ClansMethods.getUser(player).getRole();
        boolean isOwner = ClansMethods.isOwner(clan, player);
        boolean leader = playerRole.equals(Roles.LEADER.getName());
        boolean captain = playerRole.equals(Roles.CAPTAIN.getName());
        boolean reliable = playerRole.equals(Roles.RELIABLE.getName());

        if (!isOwner && !leader && !captain && !reliable) {
            player.sendMessage("§cVocê não tem permissão para convidar membros!");
            return;
        }

        if (playerName.equals(player.getName())) {
            player.sendMessage("§cVocê não pode se convidar para o clan!");
            return;
        }

        if (ClansMethods.getUserClanByName(playerName) != null) {
            player.sendMessage("§cO jogador informado já está em um clan!");
            return;
        }

        if (ClansMethods.hasInviteByName(clan, playerName)) {
            player.sendMessage("§cO jogador informado já está convidado para esse clan!");
            return;
        }

        if (ClansMethods.getInvitesCount(playerName) > 5) {
            player.sendMessage("§cO jogador informado atingiu o limite máximo de invites!");
            return;
        }

        ClansMethods.sendInviteByName(clan, playerName);
        player.sendMessage("§aVocê convidou o jogador §7" + playerName + "§a para o clan!");

        Player target = Bukkit.getPlayer(playerName);

        if (target != null) {
            String name = ClansMethods.getClan(clan).getName();
            String tag = ClansMethods.getClan(clan).getTag();

            target.sendMessage("");
            target.sendMessage(" §a§lWOW! §aVocê recebeu um convite para adentrar a um clan");
            target.sendMessage(" §7O clan §a" + name + " [" + tag + "]§7 está convidando");
            target.sendMessage(" §7você para adentrar ao clan.");
            target.sendMessage("");
            TextComponent message = new TextComponent(" §aPara aceitar o convite clique §a§lAQUI");
            message.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/clans convites"));
            target.spigot().sendMessage(message);
            target.sendMessage("");
        }
    }
}
