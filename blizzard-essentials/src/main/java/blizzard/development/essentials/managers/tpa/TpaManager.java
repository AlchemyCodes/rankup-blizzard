package blizzard.development.essentials.managers.tpa;

import blizzard.development.essentials.Main;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitTask;

import java.util.HashMap;

public class TpaManager {

    public HashMap<Player, Player> tpaCacheManager = new HashMap<>();
    public HashMap<Player, BukkitTask> schedulerTasks = new HashMap<>();
    private static TpaManager instance;

    public void add(Player player, Player target) {
        tpaCacheManager.put(player, target);

        BukkitTask existingTask = schedulerTasks.get(player);
        if (existingTask != null) {
            existingTask.cancel();
        }

        BukkitTask task = Bukkit.getScheduler().runTaskLaterAsynchronously(Main.getInstance(), () -> {
            if (contains(player)) {

                player.sendActionBar("§c§lEI! §cA sua solicitação de teletransporte expirou.");
                remove(player);

                schedulerTasks.remove(player);
            }
        }, 20L * 10);

        schedulerTasks.put(player, task);
    }

    public void remove(Player player) {
        tpaCacheManager.remove(player);

        BukkitTask task = schedulerTasks.get(player);
        if (task != null) {
            task.cancel();
            schedulerTasks.remove(player);
        }
    }

    public boolean contains(Player player) {
        return tpaCacheManager.containsKey(player);
    }

    public Player get(Player player) {
        return tpaCacheManager.get(player);
    }

    public static TpaManager getInstance() {
        if (instance == null) {
            instance = new TpaManager();
        }
        return instance;
    }
}