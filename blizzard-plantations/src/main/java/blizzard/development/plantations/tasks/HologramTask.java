package blizzard.development.plantations.tasks;

import blizzard.development.plantations.Main;
import blizzard.development.plantations.builder.ItemBuilder;
import blizzard.development.plantations.builder.hologram.HologramBuilder;
import eu.decentsoftware.holograms.api.DHAPI;
import eu.decentsoftware.holograms.api.holograms.Hologram;
import eu.decentsoftware.holograms.api.utils.items.HologramItem;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class HologramTask {

    public static void initializeHologramTask(Player player, Block block, Material material) {
        Location initialLocation = block.getLocation().add(0.5, 1.9, 0.5);
        UUID hologramUUID = HologramBuilder.hologram(player, block);

        new BukkitRunnable() {
            final int totalFrames = 25;
            int currentFrame = 0;
            final double maxHeight = 3;

            @Override
            public void run() {
                Hologram currentHologram = HologramBuilder.getHologram(hologramUUID);

                if (currentHologram == null) {
                    this.cancel();
                    return;
                }
                currentHologram.setDefaultVisibleState(false);
                currentHologram.setShowPlayer(player);

                double progress = (double) currentFrame / totalFrames;

                double height = progress * maxHeight;
                double wobble = Math.sin(progress * Math.PI * 2) * 0.05;

                Location newLocation = initialLocation.clone().add(
                    wobble,
                    height,
                    wobble
                );

                DHAPI.moveHologram(currentHologram, newLocation);

                List<String> newLines = new ArrayList<>();
                newLines.add("§a§l+1 §a✿");


                if (!newLines.isEmpty()) {
                    DHAPI.setHologramLines(currentHologram, newLines);
                }

                if (currentFrame >= totalFrames) {
                    HologramBuilder.removeHologram(hologramUUID);
                    this.cancel();
                }

                currentFrame++;
            }
        }.runTaskTimer(Main.getInstance(), 0L, 1L);
    }
}
