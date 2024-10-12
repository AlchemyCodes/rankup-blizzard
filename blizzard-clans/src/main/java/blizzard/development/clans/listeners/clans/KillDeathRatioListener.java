package blizzard.development.clans.listeners.clans;

import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import blizzard.development.clans.database.cache.PlayersCacheManager;

public class KillDeathRatioListener implements Listener {

    @EventHandler
    public void onPlayerKill(EntityDeathEvent event) {
        if (event.getEntityType() == EntityType.PLAYER) {
            Player killed = (Player) event.getEntity();

            Entity killer = killed.getKiller();
            if (killer != null && killer.getType() == EntityType.PLAYER) {
                Player killerPlayer = (Player) killer;
                PlayersCacheManager.addKills(killerPlayer.getName(), 1);
            }
        }
    }

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event) {
        Player player = event.getEntity();

        Entity killer = player.getKiller();
        if (killer != null && killer.getType() == EntityType.PLAYER) {
            PlayersCacheManager.addDeaths(player.getName(), 1);
        }
    }

}
