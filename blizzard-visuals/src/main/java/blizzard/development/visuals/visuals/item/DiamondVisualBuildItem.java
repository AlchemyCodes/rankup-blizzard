package blizzard.development.visuals.visuals.item;

import blizzard.development.visuals.Main;
import blizzard.development.visuals.builder.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;

public class DiamondVisualBuildItem {

    public static ItemStack diamondVisual(int amount) {
        return new ItemBuilder(Material.DIAMOND)
            .setDisplayName("§bSkin de §lDiamante §7(3.3§lx§7)")
            .setLore(Arrays.asList(
                "§7Aplique essa skin",
                "§7na sua ferramenta.",
                "",
                " §fBônus: §b3.3§lx",
                "",
                " §7Deve ser aplicado em",
                " §7picaretas e cultivadoras.",
                "",
                "§bArraste para aplicar."
            ))
            .addPersistentData(Main.getInstance(), "stone", "blizzard.visuals.diamond-visual")
            .build(amount);
    }
}
