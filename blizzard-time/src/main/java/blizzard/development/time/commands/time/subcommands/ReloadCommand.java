package blizzard.development.time.commands.time.subcommands;

import blizzard.development.time.utils.PluginImpl;
import blizzard.development.time.utils.items.TextAPI;
import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.CommandPermission;
import co.aikar.commands.annotation.Subcommand;
import org.bukkit.command.CommandSender;

@CommandAlias("time|tempo")
@CommandPermission("alchemy.time.admin")
public class ReloadCommand extends BaseCommand {

    @Subcommand("reload|reiniciar")
    public void onReload(CommandSender sender) {
        final PluginImpl plugin = PluginImpl.getInstance();

        plugin.Config.reloadConfig();
        plugin.Database.reloadConfig();
        plugin.Missions.reloadConfig();
        plugin.Rewards.reloadConfig();

        sender.sendActionBar(TextAPI.parse("§a§lYAY! §aPlugin reiniciado."));
    }
}
