package blizzard.development.mine.managers.mine;

import blizzard.development.core.Main;
import blizzard.development.mine.builders.display.PickaxeBuilder;
import blizzard.development.mine.mine.enums.LocationEnum;
import blizzard.development.mine.utils.locations.LocationUtils;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;

public class DisplayManager {

    private static final DisplayManager instance = new DisplayManager();

    public static DisplayManager getInstance() {
        return instance;
    }

    private final Material pickaxe = Material.DIAMOND_PICKAXE;
    private final float speed = 0.1f;

    public void createPickaxeDisplay(Location location) {
        PickaxeBuilder pickaxeBuilder = PickaxeBuilder.getInstance();

        pickaxeBuilder.removePickaxe();
        pickaxeBuilder.createPickaxe(
                location,
                pickaxe,
                speed // Quanto menor, mais lento
        );
    }


    public void initializePickaxeDisplay() {
        Location location = LocationUtils.getLocation(LocationEnum.DISPLAY.getName());
        if (location != null) {
            Bukkit.getScheduler().runTaskLater(
                    Main.getInstance(),
                    () -> {
                        PickaxeBuilder.getInstance().initializePickaxe(
                                location,
                                pickaxe,
                                speed // Quanto menor, mais lento
                        );
                    },
                    20L * 3 // 3 segundos
            );
        }
    }
}
