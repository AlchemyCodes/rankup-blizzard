package blizzard.development.currencies.api;

import blizzard.development.currencies.currencies.CoinsCurrency;
import blizzard.development.currencies.currencies.FlakesCurrency;
import blizzard.development.currencies.currencies.FossilsCurrency;
import blizzard.development.currencies.currencies.SoulsCurrency;
import blizzard.development.currencies.enums.Currencies;
import blizzard.development.currencies.utils.NumberFormat;
import org.bukkit.entity.Player;

public class CurrenciesAPI {
    private static CurrenciesAPI instance;

    NumberFormat format = NumberFormat.getInstance();

    // API Variables
    CoinsCurrency coinsCurrency = CoinsCurrency.getInstance();
    String coins = Currencies.COINS.getName();
    SoulsCurrency soulsCurrency = SoulsCurrency.getInstance();
    String souls = Currencies.SOULS.getName();
    FlakesCurrency flakesCurrency = FlakesCurrency.getInstance();
    String flakes = Currencies.FLAKES.getName();
    FossilsCurrency fossilsCurrency = FossilsCurrency.getInstance();
    String fossils = Currencies.FOSSILS.getName();

    // API Methods
    public Double getBalance(Player player, Currencies currency) {
        if (currency.getName().equals(souls)) {
            return soulsCurrency.getBalance(player);
        } else if (currency.getName().equals(flakes)) {
            return flakesCurrency.getBalance(player);
        } else if (currency.getName().equals(fossils)) {
            return fossilsCurrency.getBalance(player);
        }
        return 0.0;
    }

    public String getFormattedBalance(Player player, Currencies currency) {
        if (currency.getName().equals(souls)) {
            return format.formatNumber(soulsCurrency.getBalance(player));
        } else if (currency.getName().equals(flakes)) {
            return format.formatNumber(flakesCurrency.getBalance(player));
        } else if (currency.getName().equals(fossils)) {
            return format.formatNumber(fossilsCurrency.getBalance(player));
        }
        return "0";
    }

    public boolean setBalance(Player player, Currencies currency, double balance) {
        if (balance <= 0) {
            return false;
        }
        if (currency.getName().equals(coins)) {
            coinsCurrency.setBalance(player, balance);
        } else if (currency.getName().equals(souls)) {
            soulsCurrency.setBalance(player, balance);
        } else if (currency.getName().equals(flakes)) {
            flakesCurrency.setBalance(player, balance);
        } else if (currency.getName().equals(fossils)) {
            fossilsCurrency.setBalance(player, balance);
        }
        return true;
    }

    public boolean addBalance(Player player, Currencies currency, double balance) {
        if (balance <= 0) {
            return false;
        }
        if (currency.getName().equals(coins)) {
            coinsCurrency.addBalance(player, balance);
        } else if (currency.getName().equals(souls)) {
            soulsCurrency.addBalance(player, balance);
        } else if (currency.getName().equals(flakes)) {
            flakesCurrency.addBalance(player, balance);
        } else if (currency.getName().equals(fossils)) {
            fossilsCurrency.addBalance(player, balance);
        }
        return true;
    }

    public boolean removeBalance(Player player, Currencies currency, double balance) {
        if (balance <= 0) {
            return false;
        }
        if (currency.getName().equals(coins)) {
            coinsCurrency.removeBalance(player, balance);
        } else if (currency.getName().equals(souls)) {
            soulsCurrency.removeBalance(player, balance);
        } else if (currency.getName().equals(flakes)) {
            flakesCurrency.removeBalance(player, balance);
        } else if (currency.getName().equals(fossils)) {
            fossilsCurrency.removeBalance(player, balance);
        }
        return true;
    }

    public boolean transferBalance(Player player, Player target, Currencies currency, double balance) {
        if (balance <= 0) {
            return false;
        }
        if (currency.getName().equals(souls)) {
            soulsCurrency.removeBalance(player, balance);
            soulsCurrency.addBalance(target, balance);
        } else if (currency.getName().equals(flakes)) {
            flakesCurrency.removeBalance(player, balance);
            flakesCurrency.addBalance(target, balance);
        } else if (currency.getName().equals(fossils)) {
            fossilsCurrency.removeBalance(player, balance);
            fossilsCurrency.addBalance(target, balance);
        }
        return true;
    }

    public static CurrenciesAPI getInstance() {
        if (instance == null) instance = new CurrenciesAPI();
        return instance;
    }
}
