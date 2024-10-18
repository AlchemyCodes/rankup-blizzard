package blizzard.development.essentials.commands.commons;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.Default;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

@CommandAlias("trash|lixeira|lixo")
public class TrashCommand extends BaseCommand {

    @Default
    public void onCommand(CommandSender commandSender) {
        if (!(commandSender instanceof Player)) {
            return;
        }

        Player player = (Player) commandSender;

        Inventory trash = Bukkit.createInventory(null, 6 * 9, "Lixeira");
        player.openInventory(trash);
    }
}
