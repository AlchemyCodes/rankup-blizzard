package blizzard.development.essentials.tasks;

import blizzard.development.essentials.managers.AnnounceManager;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class AnnounceTask extends BukkitRunnable {
    @Override
    public void run() {
        for (Player player : Bukkit.getOnlinePlayers()) {
            AnnounceManager.getInstance().send(player);
        }
    }


}
