package blizzard.development.visuals.visuals.item;

import blizzard.development.visuals.Main;
import blizzard.development.visuals.builder.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;

public class IronVisualBuildItem {

    public static ItemStack ironVisual(int amount) {
        return new ItemBuilder(Material.IRON_INGOT)
            .setDisplayName("§fSkin de §lFerro §7(1.9§lx§7)")
            .setLore(Arrays.asList(
                "§7Aplique essa skin",
                "§7na sua ferramenta.",
                "",
                " §fBônus: §f1.9§lx",
                "",
                " §7Deve ser aplicado em",
                " §7picaretas e cultivadoras.",
                "",
                "§fArraste para aplicar."
            ))
            .addPersistentData(Main.getInstance(), "visual", "blizzard.visuals.iron-visual")
            .build(amount);
    }
}
