package blizzard.development.clans.listeners.clans;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import blizzard.development.clans.database.cache.ClansCacheManager;
import blizzard.development.clans.database.storage.ClansData;
import blizzard.development.clans.methods.ClansMethods;

public class FriendlyFireListener implements Listener {

    @EventHandler
    public void onPlayerDamage(EntityDamageByEntityEvent event) {
        if (event.getEntity() instanceof Player && event.getDamager() instanceof Player) {
            Player damaged = (Player) event.getEntity();
            Player damager = (Player) event.getDamager();

            String damagedClan = ClansMethods.getUserClan(damaged);
            String damagerClan = ClansMethods.getUserClan(damager);

            if (damagedClan != null && damagedClan.equals(damagerClan)) {
                ClansData clanData = ClansCacheManager.getClansData(damagedClan);

                if (clanData != null && !clanData.isFriendlyfire()) {
                    event.setCancelled(true);
                }
            }
        }
    }
}
