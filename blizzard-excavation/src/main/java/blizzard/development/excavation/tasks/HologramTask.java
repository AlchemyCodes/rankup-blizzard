package blizzard.development.excavation.tasks;

import blizzard.development.excavation.Main;
import blizzard.development.excavation.builder.hologram.HologramBuilder;
import eu.decentsoftware.holograms.api.DHAPI;
import eu.decentsoftware.holograms.api.holograms.Hologram;
import eu.decentsoftware.holograms.api.utils.items.HologramItem;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.UUID;
import java.util.ArrayList;
import java.util.List;

public class HologramTask {

    public void initializeHologramTask(Player player, Block block) {
        Location initialLocation = block.getLocation().add(0.5, 1, 0.5);
        UUID hologramUUID = HologramBuilder.hologram(player, block);

        new BukkitRunnable() {
            double height = 0;
            final double maxHeight = 2;
            final int totalFrames = 90;
            int currentFrame = 0;
            final double spiralRadius = 0.5;

            @Override
            public void run() {
                Hologram currentHologram = HologramBuilder.getHologram(hologramUUID);

                if (currentHologram == null) {
                    this.cancel();
                    return;
                }

                height = (Math.sin((Math.PI / totalFrames) * currentFrame * 4) * 0.5 + 1) * maxHeight;

                double angle = (2 * Math.PI / totalFrames) * currentFrame;
                double xOffset = Math.cos(angle) * spiralRadius;
                double zOffset = Math.sin(angle) * spiralRadius;

                double scale = height / maxHeight;
                xOffset *= scale;
                zOffset *= scale;

                Location newLocation = initialLocation.clone().add(xOffset, height, zOffset);
                DHAPI.moveHologram(currentHologram, newLocation);

                List<String> newLines = new ArrayList<>();
                newLines.add("#ICON:" + HologramItem.fromItemStack(new ItemStack(Material.BONE)).getContent());
                newLines.add("§f§l+1 §fFóssil.");
                DHAPI.setHologramLines(currentHologram, newLines);

                if (currentFrame >= totalFrames) {
                    HologramBuilder.removeHologram(hologramUUID);
                    this.cancel();
                }

                currentFrame++;
            }
        }.runTaskTimer(Main.getInstance(), 0L, 1L);
    }
}