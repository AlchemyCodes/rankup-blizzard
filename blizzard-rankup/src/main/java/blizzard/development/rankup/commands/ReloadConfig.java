package blizzard.development.rankup.commands;

import blizzard.development.rankup.utils.PluginImpl;
import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.Default;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

@CommandAlias("reloadrankup")
public class ReloadConfig extends BaseCommand {

    @Default
    public void onReload(CommandSender commandSender) {
        Player player = (Player) commandSender;
        PluginImpl instance = PluginImpl.getInstance();

        instance.Config.reloadConfig();
        instance.Database.reloadConfig();
        instance.Messages.reloadConfig();
        instance.Inventories.reloadConfig();
        instance.Prestige.reloadConfig();


        player.sendActionBar("§4§lYAY! §4Reload de todas as config's foram realizadas com sucesso.");
    }
}
