package blizzard.development.spawners.listeners.spawners.mobs;

import org.bukkit.entity.Entity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreeperPowerEvent;
import org.bukkit.event.entity.EntityCombustEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.PigZapEvent;

public class MobCommonListener implements Listener {

    @EventHandler
    public void onMobSunDamage(EntityDamageEvent event) {
        if (event.getCause() != EntityDamageEvent.DamageCause.ENTITY_ATTACK) {
            Entity mob = event.getEntity();
            if (mob.hasMetadata("blizzard_spawners-mob")) {
                event.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onEntityCombust(EntityCombustEvent event) {
        Entity mob = event.getEntity();
        if (mob.hasMetadata("blizzard_spawners-mob")) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onPigLightningTransform(PigZapEvent event) {
        Entity mob = event.getEntity();
        if (mob.hasMetadata("blizzard_spawners-mob")) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onCreeperPowerEvent(CreeperPowerEvent event) {
        Entity mob = event.getEntity();
        if (mob.hasMetadata("blizzard_spawners-mob")) {
            event.setCancelled(true);
        }
    }
}
