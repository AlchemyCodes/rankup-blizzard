package blizzard.development.plantations.tasks;

import blizzard.development.plantations.Main;
import blizzard.development.plantations.utils.packets.PacketUtils;
import org.bukkit.Bukkit;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitScheduler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PlantationRegenTask {

    private static final BukkitScheduler scheduler = Bukkit.getScheduler();
    private static final Map<Long, List<Object[]>> tasks = new HashMap<>();

    public static void create(Block block, Player player, int time) {
        long executionTime = System.currentTimeMillis() + (time * 1000L);

        synchronized (tasks) {
            tasks.computeIfAbsent(executionTime, k -> new ArrayList<>())
                .add(new Object[]{block, player});
        }
    }

    public static void start() {
        scheduler.runTaskTimerAsynchronously(Main.getInstance(), () -> {
            long currentTime = System.currentTimeMillis();

            List<Object[]> toProcess = new ArrayList<>();

            synchronized (tasks) {
                tasks.entrySet().removeIf(entry -> {
                    if (entry.getKey() <= currentTime) {
                        toProcess.addAll(entry.getValue());
                        return true;
                    }
                    return false;
                });
            }

            for (Object[] entry : toProcess) {
                Block block = (Block) entry[0];
                Player player = (Player) entry[1];
                sendBlockUpdate(block, player);
            }
        }, 0L, 20L);
    }

    private static void sendBlockUpdate(Block block, Player player) {
        PacketUtils.getInstance().sendPacket(player, block);
    }

}
