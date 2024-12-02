package blizzard.development.essentials.commands.commons;

import blizzard.development.essentials.managers.tpa.adapters.TpaDenyAdapter;
import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.Default;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

@CommandAlias("tpadeny")
public class TpaDenyCommand extends BaseCommand {

    @Default
    public void onCommand(CommandSender commandSender, String playerRequester) {
        Player player = (Player) commandSender;
        Player requester = Bukkit.getPlayer(playerRequester);

        if (requester == null) {
            player.sendActionBar("§c§lEI! §cO jogador §f´§7" + playerRequester + "§f´§c não existe.");
            return;
        }

        TpaDenyAdapter.getInstance().tpaDeny(player, requester);
    }
}
