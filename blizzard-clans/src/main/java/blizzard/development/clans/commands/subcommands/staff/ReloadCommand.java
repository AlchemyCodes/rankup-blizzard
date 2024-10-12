package blizzard.development.clans.commands.subcommands.staff;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.CommandPermission;
import co.aikar.commands.annotation.Subcommand;
import org.bukkit.command.CommandSender;
import blizzard.development.clans.utils.PluginImpl;

@CommandAlias("clans|clan")
public class ReloadCommand extends BaseCommand {

    @Subcommand("reload")
    @CommandPermission("legacy.clans.admin")
    public void onCommand(CommandSender sender) {
        PluginImpl.getInstance().Config.reloadConfig();
        sender.sendMessage("§aConfigurações reiniciadas com sucesso!");
    }

}
