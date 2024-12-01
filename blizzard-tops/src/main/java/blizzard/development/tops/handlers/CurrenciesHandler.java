package blizzard.development.tops.handlers;

import app.miyuki.miyukieconomy.bukkit.BukkitMiyukiEconomy;
import app.miyuki.miyukieconomy.bukkit.BukkitMiyukiEconomyBootstrap;
import app.miyuki.miyukieconomy.common.api.MiyukiEconomyAPI;
import app.miyuki.miyukieconomy.common.currency.Currency;
import app.miyuki.miyukieconomy.common.user.User;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.List;

public class CurrenciesHandler {
    private static CurrenciesHandler instance;

    private final BukkitMiyukiEconomy miyuki = JavaPlugin.getPlugin(BukkitMiyukiEconomyBootstrap.class).getMiyukiEconomy();

    public List<User> getCoinsTopPlayers() {
        MiyukiEconomyAPI api = miyuki.getMiyukiEconomyAPI();
        Currency currency = api.getCurrency("yen");
        return miyuki.getStorage().loadTopUsers(currency.getCurrencyId(), 0, 10);
    }

    public static CurrenciesHandler getInstance() {
        if (instance == null) instance = new CurrenciesHandler();
        return instance;
    }
}
