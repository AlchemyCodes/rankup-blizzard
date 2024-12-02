package blizzard.development.essentials.commands.commons;

import blizzard.development.essentials.database.cache.methods.HomeCacheMethod;
import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.Default;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

@CommandAlias("sethome")
public class SetHomeCommand extends BaseCommand {
    private final HomeCacheMethod homeCacheMethod = new HomeCacheMethod();

    @Default
    public void onCommand(CommandSender commandSender, String homeName) {
        Player player = (Player) commandSender;

        if (homeName == null) {
            player.sendMessage("§c§lEI! §dDê um nome a sua home.");
            return;
        }

        homeCacheMethod.setHome(player, homeName);
    }
}