package blizzard.development.rankup.tasks;

import blizzard.development.rankup.inventories.ConfirmationInventory;
import blizzard.development.rankup.inventories.RankInventory;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

public class AutoRankup implements Runnable {

    public AutoRankup(Plugin main) {
        main.getServer()
                .getScheduler()
                .runTaskTimerAsynchronously(main, this, 0, 20L * 5);
    }

    @Override
    public void run() {
        for (Player player : Bukkit.getOnlinePlayers()) {
            if (RankInventory.autoRank) {
                player.sendMessage("ta auto rankup ativado e tentou upar");
                ConfirmationInventory.processRankUp(player);
            }
        }

    }
}
