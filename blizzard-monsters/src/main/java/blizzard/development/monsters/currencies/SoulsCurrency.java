package blizzard.development.monsters.currencies;

import blizzard.development.currencies.api.CurrenciesAPI;
import blizzard.development.currencies.enums.Currencies;

public class SoulsCurrency {
    private static SoulsCurrency instance;

    private final CurrenciesAPI currencies = CurrenciesAPI.getInstance();
    private final Currencies soulsCurrency = Currencies.SOULS;

    public void addSouls() {

    }

    public void removeSouls() {

    }

    public static SoulsCurrency getInstance() {
        if (instance == null) instance = new SoulsCurrency();
        return instance;
    }
}
