package blizzard.development.mine.mine.item;

import blizzard.development.core.Main;
import blizzard.development.mine.builders.item.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;

public class EnchantmentBuildItem {

    public static ItemStack enchantment(Player player) {

        return new ItemBuilder(Material.END_CRYSTAL)
            .setDisplayName("§dEncantamentos")
            .setLore(Arrays.asList(
                "§7Melhore a sua picareta",
                "§7com encantamentos especiais.",
                "",
                "§dPressione o b. direito."
            ))
            .addEnchant(Enchantment.DURABILITY, 1000, true)
            .addPersistentData(Main.getInstance(), "blizzard.mine.enchantment", player.getName())
            .build(1);
    }
}
