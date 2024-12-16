package blizzard.development.spawners.commands.slaughterhouses.subcommands;

import blizzard.development.spawners.utils.PluginImpl;
import blizzard.development.spawners.utils.items.TextAPI;
import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.CommandPermission;
import co.aikar.commands.annotation.Subcommand;
import org.bukkit.command.CommandSender;

@CommandAlias("slaughterhouses|slaughterhouse|abatedouros|abatedouro|matadouros|matadouro")
@CommandPermission("blizzard.spawners.admin")
public class ReloadCommand extends BaseCommand {

    @Subcommand("reload")
    public void onCommand(CommandSender sender) {
        final PluginImpl plugin = PluginImpl.getInstance();

        plugin.Slaughterhouses.reloadConfig();

        sender.sendActionBar(TextAPI.parse("§a§lYAY! §aPlugin reiniciado com sucesso."));
    }
}
