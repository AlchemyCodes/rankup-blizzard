package blizzard.development.mine.tasks.booster;

import blizzard.development.mine.database.cache.BoosterCacheManager;
import blizzard.development.mine.database.cache.methods.BoosterCacheMethods;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;

public class BoosterTask implements Runnable {
    @Override
    public void run() {
        BoosterCacheMethods boosterCache = BoosterCacheMethods.getInstance();

        for (@NotNull OfflinePlayer player : Bukkit.getOfflinePlayers()) {
            UUID uuid = player.getUniqueId();

            if (boosterCache.getBoosterDuration(uuid) <= 1) {
                boosterCache.setBoosterName(uuid, "");
                boosterCache.setBoosterDuration(uuid, 0);
                continue;
            }

            boosterCache.setBoosterDuration(uuid, boosterCache.getBoosterDuration(uuid) - 3);
        }
    }
}
