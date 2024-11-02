package blizzard.development.slimeworlds.tasks;

import blizzard.development.slimeworlds.Main;
import blizzard.development.slimeworlds.managers.SchematicManager;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitScheduler;

public class SchematicTask {

    private static final BukkitScheduler scheduler = Bukkit.getScheduler();
    private final SchematicManager schematicManager = new SchematicManager();

    public void schematic(String worldName, Location location) {

        scheduler.runTaskLater(Main.getInstance(), () -> {
            World world = Bukkit.getWorld(worldName);

            if (world == null) return;

            schematicManager.pasteSchematic(world, location);
        }, 20L);
    }
}
