package blizzard.development.clans.commands.chat;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.CommandPermission;
import co.aikar.commands.annotation.Default;
import co.aikar.commands.annotation.Syntax;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import blizzard.development.clans.commands.subcommands.staff.ChatSpyCommand;
import blizzard.development.clans.managers.LPerms;
import blizzard.development.clans.methods.ClansMethods;

import java.util.List;
import java.util.Objects;

@CommandAlias("c|.")
public class ClansChatCommand extends BaseCommand {

    @Default
    @CommandPermission("legacy.clans.basic")
    @Syntax("<mensagem>")
    public void onCommand(Player player, String[] args) {

        String userClan = ClansMethods.getUserClan(player);

        if (userClan == null) {
            player.sendMessage("§cVocê não está em nenhum clan!");
            return;
        }

        String message = String.join(" ", args);

        if (message.isEmpty() || message.length() > 100) {
            player.sendMessage(Component.text("§cA mensagem que você está tentando enviar é longa ou curta demais."));
            return;
        }

        if (player.hasPermission("players.chat.color")) {
            message = message.replace("&a", "§a").replace("&b", "§b").replace("&c", "§c").replace("&d", "§d")
                    .replace("&e", "§e").replace("&f", "§f").replace("&0", "§0").replace("&1", "§1").replace("&2", "§2")
                    .replace("&3", "§3").replace("&4", "§4").replace("&5", "§5").replace("&6", "§6").replace("&7", "§7")
                    .replace("&8", "§8").replace("&9", "§9");
        }

        Component formattedMessage = Component.text("§a[C] ")
                .append(Component.text("§7[" + ClansMethods.getClan(userClan).getTag() + "] "))
                .append(Objects.requireNonNull(LPerms.getPrefix(player)))
                .append(Component.text(ClansMethods.getRoleEmojiByName(player) + " "))
                .append(Component.text("§f" + player.getName() + "§f: §7" + message));

        Component spyFormattedMessage = Component.text("§c[SPY] ")
                .append(Component.text("§a[C] "))
                .append(Component.text("§7[" + ClansMethods.getClan(userClan).getTag() + "] "))
                .append(Objects.requireNonNull(LPerms.getPrefix(player)))
                .append(Component.text(ClansMethods.getRoleEmojiByName(player) + " "))
                .append(Component.text("§f" + player.getName() + "§f: §7" + message));

        List<String> members = ClansMethods.getMembers(userClan);

        for (Player players : Bukkit.getOnlinePlayers()) {
            if (members.contains(players.getName())) {
                players.sendMessage(formattedMessage);
            }
            if (ChatSpyCommand.chatSpyMap.containsKey(players)) {
                players.sendMessage(spyFormattedMessage);
            }
        }
    }
}

