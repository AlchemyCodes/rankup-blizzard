package blizzard.development.essentials.commands.commons;

import blizzard.development.essentials.managers.tpa.adapters.TpaRequestAdapter;
import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.Default;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

@CommandAlias("tpa")
public class TpaCommand extends BaseCommand {

    @Default
    public void onCommand(CommandSender commandSender, String playerTarget) {

        Player player = (Player) commandSender;
        Player target = Bukkit.getPlayer(playerTarget);

        if (target == null) {
            player.sendActionBar("§c§lEI! §cO jogador §f´§7" + playerTarget + "§f´§c não existe.");
            return;
        }

        TpaRequestAdapter.getInstance().sendTpaRequest(player, target);
    }
}
