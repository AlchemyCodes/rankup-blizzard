package blizzard.development.vips.commands.subcommands.freeze;

import blizzard.development.vips.utils.vips.VipUtils;
import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.CommandPermission;
import co.aikar.commands.annotation.Subcommand;
import org.bukkit.entity.Player;

@CommandAlias("vip")
public class FreezeVip extends BaseCommand {

    @Subcommand("congelar")
    @CommandPermission("vips.admin")
    public void onCommand(Player player) {
        VipUtils vipUtils = VipUtils.getInstance();

        if (vipUtils.isVipTimeFrozen) {
            vipUtils.isVipTimeFrozen = false;
            player.sendMessage("§bTempo dos vips não está mais congelado!");
            return;
        }

        vipUtils.isVipTimeFrozen = true;
        player.sendMessage("§bTempo dos vips está congelado!");
    }
}
