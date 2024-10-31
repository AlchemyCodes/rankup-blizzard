package blizzard.development.fishing.listeners;

import blizzard.development.fishing.database.cache.FishingCacheManager;
import blizzard.development.fishing.handlers.FishBucketHandler;
import blizzard.development.fishing.handlers.FishingNetHandler;
import blizzard.development.fishing.handlers.FishingRodHandler;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class TrafficListener implements Listener {

    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        FishingCacheManager.removeFisherman(event.getPlayer().getUniqueId());
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        FishBucketHandler.setBucket(player);
        FishingNetHandler.setNet(player);
        FishingRodHandler.setRod(player);
    }
}
