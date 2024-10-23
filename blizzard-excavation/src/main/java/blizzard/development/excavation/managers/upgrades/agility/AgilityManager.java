package blizzard.development.excavation.managers.upgrades.agility;

import blizzard.development.excavation.database.cache.methods.ExcavatorCacheMethod;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class AgilityManager {

    public void check(Player player, ExcavatorCacheMethod excavatorCacheMethod) {

        int agility = excavatorCacheMethod.agilityEnchant(player.getName());

        if (agility < 2) {
            player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, PotionEffect.INFINITE_DURATION, 0));
        } else if (agility == 2) {
            player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, PotionEffect.INFINITE_DURATION, 1));
        }
    }
}
