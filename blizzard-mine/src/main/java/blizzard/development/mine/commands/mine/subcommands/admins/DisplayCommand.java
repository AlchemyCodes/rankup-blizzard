package blizzard.development.mine.commands.mine.subcommands.admins;

import blizzard.development.mine.managers.mine.DisplayManager;
import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.CommandPermission;
import co.aikar.commands.annotation.Subcommand;
import net.kyori.adventure.text.Component;
import org.bukkit.command.CommandSender;

@CommandAlias("mina|mineracao|mine")
@CommandPermission("blizzard.mine.admin")
public class DisplayCommand extends BaseCommand {

    @Subcommand("startdisplay")
    public void onStartDisplay(CommandSender sender) {
        DisplayManager.getInstance().initializePickaxeDisplay();
        sender.sendActionBar(Component.text("§a§lYAY! §aVocê iniciou a rotação do display da mina."));
    }
}
