package blizzard.development.bosses.commands.subcommands;

import blizzard.development.bosses.utils.PluginImpl;
import blizzard.development.bosses.utils.items.apis.TextAPI;
import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.*;
import org.bukkit.command.CommandSender;

@CommandAlias("bosses|boss|monstros|monstro")
public class ReloadCommand extends BaseCommand {

    @Subcommand("reload|reiniciar")
    @Syntax("<reiniciar>")
    @CommandPermission("blizzard.bosses.admin")
    public void onCommand(CommandSender sender) {
        PluginImpl plugin = PluginImpl.getInstance();

        plugin.Config.reloadConfig();
        plugin.Database.reloadConfig();
        plugin.Locations.reloadConfig();

        sender.sendActionBar(TextAPI.parse("§a§lYAY! §aConfigurações reiniciadas com sucesso."));
    }
}
