package blizzard.development.crates.builder;

import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.entity.Display;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.TextDisplay;
import org.bukkit.util.Transformation;

import java.util.ArrayList;
import java.util.List;

public class HologramBuilder {

    private static final List<TextDisplay> holograms = new ArrayList<>();

    public void createHologram(String hologramName, Location location, String text) {

        TextDisplay hologram = (TextDisplay) location.getWorld().spawnEntity(location, EntityType.TEXT_DISPLAY);
        hologram.setCustomName(hologramName);
        hologram.setCustomNameVisible(false);
        hologram.setBillboard(Display.Billboard.CENTER);
        hologram.setText(text);
        hologram.setBackgroundColor(Color.fromARGB(0, 0, 0, 0));
        hologram.setShadowed(true);

        Transformation transformation = hologram.getTransformation();
        transformation.getScale().add(1, 1, 1);

        hologram.setTransformation(transformation);

        holograms.add(hologram);

    }



    public static void removeHologram() {
        for (TextDisplay hologram : holograms) {
            hologram.remove();
        }
        holograms.clear();
    }
}
