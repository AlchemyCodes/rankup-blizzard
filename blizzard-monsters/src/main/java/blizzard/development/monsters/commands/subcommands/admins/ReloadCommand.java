package blizzard.development.monsters.commands.subcommands.admins;

import blizzard.development.monsters.utils.PluginImpl;
import blizzard.development.monsters.utils.items.TextAPI;
import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.CommandPermission;
import co.aikar.commands.annotation.Subcommand;
import org.bukkit.command.CommandSender;

@CommandAlias("monsters|monster|monstros|monstro|bosses|boss")
@CommandPermission("blizzard.monsters.admin")
public class ReloadCommand extends BaseCommand {

    @Subcommand("reload")
    public void onCommand(CommandSender sender) {
        PluginImpl plugin = PluginImpl.getInstance();

        plugin.Config.reloadConfig();
        plugin.Database.reloadConfig();

        sender.sendActionBar(TextAPI.parse("§a§lYAY! §aPlugin reiniciado com sucesso."));
    }
}
