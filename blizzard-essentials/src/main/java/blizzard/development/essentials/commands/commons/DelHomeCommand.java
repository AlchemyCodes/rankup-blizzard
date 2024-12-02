package blizzard.development.essentials.commands.commons;

import blizzard.development.essentials.database.cache.methods.HomeCacheMethod;
import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.Default;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

@CommandAlias("delhome")
public class DelHomeCommand extends BaseCommand {

    private final HomeCacheMethod homeCacheMethod = new HomeCacheMethod();

    @Default
    public void onCommand(CommandSender commandSender, String homeName) {
        Player player = (Player) commandSender;

        if (homeName == null) {
            player.sendMessage("§c§lEI! §dInforme o nome da home a ser removida.");
            return;
        }

        homeCacheMethod.deleteHome(player, homeName);
    }
}