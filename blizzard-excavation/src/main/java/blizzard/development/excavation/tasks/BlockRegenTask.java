package blizzard.development.excavation.tasks;

import blizzard.development.excavation.Main;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.scheduler.BukkitScheduler;


public class BlockRegenTask {

    private static final BukkitScheduler scheduler = Bukkit.getScheduler();

    public static void create(Block block, Material material, int time) {

        scheduler.runTaskLater(Main.getInstance(), () -> {
            block.setType(
                    material
            );

        }, (time * 20L));

    }
}
