package blizzard.development.rankup.tasks;

import blizzard.development.rankup.inventories.ConfirmationInventory;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import java.util.HashMap;

public class AutoRankup implements Runnable {

    public AutoRankup(Plugin main) {
        main.getServer()
                .getScheduler()
                .runTaskTimerAsynchronously(main, this, 0, 20L * 5);
    }

    public static HashMap<Player, Boolean> autoRankUp = new HashMap<>();

    @Override
    public void run() {
        for (Player player : Bukkit.getOnlinePlayers()) {
            if (autoRankUp.containsKey(player)) {
                ConfirmationInventory.processRankUp(player);
            }
        }

    }
}
