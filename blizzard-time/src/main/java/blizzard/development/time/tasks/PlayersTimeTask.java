package blizzard.development.time.tasks;

import blizzard.development.time.database.cache.managers.PlayersCacheManager;
import blizzard.development.time.database.cache.setters.PlayersCacheSetters;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class PlayersTimeTask extends BukkitRunnable {
    @Override
    public void run() {
        PlayersCacheManager.getInstance().playersCache.forEach((name, playersData) -> {
            Player player = Bukkit.getPlayer(name);
            if (player != null && player.isOnline()) {
                PlayersCacheSetters.getInstance().setPlayTime(player, playersData.getPlayTime() + 1);
            }
        });
    }
}
