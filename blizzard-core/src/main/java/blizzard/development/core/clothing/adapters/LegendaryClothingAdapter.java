package blizzard.development.core.clothing.adapters;

import blizzard.development.core.clothing.factory.ClothingActivatorFactory;
import blizzard.development.core.clothing.item.LegendaryClothingBuildItem;
import org.bukkit.Material;
import org.bukkit.entity.Player;

public class LegendaryClothingAdapter implements ClothingActivatorFactory {
    public void active(Player player) {
        LegendaryClothingBuildItem legendaryClothingBuildItem = new LegendaryClothingBuildItem();

        player.getInventory().setHelmet(legendaryClothingBuildItem.buildLegendaryClothing(Material.DIAMOND_HELMET));
        player.getInventory().setChestplate(legendaryClothingBuildItem.buildLegendaryClothing(Material.DIAMOND_CHESTPLATE));
        player.getInventory().setLeggings(legendaryClothingBuildItem.buildLegendaryClothing(Material.DIAMOND_LEGGINGS));
        player.getInventory().setBoots(legendaryClothingBuildItem.buildLegendaryClothing(Material.DIAMOND_BOOTS));
    }
}
