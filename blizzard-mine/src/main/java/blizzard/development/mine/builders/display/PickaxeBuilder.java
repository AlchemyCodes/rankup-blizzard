package blizzard.development.mine.builders.display;

import blizzard.development.core.Main;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.ItemDisplay;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Transformation;
import org.joml.Quaternionf;
import org.joml.Vector3f;

public class PickaxeBuilder {

    private static final PickaxeBuilder instance = new PickaxeBuilder();

    public static PickaxeBuilder getInstance() {
        return instance;
    }

    private ItemDisplay currentDisplay;

    public void removePickaxe() {
        if (currentDisplay != null && currentDisplay.isValid()) {
            try {
                currentDisplay.remove();
            } catch (Exception e) {
                e.printStackTrace();
            }
            currentDisplay = null;
        }
    }

    public ItemDisplay createPickaxe(Location location, Material material, float speed) {
        removePickaxe();

        currentDisplay = location.getWorld().spawn(location, ItemDisplay.class);
        currentDisplay.setItemStack(new ItemStack(material));

        Quaternionf initialRotation = new Quaternionf().rotateXYZ(0, 0, 0);
        currentDisplay.setTransformation(new Transformation(
                new Vector3f(0f, 0f, 0f),
                initialRotation,
                new Vector3f(10f, 10f, 10f),
                new Quaternionf()
        ));

        new BukkitRunnable() {
            int ticks = 0;

            @Override
            public void run() {
                if (currentDisplay == null || !currentDisplay.isValid()) {
                    cancel();
                    currentDisplay = null;
                    return;
                }

                float angle = ticks * speed;
                Quaternionf rotation = new Quaternionf().rotateY(angle);

                currentDisplay.setTransformation(new Transformation(
                        new Vector3f(0f, 0f, 0f),
                        rotation,
                        new Vector3f(10f, 10f, 10f),
                        new Quaternionf()
                ));

                ticks++;
            }
        }.runTaskTimer(Main.getInstance(), 0L, 1L);

        return currentDisplay;
    }

    public ItemDisplay initializePickaxe(Location location, Material material, float speed) {
        if (currentDisplay == null) {
            return createPickaxe(location, material, speed);
        }
        return currentDisplay;
    }
}