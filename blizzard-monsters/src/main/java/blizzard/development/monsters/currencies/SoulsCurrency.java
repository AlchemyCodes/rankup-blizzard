package blizzard.development.monsters.currencies;

import blizzard.development.currencies.api.CurrenciesAPI;
import blizzard.development.currencies.enums.Currencies;
import org.bukkit.entity.Player;

public class SoulsCurrency {
    private static SoulsCurrency instance;

    private final CurrenciesAPI currencies = CurrenciesAPI.getInstance();
    private final Currencies soulsCurrency = Currencies.SOULS;

    public Double getSouls(Player player) {
        return currencies.getBalance(player, soulsCurrency);
    }

    public void addSouls(Player player, double amount) {
        currencies.addBalance(player, soulsCurrency, amount);
    }

    public void removeSouls(Player player, double amount) {
        currencies.removeBalance(player, soulsCurrency, amount);
    }

    public static SoulsCurrency getInstance() {
        if (instance == null) instance = new SoulsCurrency();
        return instance;
    }
}
