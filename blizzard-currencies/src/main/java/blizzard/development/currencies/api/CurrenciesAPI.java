package blizzard.development.currencies.api;

import blizzard.development.currencies.currencies.SoulsCurrency;
import blizzard.development.currencies.enums.Currencies;
import blizzard.development.currencies.utils.NumberFormat;
import org.bukkit.entity.Player;

public class CurrenciesAPI {
    private static CurrenciesAPI instance;

    NumberFormat format = NumberFormat.getInstance();

    SoulsCurrency soulsCurrency = SoulsCurrency.getInstance();
    String souls = Currencies.SOULS.getName();

    public String getBalance(Player player, Currencies currency) {
        if (currency.getName().equals(souls)) {
            return format.formatNumber(soulsCurrency.getBalance(player));
        }
        return "0";
    }

    public void setBalance(Player player, Currencies currency, String balance) {
        if (currency.getName().equals(souls)) {
            double bal = format.parseNumber(balance);
            soulsCurrency.setBalance(player, bal);
        }
    }

    public void setBalance(Player player, Currencies currency, double balance) {
        if (currency.getName().equals(souls)) {
            soulsCurrency.setBalance(player, balance);
        }
    }

    public void addBalance(Player player, Currencies currency, String balance) {
        if (currency.getName().equals(souls)) {
            double bal = format.parseNumber(balance);
            soulsCurrency.addBalance(player, bal);
        }
    }

    public void addBalance(Player player, Currencies currency, double balance) {
        if (currency.getName().equals(souls)) {
            soulsCurrency.addBalance(player, balance);
        }
    }

    public void removeBalance(Player player, Currencies currency, String balance) {
        if (currency.getName().equals(souls)) {
            double bal = format.parseNumber(balance);
            soulsCurrency.removeBalance(player, bal);
        }
    }

    public void removeBalance(Player player, Currencies currency, double balance) {
        if (currency.getName().equals(souls)) {
            soulsCurrency.removeBalance(player, balance);
        }
    }

    public static CurrenciesAPI getInstance() {
        if (instance == null) instance = new CurrenciesAPI();
        return instance;
    }
}
