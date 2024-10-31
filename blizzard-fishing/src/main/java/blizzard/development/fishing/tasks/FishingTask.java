package blizzard.development.fishing.tasks;

import blizzard.development.fishing.database.cache.FishingCacheManager;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

public final class FishingTask implements Runnable {

    public FishingTask(Plugin plugin) {
        plugin.getServer()
                .getScheduler()
                .runTaskTimer(plugin, this, 0, 20L);
    }

    @Override
    public void run() {
        for (Map.Entry<UUID, Long> entry : FishingCacheManager.getFishermans().entrySet()) {

            final long lastFishTime = entry.getValue();

            if (lastFishTime + TimeUnit.SECONDS.toMillis(2) > System.currentTimeMillis()) {
                continue;
            }

            final UUID playerId = entry.getKey();
            final Player player = Bukkit.getPlayer(playerId);

            player.sendMessage("§bVocê pescou um peixe!");

            FishingCacheManager.resetPlayerLastFishTime(player);
        }
    }
}
