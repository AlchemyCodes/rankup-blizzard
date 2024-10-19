package blizzard.development.core.clothing.adapters;

import blizzard.development.core.clothing.factory.ClothingActivatorFactory;
import blizzard.development.core.clothing.item.CommonClothingBuildItem;
import blizzard.development.core.clothing.item.MysticClothingBuildItem;
import org.bukkit.Material;
import org.bukkit.entity.Player;

public class CommonClothingAdapter implements ClothingActivatorFactory {
    @Override
    public void active(Player player) {

        CommonClothingBuildItem commonClothingBuildItem = new CommonClothingBuildItem();

        player.getInventory().setHelmet(commonClothingBuildItem.buildCommonClothing(Material.LEATHER_HELMET));
        player.getInventory().setChestplate(commonClothingBuildItem.buildCommonClothing(Material.LEATHER_CHESTPLATE));
        player.getInventory().setLeggings(commonClothingBuildItem.buildCommonClothing(Material.LEATHER_LEGGINGS));
        player.getInventory().setBoots(commonClothingBuildItem.buildCommonClothing(Material.LEATHER_BOOTS));
    }
}
