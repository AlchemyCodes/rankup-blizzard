package blizzard.development.plantations.commands;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.Default;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

@CommandAlias("debug|debugar")
public class DebugCommand extends BaseCommand {

    @Default
    public void onCommand(CommandSender commandSender) {

        Player player = (Player) commandSender;

        ItemStack item = player.getInventory().getItemInMainHand();


        System.out.println("nbts: " + item.getItemMeta().getPersistentDataContainer().getKeys());
    }
}
