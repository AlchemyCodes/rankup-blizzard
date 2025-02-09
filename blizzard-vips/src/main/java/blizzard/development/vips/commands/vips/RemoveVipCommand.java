package blizzard.development.vips.commands.vips;

import blizzard.development.vips.vips.adapters.VipsAdapter;
import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.CommandCompletion;
import co.aikar.commands.annotation.Default;
import co.aikar.commands.annotation.Syntax;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

@CommandAlias("removervip")
public class RemoveVipCommand extends BaseCommand {

    @Default
    @Syntax("<player> <vip>")
    @CommandCompletion("@playerName @vipName")
    public void onCommand(Player sender, String player, String vipName) {
        Player playerExact = Bukkit.getPlayerExact(player);

        VipsAdapter.getInstance().removeVip(playerExact, vipName);
    }
}