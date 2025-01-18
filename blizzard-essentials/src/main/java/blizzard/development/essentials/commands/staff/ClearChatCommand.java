package blizzard.development.essentials.commands.staff;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.CommandPermission;
import co.aikar.commands.annotation.Default;
import co.aikar.commands.annotation.Subcommand;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

@CommandAlias("clearchat|cc|limparchat")
public class ClearChatCommand extends BaseCommand {

    @Default
    @CommandPermission("alchemy.essentials.staff")
    public void onCommand(CommandSender commandSender) {

        Player sender = (Player) commandSender;

        for (Player player : Bukkit.getOnlinePlayers()) {
            for (int i = 0; i < 1000; i++) {
                player.sendMessage("\n");
                sender.sendActionBar("§b§lYAY! §bVocê limpou o chat com sucesso!");
            }
        }
    }
}
