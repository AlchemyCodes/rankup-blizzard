package blizzard.development.rankup.utils;

import blizzard.development.currencies.api.CurrenciesAPI;
import blizzard.development.currencies.enums.Currencies;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

public class PrestigeUtils {

    public static double getPrestigeCoinsPrice(int prestigeLevel) {
        YamlConfiguration prestigeConfig = (PluginImpl.getInstance()).Prestige.getConfig();

        double prestigeCoinsPrice = prestigeConfig.getDouble("prestige.coinsPrice");

        return prestigeCoinsPrice * (prestigeLevel + 1);
    }

    public static double getPrestigeFlakesPrice(int prestigeLevel) {
        YamlConfiguration prestigeConfig = (PluginImpl.getInstance()).Prestige.getConfig();

        double prestigeFlakesPrice = prestigeConfig.getDouble("prestige.flakesPrice");

        return prestigeFlakesPrice * (prestigeLevel + 1);
    }

    public static double getMissingPrestigeMoney(Player player, int prestigeLevel) {
        CurrenciesAPI currenciesAPI = CurrenciesAPI.getInstance();
        Double balance = currenciesAPI.getBalance(player, Currencies.COINS);

        return getPrestigeCoinsPrice(prestigeLevel) - balance;
    }

    public static double getMissingPrestigeFlakes(Player player, int prestigeLevel) {
        CurrenciesAPI currenciesAPI = CurrenciesAPI.getInstance();
        Double balance = currenciesAPI.getBalance(player, Currencies.FLAKES);

        return getPrestigeFlakesPrice(prestigeLevel) - balance;
    }

    public static double prestigeCoinsCostAdd(int prestigeLevel) {
        YamlConfiguration prestigeConfig = (PluginImpl.getInstance()).Prestige.getConfig();

        double costAdd = prestigeConfig.getDouble("prestige.costCoins-add");

        if (prestigeLevel == 0) {
            return 1.0D;
        }

        return costAdd * prestigeLevel;
    }

    public static double prestigeFlakesCostAdd(int prestigeLevel) {
        YamlConfiguration prestigeConfig = (PluginImpl.getInstance()).Prestige.getConfig();

        double costAdd = prestigeConfig.getDouble("prestige.costFlakes-add");

        if (prestigeLevel == 0) {
            return 1.0D;
        }

        return costAdd * prestigeLevel;
    }
}
