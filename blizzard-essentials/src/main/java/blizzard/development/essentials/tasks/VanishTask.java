package blizzard.development.essentials.tasks;

import blizzard.development.essentials.Main;
import net.kyori.adventure.text.Component;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import java.util.HashMap;
import java.util.Map;

import static blizzard.development.essentials.commands.staff.VanishCommand.vanishedPlayers;

public class VanishTask {

    private static final Map<Player, BukkitTask> playerTasks = new HashMap<>();

    public static void create(Player p) {
        BukkitTask task = new BukkitRunnable() {
            @Override
            public void run() {
                p.sendActionBar(Component.text("§bVocê agora está invisível para outros jogadores [§l" + vanishedPlayers.size() + "§b]."));
            }
        }.runTaskTimerAsynchronously(Main.getInstance(), 0L, 20 * 2);

        playerTasks.put(p, task);
    }
    public static void cancel(Player p) {
        BukkitTask task = playerTasks.remove(p);
        if (task != null) {
            task.cancel();
        }
    }
}
