package blizzard.development.currencies.commands.currencies.bosses;

import blizzard.development.currencies.api.CurrenciesAPI;
import blizzard.development.currencies.enums.Currencies;
import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.Default;
import org.bukkit.entity.Player;

@CommandAlias("souls")
public class SoulsCommand extends BaseCommand {

    CurrenciesAPI api = CurrenciesAPI.getInstance();

    @Default
    public void onCommand(Player player) {
        String balance = api.getBalance(player, Currencies.SOULS);
        player.sendMessage("§aSuas arma sao de: §7" + balance);
    }

}
