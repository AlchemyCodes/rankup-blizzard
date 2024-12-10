package blizzard.development.spawners.commands.spawners;

import blizzard.development.spawners.inventories.shop.ShopInventory;
import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.CommandPermission;
import co.aikar.commands.annotation.Default;
import org.bukkit.entity.Player;

@CommandAlias("spawners|spawner|geradores|gerador")
@CommandPermission("blizzard.spawners.admin")
public class SpawnersCommand extends BaseCommand {

    @Default
    public void onCommand(Player player) {
        ShopInventory.getInstance().open(player);
    }
}
