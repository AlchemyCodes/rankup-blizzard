package blizzard.development.spawners.handlers.enchantments;

import blizzard.development.spawners.utils.PluginImpl;
import org.bukkit.configuration.ConfigurationSection;

import java.util.HashMap;
import java.util.Map;

public class EnchantmentsHandler {
    private static EnchantmentsHandler instance;

    public Map<String, Integer> getEnchantmentInfo(String enchantmentKey) {
        ConfigurationSection section = PluginImpl.getInstance().Enchantments.getConfig().getConfigurationSection("enchantments." + enchantmentKey);
        if (section == null) {
            return new HashMap<>();
        }

        Map<String, Integer> enchantmentData = new HashMap<>();
        enchantmentData.put("initial-level", section.getInt("initial-level", 0));
        enchantmentData.put("per-level", section.getInt("per-level", 0));
        enchantmentData.put("max-level", section.getInt("max-level", 0));

        return enchantmentData;
    }

    public int getInitialLevel(String enchantmentKey) {
        return PluginImpl.getInstance().Enchantments.getConfig().getInt("enchantments." + enchantmentKey + ".initial-level", 0);
    }

    public int getPerLevel(String enchantmentKey) {
        return PluginImpl.getInstance().Enchantments.getConfig().getInt("enchantments." + enchantmentKey + ".per-level", 0);
    }

    public int getMaxLevel(String enchantmentKey) {
        return PluginImpl.getInstance().Enchantments.getConfig().getInt("enchantments." + enchantmentKey + ".max-level", 0);
    }

    public int getInitialPrice(String enchantmentKey) {
        return PluginImpl.getInstance().Enchantments.getConfig().getInt("enchantments." + enchantmentKey + ".initial-price", 0);
    }

    public int getPerLevelPrice(String enchantmentKey) {
        return PluginImpl.getInstance().Enchantments.getConfig().getInt("enchantments." + enchantmentKey + ".per-level-price", 0);
    }

    public boolean isEnchantmentValid(String enchantmentKey) {
        return PluginImpl.getInstance().Enchantments.getConfig().contains("enchantments." + enchantmentKey);
    }

    public static EnchantmentsHandler getInstance() {
        if (instance == null) instance = new EnchantmentsHandler();
        return instance;
    }
}
