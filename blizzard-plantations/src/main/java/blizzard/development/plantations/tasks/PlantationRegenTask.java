package blizzard.development.plantations.tasks;

import blizzard.development.plantations.Main;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.scheduler.BukkitScheduler;

public class PlantationRegenTask {

    private static final BukkitScheduler scheduler = Bukkit.getScheduler();

    public static void create(Block block, Material material, int time) {

        scheduler.runTaskLater(Main.getInstance(), () -> {
            block.setType(
                    material
            );

        }, (time * 20L));

    }
}
