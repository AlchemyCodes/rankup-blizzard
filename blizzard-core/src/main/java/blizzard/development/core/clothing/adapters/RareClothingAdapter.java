package blizzard.development.core.clothing.adapters;

import blizzard.development.core.clothing.factory.ClothingActivatorFactory;
import blizzard.development.core.clothing.item.RareClothingBuildItem;
import org.bukkit.Material;
import org.bukkit.entity.Player;

public class RareClothingAdapter implements ClothingActivatorFactory {
    public void active(Player player) {
        RareClothingBuildItem rareClothingBuildItem = new RareClothingBuildItem();

        player.getInventory().setChestplate(rareClothingBuildItem.buildRareClothing(Material.CHAINMAIL_CHESTPLATE));
    }
}
