package blizzard.development.vips.commands.vips;

import blizzard.development.vips.utils.vips.VipUtils;
import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.CommandCompletion;
import co.aikar.commands.annotation.Default;
import co.aikar.commands.annotation.Syntax;
import org.bukkit.entity.Player;

import java.sql.SQLException;

@CommandAlias("removervip")

public class RemoveVip extends BaseCommand {

    @Default
    @Syntax("<player> <vip>")
    @CommandCompletion("@playerName @vipName")
    public void onCommand(Player sender, String player, String vipName) throws SQLException {
        VipUtils.getInstance().removeVip(sender, player, vipName);
    }
}
