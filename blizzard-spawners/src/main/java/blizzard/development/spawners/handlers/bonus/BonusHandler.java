package blizzard.development.spawners.handlers.bonus;

import blizzard.development.spawners.handlers.enchantments.EnchantmentsHandler;
import blizzard.development.spawners.utils.PluginImpl;
import org.bukkit.configuration.ConfigurationSection;

import java.util.HashMap;
import java.util.Map;

public class BonusHandler {
    private static BonusHandler instance;

    public Map<String, Integer> getBuyBonusInfo(String groupKey) {
        return getBonusInfo("bonus.buy-bonus." + groupKey);
    }

    public Map<String, Integer> getSellBonusInfo(String groupKey) {
        return getBonusInfo("bonus.sell-bonus." + groupKey);
    }

    private Map<String, Integer> getBonusInfo(String path) {
        ConfigurationSection section = PluginImpl.getInstance().Bonus.getConfig().getConfigurationSection(path);
        if (section == null) {
            return new HashMap<>();
        }

        Map<String, Integer> bonusData = new HashMap<>();
        bonusData.put("bonus", section.getInt("bonus", 0));
        return bonusData;
    }

    public int getBuyBonus(String groupKey) {
        return PluginImpl.getInstance().Bonus.getConfig().getInt("bonus.buy-bonus." + groupKey + ".bonus", 0);
    }

    public int getSellBonus(String groupKey) {
        return PluginImpl.getInstance().Bonus.getConfig().getInt("bonus.sell-bonus." + groupKey + ".bonus", 0);
    }

    public String getBuyBonusPermission(String groupKey) {
        return PluginImpl.getInstance().Bonus.getConfig().getString("bonus.buy-bonus." + groupKey + ".permission", "");
    }

    public String getSellBonusPermission(String groupKey) {
        return PluginImpl.getInstance().Bonus.getConfig().getString("bonus.sell-bonus." + groupKey + ".permission", "");
    }

    public boolean isBuyBonusValid(String groupKey) {
        return PluginImpl.getInstance().Bonus.getConfig().contains("bonus.buy-bonus." + groupKey);
    }

    public boolean isSellBonusValid(String groupKey) {
        return PluginImpl.getInstance().Bonus.getConfig().contains("bonus.sell-bonus." + groupKey);
    }

    public static BonusHandler getInstance() {
        if (instance == null) instance = new BonusHandler();
        return instance;
    }
}
