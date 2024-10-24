package blizzard.development.rankup.utils;

import org.bukkit.configuration.file.YamlConfiguration;

public class PrestigeUtils {

    public static double prestigeCoinsPrice(int prestigeLevel) {
        YamlConfiguration prestigeConfig = (PluginImpl.getInstance()).Prestige.getConfig();

        double prestigeCoinsPrice = prestigeConfig.getDouble("prestige.coinsPrice");

        return prestigeCoinsPrice * (prestigeLevel + 1);
    }

    public static double prestigeFlakesPrice(int prestigeLevel) {
        YamlConfiguration prestigeConfig = (PluginImpl.getInstance()).Prestige.getConfig();

        double prestigeFlakesPrice = prestigeConfig.getDouble("prestige.flakesPrice");

        return prestigeFlakesPrice * (prestigeLevel + 1);
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
