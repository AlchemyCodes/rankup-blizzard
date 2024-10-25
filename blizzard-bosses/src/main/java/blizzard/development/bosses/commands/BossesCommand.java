package blizzard.development.bosses.commands;

import blizzard.development.bosses.inventories.BossesInventory;
import blizzard.development.currencies.api.CurrenciesAPI;
import blizzard.development.currencies.enums.Currencies;
import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.CommandPermission;
import co.aikar.commands.annotation.Default;
import co.aikar.commands.annotation.Syntax;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;


@CommandAlias("bosses|boss|monstros|monstro")
public class BossesCommand extends BaseCommand {

    @Default
    @Syntax("<bosses>")
    @CommandPermission("blizzard.bosses.basic")
    private void onCommand(CommandSender sender) {
        if (!(sender instanceof Player player)) {
            sender.sendMessage("§cEste comando só pode ser utilizado por jogadores!");
            return;
        }

        BossesInventory.open(player);
    }
}
