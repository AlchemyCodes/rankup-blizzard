package blizzard.development.rankup.utils;

import org.bukkit.configuration.file.YamlConfiguration;

public class PrestigeUtils {

    public static double prestigePrice(int prestigeLevel) {
        YamlConfiguration prestigeConfig = PluginImpl.getInstance().Prestige.getConfig();

        double prestigePrice = prestigeConfig.getDouble("prestige.price");

        return prestigePrice * (prestigeLevel + 1);
    }

    public static double prestigeCostAdd(int prestigeLevel) {
        YamlConfiguration prestigeConfig = PluginImpl.getInstance().Prestige.getConfig();

        double costAdd = prestigeConfig.getDouble("prestige.cost-add");

        if (prestigeLevel == 0) {
            return 1;
        }

        return costAdd * prestigeLevel;
    }
}
