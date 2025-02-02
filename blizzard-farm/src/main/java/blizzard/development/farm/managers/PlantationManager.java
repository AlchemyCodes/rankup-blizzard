package blizzard.development.farm.managers;

import blizzard.development.farm.utils.PluginImpl;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.HashMap;
import java.util.Map;

public class PlantationManager {

    private static final PlantationManager instance = new PlantationManager();
    private final Queue<ReplantTask> replantQueue;
    private final Map<Location, Material> plantCache;
    private static final int BATCH_SIZE = 20;
    private static final long REPLANT_DELAY = 80L; // 1 segundo de delay
    private boolean isProcessing;

    private PlantationManager() {
        this.replantQueue = new ConcurrentLinkedQueue<>();
        this.plantCache = new HashMap<>();
        this.isProcessing = false;
        startProcessor();
    }

    public static PlantationManager getInstance() {
        return instance;
    }

    private record ReplantTask(Location location, Material cropType) {
    }

    public void queueReplant(Block block, Material cropType) {
        Location loc = block.getLocation();

        if (plantCache.containsKey(loc)) {
            return;
        }

        plantCache.put(loc, cropType);
        replantQueue.offer(new ReplantTask(loc, cropType));

        if (!isProcessing) {
            processQueue();
        }
    }

    private void startProcessor() {
        new BukkitRunnable() {
            @Override
            public void run() {
                processQueue();
            }
        }.runTaskTimer(PluginImpl.getInstance().plugin, REPLANT_DELAY, REPLANT_DELAY);
    }

    private void processQueue() {
        if (replantQueue.isEmpty() || isProcessing) {
            return;
        }

        isProcessing = true;

        new BukkitRunnable() {
            @Override
            public void run() {
                int processed = 0;

                while (!replantQueue.isEmpty() && processed < BATCH_SIZE) {
                    ReplantTask task = replantQueue.poll();
                    if (task == null) continue;

                    Block block = task.location.getBlock();

                    if (block.getType() == Material.AIR) {
                        replantCrop(block, task.cropType);
                        processed++;
                    }

                    plantCache.remove(task.location);
                }

                isProcessing = !replantQueue.isEmpty();

                if (isProcessing) {
                    new BukkitRunnable() {
                        @Override
                        public void run() {
                            processQueue();
                        }
                    }.runTaskLater(PluginImpl.getInstance().plugin, REPLANT_DELAY);
                }
            }
        }.runTask(PluginImpl.getInstance().plugin);
    }

    private void replantCrop(Block block, Material cropType) {
        Material belowType = block.getLocation().subtract(0, 1, 0).getBlock().getType();

        if (!isValidSoil(belowType, cropType)) {
            return;
        }

        block.setType(cropType);
    }

    private boolean isValidSoil(Material soil, Material crop) {
        switch (crop) {
            case WHEAT:
            case CARROTS:
            case POTATOES:
                return soil == Material.FARMLAND;
            case CACTUS:
                return soil == Material.SAND;
            case MELON_STEM:
            case PUMPKIN_STEM:
                return soil == Material.FARMLAND;
            default:
                return false;
        }
    }

    public void clearCache() {
        plantCache.clear();
        replantQueue.clear();
        isProcessing = false;
    }
}
