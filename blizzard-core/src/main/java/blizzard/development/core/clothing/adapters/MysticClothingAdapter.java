package blizzard.development.core.clothing.adapters;

import blizzard.development.core.clothing.factory.ClothingActivatorFactory;
import blizzard.development.core.clothing.item.MysticClothingBuildItem;
import org.bukkit.Material;
import org.bukkit.entity.Player;

public class MysticClothingAdapter implements ClothingActivatorFactory {
    public void active(Player player) {
        MysticClothingBuildItem mysticClothingBuildItem = new MysticClothingBuildItem();

        player.getInventory().setChestplate(mysticClothingBuildItem.buildMysticClothing(Material.IRON_CHESTPLATE));
    }
}
