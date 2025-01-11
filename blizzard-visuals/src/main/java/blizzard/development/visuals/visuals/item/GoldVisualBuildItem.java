package blizzard.development.visuals.visuals.item;

import blizzard.development.visuals.Main;
import blizzard.development.visuals.builder.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;

public class GoldVisualBuildItem {

    public static ItemStack goldVisual(int amount) {
        return new ItemBuilder(Material.GOLD_INGOT)
            .setDisplayName("§6Skin de §lOuro §7(2.6§lx§7)")
            .setLore(Arrays.asList(
                "§7Aplique essa skin",
                "§7na sua ferramenta.",
                "",
                " §fBônus: §62.6§lx",
                "",
                " §7Deve ser aplicado em",
                " §7picaretas e cultivadoras.",
                "",
                "§6Arraste para aplicar."
            ))
            .addPersistentData(Main.getInstance(), "visual", "blizzard.visuals.gold-visual")
            .build(amount);
    }
}
