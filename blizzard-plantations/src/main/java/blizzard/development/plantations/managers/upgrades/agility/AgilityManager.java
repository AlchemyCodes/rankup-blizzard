package blizzard.development.plantations.managers.upgrades.agility;

import blizzard.development.plantations.database.cache.methods.ToolCacheMethod;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class AgilityManager {

    public static ToolCacheMethod toolCacheMethod = ToolCacheMethod.getInstance();

    public static void check(Player player, String id) {

        int agility = toolCacheMethod.getAgility(id);

        if (agility < 2) {
            player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, PotionEffect.INFINITE_DURATION, 0));
        } else if (agility == 2) {
            player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, PotionEffect.INFINITE_DURATION, 1));
        }
    }
}
