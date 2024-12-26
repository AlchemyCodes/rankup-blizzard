package blizzard.development.spawners.handlers.enchantments;

import blizzard.development.spawners.utils.PluginImpl;
import org.bukkit.configuration.ConfigurationSection;

import java.util.HashMap;
import java.util.Map;

public class EnchantmentsHandler {
    private static EnchantmentsHandler instance;

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

    public static EnchantmentsHandler getInstance() {
        if (instance == null) instance = new EnchantmentsHandler();
        return instance;
    }
}
