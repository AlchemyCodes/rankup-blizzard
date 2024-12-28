package blizzard.development.visuals.visuals.item;

import blizzard.development.visuals.Main;
import blizzard.development.visuals.builder.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;

public class StoneVisualBuildItem {

    public static ItemStack stoneVisual(int amount) {
        return new ItemBuilder(Material.GRAY_DYE)
            .setDisplayName("§8Skin de §lPedra §7(1.1§lx§7)")
            .setLore(Arrays.asList(
                "§7Aplique essa skin",
                "§7na sua ferramenta.",
                "",
                " §fBônus: §81.1§lx",
                "",
                " §7Deve ser aplicado em",
                " §7picaretas e cultivadoras.",
                "",
                "§8Arraste para aplicar."
            ))
            .addPersistentData(Main.getInstance(), "stone", "blizzard.visuals.stone-visual")
            .build(amount);
    }
}
