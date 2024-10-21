package blizzard.development.core.clothing.item;

import blizzard.development.core.Main;
import blizzard.development.core.builder.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;

public class LegendaryClothingBuildItem {

    public ItemStack buildLegendaryClothing(Material material) {
        return new ItemBuilder(material)
                .setDisplayName("§bManto de Diamante")
                .setLore(Arrays.asList(
                        "§7Este manto de diamante lhe",
                        "§7fornece 35% de proteção ao frio.",
                        "",
                        " §bEste é um manto da",
                        " §bcategoria lendária.",
                        ""
                ))
                .addPersistentData(Main.getInstance(), "manto.diamante")
                .build();
    }
}
