package blizzard.development.essentials.commands.staff;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.*;
import org.bukkit.Bukkit;
import org.bukkit.block.EnderChest;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

@CommandAlias("invsee|inv|spy")
public class InvseeCommand extends BaseCommand {

    @Default
    @CommandPermission("alchemy.essentials.staff")
    @CommandCompletion("@players")
    @Syntax("<jogador>")
    public void onCommand(CommandSender commandSender, String playerTarget) {
        Player player = (Player) commandSender;
        Player target = Bukkit.getPlayer(playerTarget);

        if (target == null) {
            player.sendActionBar("§c§lEI! §cO jogador está offline, ou não existe.");
            return;
        }

        player.openInventory(target.getInventory());
    }

    @Subcommand("echest")
    @CommandCompletion("@players")
    @CommandPermission("alchemy.essentials.staff")
    public void onEchest(CommandSender commandSender, String playerTarget) {

        Player player = (Player) commandSender;
        Player target = Bukkit.getPlayer(playerTarget);

        if (target == null) {
            player.sendActionBar("§c§lEI! §cO jogador está offline, ou não existe.");
            return;
        }

        player.openInventory(target.getEnderChest());
    }
}
