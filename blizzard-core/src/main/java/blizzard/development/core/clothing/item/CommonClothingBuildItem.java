package blizzard.development.core.clothing.item;

import blizzard.development.core.Main;
import blizzard.development.core.builder.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;

public class CommonClothingBuildItem {

    public ItemStack buildCommonClothing(Material material) {
        return new ItemBuilder(material)
                .setDisplayName("§cManto de Couro")
                .setLore(Arrays.asList(
                        "§7Este manto de couro lhe",
                        "§7fornece 5% de proteção ao frio.",
                        "",
                        " §cEste é um manto da",
                        " §ccategoria comum.",
                        ""
                ))
                .addPersistentData(Main.getInstance(), "manto.couro")
                .build();
    }
}
