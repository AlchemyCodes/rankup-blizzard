package blizzard.development.spawners.listeners.spawners.mobs;

import blizzard.development.spawners.utils.items.TextAPI;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public class MobHitListener implements Listener {

    private int ng = 0;

    @EventHandler
    public void onMobHit(EntityDamageByEntityEvent event) {
        if (event.getDamager() instanceof Player player) {
            Entity mob = event.getEntity();

            if (mob.hasMetadata("blizzard_spawners-mob")) {
                String value = mob.getMetadata("blizzard_spawners-mob").get(0).asString();
                player.sendMessage("vose bateu em um " + value);
            }

        }
    }
}
