package blizzard.development.spawners.listeners.spawners;

import blizzard.development.spawners.builders.EffectsBuilder;
import blizzard.development.spawners.utils.PluginImpl;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.scheduler.BukkitRunnable;

public class SpawnersJoinListener implements Listener {

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        new BukkitRunnable() {
            @Override
            public void run() {
                EffectsBuilder.restorePlayerEffects(player);
            }
        }.runTaskLater(PluginImpl.getInstance().plugin, 20 * 5);
    }
}
