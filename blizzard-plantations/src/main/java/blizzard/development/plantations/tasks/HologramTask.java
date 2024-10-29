package blizzard.development.plantations.tasks;

import blizzard.development.plantations.Main;
import blizzard.development.plantations.builder.ItemBuilder;
import blizzard.development.plantations.builder.hologram.HologramBuilder;
import blizzard.development.plantations.plantations.enums.SeedEnum;
import eu.decentsoftware.holograms.api.DHAPI;
import eu.decentsoftware.holograms.api.holograms.Hologram;
import eu.decentsoftware.holograms.api.utils.items.HologramItem;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class HologramTask {

    public static void initializeHologramTask(Player player, Block block, SeedEnum seedEnum) {
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
                switch (seedEnum.getName()) {
                    case "alface":
                        newLines.add("#ICON:" + HologramItem.fromItemStack(new ItemBuilder("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZTUxOTY5NjlhZmNjMTk0OWMzNTM4Njk3Y2RkNWIxOTE5N2ZhMzg1MTYxMzQ2OGRiZDU1ZDAzMTUzODk5YjYifX19").build()).getContent());
                        newLines.add("§a§l+1 §aAlface.");
                        DHAPI.setHologramLines(currentHologram, newLines);
                        break;
                    case "morango":
                        newLines.add("#ICON:" + HologramItem.fromItemStack(new ItemBuilder("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvY2JjODI2YWFhZmI4ZGJmNjc4ODFlNjg5NDQ0MTRmMTM5ODUwNjRhM2Y4ZjA0NGQ4ZWRmYjQ0NDNlNzZiYSJ9fX0=").build()).getContent());
                        newLines.add("§c§l+1 §cMorango.");
                        DHAPI.setHologramLines(currentHologram, newLines);
                        break;
                    case "tomate":
                        newLines.add("#ICON:" + HologramItem.fromItemStack(new ItemBuilder("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNGYxNGI1OGYzZGY2NWEwYzZiOTBlZTE5NDY0YjI1NTdjODNhZTJjOWZhMWI1NzM4YmIxMTM2NGNkOWY1YjNlMSJ9fX0=").build()).getContent());
                        newLines.add("§c§l+1 §aTomate.");
                        DHAPI.setHologramLines(currentHologram, newLines);
                        break;
                    case "batata":
                        newLines.add("#ICON:" + HologramItem.fromItemStack(new ItemBuilder("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvY2Y0NjI0ZWJmN2Q0MTlhMTFlNDNlZDBjMjAzOGQzMmNkMDlhZDFkN2E2YzZlMjBmNjMzOWNiY2ZlMzg2ZmQxYyJ9fX0=").build()).getContent());
                        newLines.add("§e§l+1 §eBatata.");
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
