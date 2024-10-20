package blizzard.development.bosses.commands.subcommands;

import blizzard.development.bosses.utils.PluginImpl;
import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.*;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

@CommandAlias("bosses|boss|monstros|monstro")
public class ReloadCommand extends BaseCommand {

    @Subcommand("reload")
    @Syntax("<reload>")
    @CommandPermission("blizzard.bosses.admin")
    public void onCommand(CommandSender sender) {
        Player player = (Player) sender;

        PluginImpl plugin = PluginImpl.getInstance();

        plugin.Config.reloadConfig();
        plugin.Database.reloadConfig();
        plugin.Locations.reloadConfig();

        player.sendMessage("§a§lYAY! §aConfigurações reiniciadas com sucesso.");
    }
}
