package blizzard.development.core.clothing.item;

import blizzard.development.core.Main;
import blizzard.development.core.builder.ItemBuilder;
import java.util.Arrays;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;

public class RareClothingBuildItem {
    public ItemStack buildRareClothing(Material material) {
        return (new ItemBuilder(material))
                .setDisplayName("<#e6e3dc>Manto de Malha<#e6e3dc>")
                .setLore(Arrays.asList(
                        "§7Este manto de malha lhe",
                        "§7fornece 15% de proteção ao frio.",
                        "",
                        " <#c4c4c4>Este é um manto da<#c4c4c4>",
                        " <#c4c4c4>categoria rara.<#c4c4c4>",
                        ""
                ))
                .addPersistentData((Plugin) Main.getInstance(), "manto.malha")
                .build();
    }
}
