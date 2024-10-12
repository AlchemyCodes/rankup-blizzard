package blizzard.development.clans.commands.subcommands.staff;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.CommandPermission;
import co.aikar.commands.annotation.Subcommand;
import org.bukkit.entity.Player;
import java.util.HashMap;

@CommandAlias("clans|clan")
public class ChatSpyCommand extends BaseCommand {

    public static HashMap<Player, Boolean> chatSpyMap = new HashMap<>();

    @Subcommand("espiar")
    @CommandPermission("legacy.clans.admin")
    public void onCommand(Player player) {

        boolean isSpying = chatSpyMap.getOrDefault(player, false);

        if (isSpying) {
            chatSpyMap.remove(player);
            player.sendMessage("§cVocê desativou o modo espião de chat!");
        } else {
            player.sendMessage("§aVocê ativou o modo espião de chat!");
            chatSpyMap.put(player, !isSpying);
        }

    }
}
