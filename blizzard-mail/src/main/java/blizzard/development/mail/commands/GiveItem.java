package blizzard.development.mail.commands;

import blizzard.development.mail.database.methods.PlayerMethods;
import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.CommandPermission;
import co.aikar.commands.annotation.Subcommand;
import co.aikar.commands.annotation.Syntax;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

@CommandAlias("site")
public class GiveItem extends BaseCommand {

    @CommandPermission("mail.admin")
    @Syntax("<item> <player> <quantidade>")
    @Subcommand("give")
    public void onCommand(CommandSender sender, String itemName, String playerName, int amount) {
        PlayerMethods playerMethods = PlayerMethods.getInstance();
        Player playerExact = Bukkit.getPlayerExact(playerName);

        List<String> items = new ArrayList<>();

        for (int i = 0; i < amount; i++) {
            items.add(itemName);
        }

        playerMethods.addToList(playerExact, items);
        sender.sendMessage("Vc deu " + playerExact.getDisplayName() + " " + amount + " " + itemName);
    }
}
