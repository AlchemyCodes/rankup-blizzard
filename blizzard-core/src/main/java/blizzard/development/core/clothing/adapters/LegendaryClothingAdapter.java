package blizzard.development.core.clothing.adapters;

import blizzard.development.core.clothing.factory.ClothingActivatorFactory;
import blizzard.development.core.clothing.item.LegendaryClothingBuildItem;
import org.bukkit.Material;
import org.bukkit.entity.Player;

public class LegendaryClothingAdapter implements ClothingActivatorFactory {
    public void active(Player player) {
        LegendaryClothingBuildItem legendaryClothingBuildItem = new LegendaryClothingBuildItem();

        player.getInventory().setChestplate(legendaryClothingBuildItem.buildLegendaryClothing(Material.DIAMOND_CHESTPLATE));
    }
}
