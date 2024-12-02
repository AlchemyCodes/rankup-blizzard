package blizzard.development.essentials.commands.commons;

import blizzard.development.essentials.database.cache.methods.HomeCacheMethod;
import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.Default;
import co.aikar.commands.annotation.Subcommand;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;


@CommandAlias("home")
public class HomeCommand extends BaseCommand {

    private final HomeCacheMethod homeCacheMethod = new HomeCacheMethod();

    @Default
    public void onCommand(CommandSender commandSender, String homeName) {
        Player player = (Player) commandSender;

        if (homeName == null) {
            return;
        }

        homeCacheMethod.teleportToHome(player, homeName);
    }

    @Subcommand("lista")
    public void onSubCommand(CommandSender commandSender) {
        Player player = (Player) commandSender;

        homeCacheMethod.listHomes(player);
    }
}