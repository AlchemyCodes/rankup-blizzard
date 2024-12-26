package blizzard.development.plantations.utils.displayentity;

import blizzard.development.plantations.Main;
import blizzard.development.plantations.utils.PluginImpl;
import blizzard.development.plantations.utils.TextUtils;
import net.kyori.adventure.text.Component;
import org.bukkit.*;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Display.Billboard;
import org.bukkit.entity.ItemDisplay;
import org.bukkit.entity.TextDisplay;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.util.Transformation;
import org.joml.Vector3f;
import org.joml.Quaternionf;

public class DisplayEntityUtils {

    private static Location displayLocation;
    private static ArmorStand armorStand;
    private static ItemDisplay itemDisplay;
    private static TextDisplay textDisplay;

    public static void initialize() {
        loadDisplayLocation();
        createDisplay();
    }

    private static void loadDisplayLocation() {
        String world = PluginImpl.getInstance().Locations.getConfig().getString("display.location.world", "world");
        double x = PluginImpl.getInstance().Locations.getConfig().getDouble("display.location.x", 0);
        double y = PluginImpl.getInstance().Locations.getConfig().getDouble("display.location.y", 64);
        double z = PluginImpl.getInstance().Locations.getConfig().getDouble("display.location.z", 0);
        float yaw = (float) PluginImpl.getInstance().Locations.getConfig().getDouble("display.location.yaw", 0);
        float pitch = (float) PluginImpl.getInstance().Locations.getConfig().getDouble("display.location.pitch", 0);

        World bukkitWorld = Bukkit.getWorld(world);
        if (bukkitWorld == null) {
            return;
        }

        displayLocation = new Location(bukkitWorld, x, y, z, yaw, pitch);
    }

    private static void saveDisplayLocation(Location location) {
        PluginImpl.getInstance().Locations.getConfig().set("display.location.world", location.getWorld().getName());
        PluginImpl.getInstance().Locations.getConfig().set("display.location.x", location.getX());
        PluginImpl.getInstance().Locations.getConfig().set("display.location.y", location.getY());
        PluginImpl.getInstance().Locations.getConfig().set("display.location.z", location.getZ());
        PluginImpl.getInstance().Locations.getConfig().set("display.location.yaw", location.getYaw());
        PluginImpl.getInstance().Locations.getConfig().set("display.location.pitch", location.getPitch());
        PluginImpl.getInstance().Locations.saveConfig();
    }

    public static void createDisplay() {
        if (displayLocation == null) {
            return;
        }


        armorStand = displayLocation.getWorld().spawn(displayLocation, ArmorStand.class);
        armorStand.setInvisible(true);
        armorStand.setGravity(false);
        armorStand.setMarker(false);
        armorStand.getPersistentDataContainer().set(new NamespacedKey(Main.getInstance(), "blizzard.plantations.estufa-display"), PersistentDataType.STRING, "estufa-display");

        Location itemLocation = armorStand.getLocation().add(0, 1, 0);
        itemDisplay = itemLocation.getWorld().spawn(itemLocation, ItemDisplay.class);


        Quaternionf rotation = new Quaternionf().rotateXYZ(0, 0, 0);
        itemDisplay.setTransformation(new org.bukkit.util.Transformation(
            new Vector3f(0f, 0f, 0f),
            rotation,
            new Vector3f(1.6f, 1.6f, 1.6f),
            new Quaternionf()
        ));

        itemDisplay.setItemStack(new ItemStack(Material.PINK_GLAZED_TERRACOTTA));
        startRotationAnimation();

        Location titleLocation = itemDisplay.getLocation().add(0, 1.6, 0);
        textDisplay = (TextDisplay) titleLocation.getWorld().spawn(titleLocation, TextDisplay.class);


        Component mainTitle = TextUtils.parse("""
                            
                    <bold><#FF55FF>Geren<#fa7dfa><#fa7dfa>ciador da<#fa7dfa> <#fa7dfa>Estufa<#FF55FF></bold>
                            
                     §fTorne sua área mais eficiente com upgrades
                     
                    §dClique para abrir o menu.
                    """);

        textDisplay.text(mainTitle);
        textDisplay.setBillboard(Billboard.FIXED);
        textDisplay.setTransformation(new org.bukkit.util.Transformation(
            new Vector3f(0f, 0f, 0f),
            new Quaternionf(),
            new Vector3f(1.2f, 1.2f, 1.2f),
            new Quaternionf()
        ));

    }

    private static void startRotationAnimation() {
        final long[] tick = {0};
        Bukkit.getScheduler().runTaskTimer(Main.getInstance(), task -> {
            if (itemDisplay == null || !itemDisplay.isValid()) {
                task.cancel();
                return;
            }

            float baseSpeed = 0.05f;
            float xRotation = (float) (baseSpeed * (1 + Math.sin(tick[0] * 0.1) * 0.5));
            float yRotation = (float) (baseSpeed * (1 + Math.cos(tick[0] * 0.15) * 0.5));
            float zRotation = (float) (baseSpeed * (1 + Math.sin(tick[0] * 0.2) * 0.5));

            Quaternionf currentRotation = itemDisplay.getTransformation().getLeftRotation();
            Quaternionf newRotation = new Quaternionf(currentRotation)
                .rotateX(xRotation)
                .rotateY(yRotation)
                .rotateZ(zRotation);

            Transformation currentTransform = itemDisplay.getTransformation();
            itemDisplay.setTransformation(new Transformation(
                currentTransform.getTranslation(),
                newRotation,
                currentTransform.getScale(),
                currentTransform.getRightRotation()
            ));

            tick[0]++;
        }, 0L, 1L);
    }

    public static void removeDisplay() {
        if (armorStand != null) {
            armorStand.remove();
            armorStand = null;
        }
        if (itemDisplay != null) {
            itemDisplay.remove();
            itemDisplay = null;
        }
        if (textDisplay != null) {
            textDisplay.remove();
            textDisplay = null;
        }
    }

    public static void setDisplayLocation(Location location) {
        displayLocation = location;
        saveDisplayLocation(location);
        createDisplay();
    }
}

