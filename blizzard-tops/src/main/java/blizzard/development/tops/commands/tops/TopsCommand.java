package blizzard.development.tops.commands.tops;

import blizzard.development.currencies.enums.Currencies;
import blizzard.development.tops.inventories.currencies.CurrenciesInventory;
import blizzard.development.tops.inventories.TopsInventory;
import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.*;
import org.bukkit.entity.Player;

import java.util.Arrays;
import java.util.List;

@CommandAlias("tops|top|destaques|destaque")
@CommandPermission("blizzard.tops.basic")
public class TopsCommand extends BaseCommand {

    @Default
    @CommandCompletion("@currencies")
    @Syntax("<economia>")
    public void onCommand(Player player, @Optional String currency) {
        if (currency != null) {
            if (currencyEquals(currency, Currencies.FLAKES.getName()) || currencyEquals(currency, "Flocos")) {
                CurrenciesInventory.getInstance().open(player, Currencies.FLAKES.getName());
            } else if ((currencyEquals(currency, Currencies.FOSSILS.getName()) || currencyEquals(currency, "Fosseis"))) {
                CurrenciesInventory.getInstance().open(player, Currencies.FOSSILS.getName());
            } else if ((currencyEquals(currency, Currencies.SOULS.getName()) || currencyEquals(currency, "Almas"))) {
                CurrenciesInventory.getInstance().open(player, Currencies.SOULS.getName());
            } else {
                List<String> messages = Arrays.asList(
                        "",
                        " §c§lEI §cA economia §7" + currency + "§c não existe.",
                        " §cDisponíveis: §7[Flocos, Fosseis e Almas]",
                        ""
                );

                messages.forEach(player::sendMessage);
            }
            return;
        }

        TopsInventory.getInstance().open(player);
    }

    public Boolean currencyEquals(String currency, String currencyName) {
        return currency.equalsIgnoreCase(currencyName);
    }
}
