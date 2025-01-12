package blizzard.development.mail.commands;

import blizzard.development.mail.database.methods.PlayerMethods;
import blizzard.development.mail.utils.MailUtils;
import blizzard.development.mail.utils.PluginImpl;
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
import java.util.Set;

@CommandAlias("site")
public class GiveItem extends BaseCommand {

    @CommandPermission("mail.admin")
    @Syntax("<item> <player> <quantidade>")
    @Subcommand("give")
    public void onCommand(CommandSender sender, String itemName, String playerName, int amount) {
        PlayerMethods playerMethods = PlayerMethods.getInstance();
        Player playerExact = Bukkit.getPlayerExact(playerName);

        if (!MailUtils.getInstance().itemExists(itemName)) {
            sender.sendMessage("§c§lOPS! §cEsse item não está no catálogo!");
            return;
        }

        for (int i = 0; i < amount; i++) {
            playerMethods.addToList(playerExact, itemName);
        }

        sender.sendMessage("Vc deu " + playerExact.getDisplayName() + " " + amount + " " + itemName);
    }
}
