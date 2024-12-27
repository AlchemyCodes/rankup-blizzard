package blizzard.development.spawners.handlers.bonus;

import blizzard.development.spawners.utils.PluginImpl;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;

public class BonusHandler {
    private static BonusHandler instance;

    public double getPlayerBonus(Player player) {
        ConfigurationSection section = PluginImpl.getInstance().Bonus.getConfig().getConfigurationSection("bonus");
        if (section == null) return 0;

        int highestPriority = Integer.MIN_VALUE;
        double bonus = 0;

        for (String groupKey : section.getKeys(false)) {
            String permission = section.getString(groupKey + ".permission", "");
            int priority = section.getInt(groupKey + ".priority", 0);
            int groupBonus = section.getInt(groupKey + ".bonus", 0);

            if (player.hasPermission(permission) && priority > highestPriority) {
                highestPriority = priority;
                bonus = groupBonus;
            }
        }

        return bonus;
    }

    public static BonusHandler getInstance() {
        if (instance == null) instance = new BonusHandler();
        return instance;
    }
}
