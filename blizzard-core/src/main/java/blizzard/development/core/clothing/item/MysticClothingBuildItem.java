package blizzard.development.core.clothing.item;

import blizzard.development.core.Main;
import blizzard.development.core.builder.ItemBuilder;
import java.util.Arrays;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;

public class MysticClothingBuildItem {
    public ItemStack buildMysticClothing(Material material) {
        return (new ItemBuilder(material))
                .setDisplayName("<#e6e3dc>Manto de Ferro<#e6e3dc>")
                .setLore(Arrays.asList(
                        "§7Este manto de ferro lhe",
                        "§7fornece 25% de proteção ao frio.",
                        "",
                        " <#e6e3dc>Este é um manto da<#e6e3dc>",
                        " <#e6e3dc>categoria mística.<#e6e3dc>",
                        ""
                ))
                .addPersistentData(Main.getInstance(), "manto.ferro")
                .build();
    }
}
