package blizzard.development.essentials.tasks;

import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class VersionTask extends BukkitRunnable {

    public VersionTask(Player player) {
        this.player = player;
    }

    Player player;
    int i = 0;

    @Override
    public void run() {
        i++;

        if (i == 5) {
            this.cancel();
            return;
        }

        player.sendTitle(
            "§c§lVersão antiga detectada!",
            "§7Use a §f1.20.4§7 para ter uma melhor experiência.",
            10,
            70,
            20
        );
        player.playSound(player.getLocation(), "block.note_block_pling", 100, 1);
    }

}
