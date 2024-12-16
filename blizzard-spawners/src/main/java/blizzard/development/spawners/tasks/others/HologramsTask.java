package blizzard.development.spawners.tasks.others;

import blizzard.development.spawners.builders.spawners.HologramBuilder;
import blizzard.development.spawners.builders.ItemBuilder;
import blizzard.development.spawners.utils.PluginImpl;
import eu.decentsoftware.holograms.api.DHAPI;
import eu.decentsoftware.holograms.api.holograms.Hologram;
import eu.decentsoftware.holograms.api.utils.items.HologramItem;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class HologramsTask {
    public static void initializeHologramTask(Player player, Location location, Material material, String display) {
        Location initialLocation = location.clone().add(0, 0.5, 0);
        UUID hologramUUID = HologramBuilder.hologram(player, initialLocation);

        new BukkitRunnable() {
            final int totalFrames = 20;
            int currentFrame = 0;
            final double maxHeight = 0.5;

            @Override
            public void run() {
                Hologram currentHologram = HologramBuilder.getHologram(hologramUUID);

                if (currentHologram == null) {
                    this.cancel();
                    return;
                }

                double progress = (double) currentFrame / totalFrames;

                double height = Math.min(progress * maxHeight, maxHeight);

                Location newLocation = initialLocation.clone().add(
                        0,
                        height,
                        0
                );

                DHAPI.moveHologram(currentHologram, newLocation);

                List<String> newLines = new ArrayList<>();
                newLines.add("#ICON:" + HologramItem.fromItemStack(new ItemBuilder(material).build()).getContent());
                newLines.add(display);

                DHAPI.setHologramLines(currentHologram, newLines);

                if (currentFrame >= totalFrames) {
                    HologramBuilder.removeHologram(hologramUUID);
                    this.cancel();
                }

                currentFrame++;
            }
        }.runTaskTimer(PluginImpl.getInstance().plugin, 0L, 2L);
    }
}
