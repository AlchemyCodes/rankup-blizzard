package blizzard.development.mine.tasks.mine;

import blizzard.development.mine.builders.hologram.HologramBuilder;
import blizzard.development.mine.database.cache.methods.PlayerCacheMethods;
import blizzard.development.mine.mine.enums.LocationEnum;
import blizzard.development.mine.utils.locations.LocationUtils;
import blizzard.development.mine.utils.text.ProgressBarUtils;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import java.util.Arrays;

public class ExtractorUpdateTask extends BukkitRunnable {

    private static final ExtractorUpdateTask instance = new ExtractorUpdateTask();
    private BukkitTask task;

    public static ExtractorUpdateTask getInstance() {
        return instance;
    }

    @Override
    public void run() {
        for (Player player : Bukkit.getOnlinePlayers()) {
            if (PlayerCacheMethods.getInstance().isInMine(player)) {
                Location location = LocationUtils.getLocation(LocationEnum.EXTRACTOR_NPC.getName());

                if (location != null) {
                    HologramBuilder hologramBuilder = HologramBuilder.getInstance();

                    hologramBuilder.removeHologram(player.getUniqueId());
                    hologramBuilder.createPlayerHologram(
                            player,
                            player.getUniqueId(),
                            location.add(0, 4.9, 0),
                            Arrays.asList(
                                    "§e§lEXTRATORA!",
                                    "§fQuebre blocos para conseguir",
                                    "§fativar o poder da extratora.",
                                    "",
                                    " §bProgresso:",
                                    " " + ProgressBarUtils.getInstance().extractor(player),
                                    ""
                            )
                    );
                }
            }
        }
    }

    public void setTask(BukkitTask task) {
        this.task = task;
    }

    public void cancelTask() {
        try {
            if (task != null && !task.isCancelled()) {
                task.cancel();
            }
        } catch (Exception ignored) {}
    }
}
