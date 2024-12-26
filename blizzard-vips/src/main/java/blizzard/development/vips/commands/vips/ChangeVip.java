package blizzard.development.vips.commands.vips;

import blizzard.development.vips.database.dao.PlayersDAO;
import blizzard.development.vips.inventories.VipChangeInventory;
import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.Default;
import org.bukkit.entity.Player;

@CommandAlias("trocarvip")
public class ChangeVip extends BaseCommand {
    @Default
    public void onCommand(Player player) {
        VipChangeInventory.getInstance().changeVipInventory(player);
    }
}
