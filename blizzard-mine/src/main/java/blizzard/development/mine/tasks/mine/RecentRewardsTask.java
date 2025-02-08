package blizzard.development.mine.tasks.mine;

import blizzard.development.mine.database.cache.methods.PlayerCacheMethods;
import blizzard.development.mine.utils.text.NumberUtils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class RecentRewardsTask extends BukkitRunnable {

    private static final RecentRewardsTask instance = new RecentRewardsTask();
    private BukkitTask task;

    private final ConcurrentHashMap<UUID, Double> recentCoins = new ConcurrentHashMap<>();
    private final ConcurrentHashMap<UUID, Double> recentBlocks = new ConcurrentHashMap<>();

    public static RecentRewardsTask getInstance() {
        return instance;
    }

    @Override
    public void run() {
        Bukkit.getOnlinePlayers().forEach(player -> {
            if (PlayerCacheMethods.getInstance().isInMine(player)) {
                UUID uuid = player.getUniqueId();

                double money = recentCoins.getOrDefault(uuid, 0.0);
                double blocks = recentBlocks.getOrDefault(uuid, 0.0);

                if (money > 0 || blocks > 0) {
                    List<String> message = Arrays.asList(
                            "",
                            "§e§l WOW! Confira seus ganhos na mina §7[3 minutos].",
                            "",
                            "§2§l  $§a" + NumberUtils.getInstance().formatNumber(money) + " Coins.",
                            "§b§l  ❒§b" + NumberUtils.getInstance().formatNumber(blocks) + " Blocos.",
                            "",
                            "§f Continue minerando para obter ganhos."
                    );

                    message.forEach(player::sendMessage);
                }

                removeRewards(player);
            }
        });
    }

    public void addRewards(Player player, Double money, Double blocks) {
        if (player == null || !player.isOnline()) return;

        UUID uuid = player.getUniqueId();

        recentCoins.merge(uuid, money, Double::sum);
        recentBlocks.merge(uuid, blocks, Double::sum);
    }

    private void removeRewards(Player player) {
        if (player == null) return;
        recentCoins.remove(player.getUniqueId());
        recentBlocks.remove(player.getUniqueId());
    }

    public BukkitTask getTask() {
        return task;
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