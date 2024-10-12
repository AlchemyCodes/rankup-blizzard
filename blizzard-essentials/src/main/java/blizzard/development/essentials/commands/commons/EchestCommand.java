package blizzard.development.essentials.commands.commons;

import blizzard.development.essentials.utils.CooldownUtils;
import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.Default;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.concurrent.TimeUnit;

@CommandAlias("echest|chest|ec")
public class EchestCommand extends BaseCommand {

    @Default
    public void onCommand(CommandSender commandSender) {
        if (!(commandSender instanceof Player)) {
            return;
        }

        Player player = (Player) commandSender;

        if (CooldownUtils.getInstance().isInCountdown(player, "enderchest")) {
            player.sendActionBar("§c§lEI! §cAguarde um pouco para abrir o enderchest novamente.");
            return;
        }

        player.openInventory(player.getEnderChest());
        CooldownUtils.getInstance().createCountdown(player, "enderchest", 5, TimeUnit.SECONDS);
    }

}
