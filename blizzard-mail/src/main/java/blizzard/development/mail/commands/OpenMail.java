package blizzard.development.mail.commands;

import blizzard.development.mail.inventories.MailInventory;
import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.Default;
import org.bukkit.entity.Player;

@CommandAlias("site")
public class OpenMail extends BaseCommand {

    @Default
    public void onCommand(Player player) {
        MailInventory.openMailInventory(player);
    }
}
