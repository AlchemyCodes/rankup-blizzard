package blizzard.development.clans.commands.subcommands.staff;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.*;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import blizzard.development.clans.builders.LimitBuilder;

@CommandAlias("clans|clan")
public class GiveLimitsCommand extends BaseCommand {

    @Subcommand("give")
    @CommandPermission("legacy.clans.admin")
    @CommandCompletion("@types @players @limits")
    @Syntax("<tipo> <jogador> <valor> <quantia>")
    public void onCommand(CommandSender sender, String type, String target, int value, int amount) {
        switch (type) {
            case "limite" -> {

                Player targetPlayer = Bukkit.getPlayer(target);

                if (targetPlayer == null) {
                    sender.sendMessage("§cO jogador informado está offline ou é inválido!");
                    return;
                }

                ItemStack limitItem = LimitBuilder.createLimit(targetPlayer, value);
                limitItem.setAmount(amount);

                if (value <= 0) {
                    sender.sendMessage("§cO valor do limite tem que ser maior que 0!");
                    return;
                }

                sender.sendMessage("§aVocê deu §7" + amount + "x Limite(s) [" + value + "]§a para o jogador §7" + targetPlayer.getName());
                targetPlayer.getInventory().addItem(limitItem);
            }
            default -> sender.sendMessage("§cTipo inválido!");
        }
    }
}
