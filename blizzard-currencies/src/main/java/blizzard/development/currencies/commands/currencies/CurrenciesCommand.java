package blizzard.development.currencies.commands.currencies;

import blizzard.development.currencies.utils.PluginImpl;
import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.CommandPermission;
import co.aikar.commands.annotation.Default;
import co.aikar.commands.annotation.Subcommand;
import org.bukkit.command.CommandSender;

@CommandAlias("currencies|moedas")
public class CurrenciesCommand extends BaseCommand {
    PluginImpl impl = PluginImpl.getInstance();

    @Default
    @CommandPermission("blizzard.currencies.admin")
    public void onCommand(CommandSender sender) {}

    @Subcommand("reload reiniciad")
    @CommandPermission("blizzard.currencies.admin")
    public void onReload(CommandSender sender) {
        impl.Config.reloadConfig();
        impl.Database.reloadConfig();
        sender.sendMessage("§a§lYAY! §aPlugin reiniciado com sucesso.");
    }
}
