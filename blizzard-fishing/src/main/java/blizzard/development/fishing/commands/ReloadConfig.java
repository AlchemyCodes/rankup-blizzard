package blizzard.development.fishing.commands;

import blizzard.development.fishing.utils.PluginImpl;
import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.Default;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

@CommandAlias("reloadfishing")
public class ReloadConfig extends BaseCommand {

    @Default
    public void onReload(CommandSender commandSender) {
        Player player = (Player) commandSender;

        PluginImpl.getInstance().Config.reloadConfig();
        PluginImpl.getInstance().Database.reloadConfig();
        PluginImpl.getInstance().Enchantments.reloadConfig();
        PluginImpl.getInstance().Messages.reloadConfig();

        player.sendActionBar("§4§lYAY! §4Reload de todas as config's foram realizadas com sucesso.");
    }
}
