package blizzard.development.mine.commands.mine.subcommands.admins;

import blizzard.development.mine.utils.PluginImpl;
import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.CommandPermission;
import co.aikar.commands.annotation.Subcommand;
import net.kyori.adventure.text.Component;
import org.bukkit.command.CommandSender;

@CommandAlias("mina|mineracao|mine")
@CommandPermission("blizzard.mine.admin")
public class ReloadCommand extends BaseCommand {

    @Subcommand("reload|reiniciar")
    public void onReload(CommandSender sender) {
        PluginImpl plugin = PluginImpl.getInstance();

        plugin.Config.reloadConfig();
        plugin.Database.reloadConfig();
        plugin.Locations.reloadConfig();
        plugin.Ranking.reloadConfig();

        sender.sendActionBar(Component.text("§a§lYAY! §aConfigurações reiniciadas com sucesso."));
    }
}
