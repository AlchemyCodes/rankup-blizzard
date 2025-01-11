package blizzard.development.core.clothing.adapters;

import blizzard.development.core.Main;
import blizzard.development.core.clothing.factory.ClothingActivatorFactory;
import blizzard.development.core.clothing.item.CommonClothingBuildItem;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class CommonClothingAdapter implements ClothingActivatorFactory {
    public void active(Player player) {
        CommonClothingBuildItem commonClothingBuildItem = new CommonClothingBuildItem();
        player.getInventory().setChestplate(commonClothingBuildItem.buildCommonClothing(Material.LEATHER_CHESTPLATE, null));
        changeColor(player);
    }

    public void changeColor(Player player) {
        new BukkitRunnable() {
            int red = 255;
            int green = 255;
            int blue = 255;
            boolean decreasing = true;

            @Override
            public void run() {
                if (decreasing) {
                    red -= 3;
                    green -= 3;
                    if (red <= 100 || green <= 100) {
                        red = green = 100;
                        decreasing = false;
                    }
                } else {
                    red += 3;
                    green += 3;
                    if (red >= 255 || green >= 255) {
                        red = green = 255;
                        decreasing = true;
                    }
                }

                Color color = Color.fromRGB(red, green, blue);

                player.getInventory().setChestplate(
                        new CommonClothingBuildItem().buildCommonClothing(Material.LEATHER_CHESTPLATE, color)
                );
            }
        }.runTaskTimerAsynchronously(Main.getInstance(), 0L, 2L);
    }
}

