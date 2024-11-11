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
        Location initialLocation = block.getLocation().add(0.5, 0.5, 0.5);
        UUID hologramUUID = HologramBuilder.hologram(player, block);

        new BukkitRunnable() {
            final int totalFrames = 25;
            int currentFrame = 0;
            final double maxHeight = 1.5;

            @Override
            public void run() {
                Hologram currentHologram = HologramBuilder.getHologram(hologramUUID);

                if (currentHologram == null) {
                    this.cancel();
                    return;
                }

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
                switch (material) {
                    case POTATOES:
                        newLines.add("#ICON:" + HologramItem.fromItemStack(new ItemBuilder("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvY2Y0NjI0ZWJmN2Q0MTlhMTFlNDNlZDBjMjAzOGQzMmNkMDlhZDFkN2E2YzZlMjBmNjMzOWNiY2ZlMzg2ZmQxYyJ9fX0=").build()).getContent());
                        newLines.add("§e§l+1 §eBatata");
                        break;
                    case CARROTS:
                        newLines.add("#ICON:" + HologramItem.fromItemStack(new ItemBuilder("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNGQzYTZiZDk4YWMxODMzYzY2NGM0OTA5ZmY4ZDJkYzYyY2U4ODdiZGNmM2NjNWIzODQ4NjUxYWU1YWY2YiJ9fX0=").build()).getContent());
                        newLines.add("§6§l+1 §6Cenoura");
                        break;
                    case BEETROOTS:
                        newLines.add("#ICON:" + HologramItem.fromItemStack(new ItemBuilder("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNGYxNGI1OGYzZGY2NWEwYzZiOTBlZTE5NDY0YjI1NTdjODNhZTJjOWZhMWI1NzM4YmIxMTM2NGNkOWY1YjNlMSJ9fX0=").build()).getContent());
                        newLines.add("§c§l+1 §cTomate");
                        break;
                    case WHEAT:
                        newLines.add("#ICON:" + HologramItem.fromItemStack(new ItemBuilder("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZDNhNmIwOTljZDQwMWUzYTBkNjRkOWExNmY0NmNkMGM1Y2E1ZjdlNDVlNmE2OWMyN2QyZTQ3Mzc3NWIyNWZlIn19fQ==").build()).getContent());
                        newLines.add("§e§l+1 §eMilho");
                        break;
                }

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
