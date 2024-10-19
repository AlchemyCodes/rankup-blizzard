package blizzard.development.core.clothing.adapters;

import blizzard.development.core.clothing.factory.ClothingActivatorFactory;
import blizzard.development.core.clothing.item.RareClothingBuildItem;
import org.bukkit.Material;
import org.bukkit.entity.Player;

public class RareClothingAdapter implements ClothingActivatorFactory {
    @Override
    public void active(Player player) {

        RareClothingBuildItem rareClothingBuildItem = new RareClothingBuildItem();

        player.getInventory().setHelmet(rareClothingBuildItem.buildRareClothing(Material.CHAINMAIL_HELMET));
        player.getInventory().setChestplate(rareClothingBuildItem.buildRareClothing(Material.CHAINMAIL_CHESTPLATE));
        player.getInventory().setLeggings(rareClothingBuildItem.buildRareClothing(Material.CHAINMAIL_LEGGINGS));
        player.getInventory().setBoots(rareClothingBuildItem.buildRareClothing(Material.CHAINMAIL_BOOTS));

    }
}
