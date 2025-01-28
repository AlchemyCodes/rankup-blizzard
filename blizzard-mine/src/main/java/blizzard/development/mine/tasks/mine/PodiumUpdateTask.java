package blizzard.development.mine.tasks.mine;

import blizzard.development.mine.mine.adapters.PodiumAdapter;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

public class PodiumUpdateTask extends BukkitRunnable {

    private final PodiumAdapter podiumAdapter = PodiumAdapter.getInstance();
    private BukkitTask task;

    @Override
    public void run() {
        podiumAdapter.removeAllNPCs();
        podiumAdapter.createAllNPCs();
    }

    public void setTask(BukkitTask task) {
        this.task = task;
    }

    public void cancelTask() {
        if (task != null && !task.isCancelled()) {
            task.cancel();
        }
    }
}
