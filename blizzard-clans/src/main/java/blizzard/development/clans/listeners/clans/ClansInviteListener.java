package blizzard.development.clans.listeners.clans;

import blizzard.development.clans.utils.gradient.TextUtil;
import io.papermc.paper.event.player.AsyncChatEvent;
import net.kyori.adventure.text.serializer.plain.PlainTextComponentSerializer;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.scheduler.BukkitRunnable;
import blizzard.development.clans.database.dao.PlayersDAO;
import blizzard.development.clans.database.storage.PlayersData;
import blizzard.development.clans.enums.Roles;
import blizzard.development.clans.methods.ClansMethods;
import blizzard.development.clans.utils.PluginImpl;

import java.util.HashMap;
import java.util.Map;

public class ClansInviteListener implements Listener {

    public static final Map<Player, BukkitRunnable> inviteTimeouts = new HashMap<>();
    public static final Map<Player, String> pendingInvites = new HashMap<>();

    public static void addPendingInvite(Player player) {
        if (inviteTimeouts.containsKey(player)) {
            inviteTimeouts.get(player).cancel();
        }

        BukkitRunnable timeoutTask = new BukkitRunnable() {
            @Override
            public void run() {
                player.sendMessage("§cTempo para o convite expirou.");
                removePendingInvite(player);
            }
        };
        timeoutTask.runTaskLater(PluginImpl.getInstance().plugin, 20 * 30);
        inviteTimeouts.put(player, timeoutTask);
        pendingInvites.put(player, ClansMethods.getUserClan(player));
    }

    public static void removePendingInvite(Player player) {
        if (inviteTimeouts.containsKey(player)) {
            inviteTimeouts.get(player).cancel();
            inviteTimeouts.remove(player);
        }
        pendingInvites.remove(player);
    }

    @EventHandler(priority = EventPriority.HIGH)
    public void onPlayerChat(AsyncChatEvent event) {
        Player player = event.getPlayer();
        if (!inviteTimeouts.containsKey(player)) {
            return;
        }
        event.setCancelled(true);

        String cancelMessage = PlainTextComponentSerializer.plainText().serialize(event.message());
        if (cancelMessage.equalsIgnoreCase("cancelar")) {
            player.sendMessage(TextUtil.parse("§aVocê cancelou o convite com sucesso!"));
            removePendingInvite(player);
            return;
        }

        String playerName = PlainTextComponentSerializer.plainText().serialize(event.message());

        String clan = pendingInvites.get(player);
        if (clan == null) {
            player.sendMessage("§cVocê não pertence a nenhum clan!");
            removePendingInvite(player);
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

        removePendingInvite(player);
    }
}
