package blizzard.development.vips.commands.subcommands;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.CommandPermission;
import co.aikar.commands.annotation.Subcommand;
import org.bukkit.entity.Player;

@CommandAlias("vip")
public class FreezeVipCommand extends BaseCommand {

    private Boolean isVipTimeFrozen = false;

    @Subcommand("congelar")
    @CommandPermission("vips.admin")
    public void onCommand(Player player) {
        if (isVipTimeFrozen) {
            isVipTimeFrozen = false;
            player.sendMessage("§bTempo dos vips não está mais congelado!");
            return;
        }

        isVipTimeFrozen = true;
        player.sendMessage("§bTempo dos vips está congelado!");
    }
}