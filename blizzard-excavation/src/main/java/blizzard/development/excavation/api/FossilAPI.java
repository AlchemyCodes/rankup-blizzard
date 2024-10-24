package blizzard.development.excavation.api;

import blizzard.development.currencies.api.CurrenciesAPI;
import blizzard.development.currencies.enums.Currencies;
import org.bukkit.entity.Player;

public class FossilAPI {

    public String getFossilBalance(Player player) {
        CurrenciesAPI api = CurrenciesAPI.getInstance();

        return api.getBalance(player, Currencies.FOSSILS);
    }

    public void setFossilBalance(Player player, double amount) {
        CurrenciesAPI api = CurrenciesAPI.getInstance();

        api.setBalance(player, Currencies.FOSSILS, amount);
    }
}
