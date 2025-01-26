package blizzard.development.mine.tasks.mine;

import blizzard.development.mine.mine.adapters.PodiumAdapter;
import org.bukkit.scheduler.BukkitRunnable;

public class UpdatePodiumTask extends BukkitRunnable {

    private final PodiumAdapter podiumAdapter = PodiumAdapter.getInstance();

    @Override
    public void run() {
        podiumAdapter.removeAllNPCs();
        podiumAdapter.createAllNPCs();
    }
}
