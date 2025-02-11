package blizzard.development.core.inventories;

import blizzard.development.core.Main;
import blizzard.development.core.builder.ItemBuilder;
import blizzard.development.core.clothing.ClothingType;
import blizzard.development.core.clothing.adapters.CommonClothingAdapter;
import blizzard.development.core.clothing.adapters.LegendaryClothingAdapter;
import blizzard.development.core.clothing.adapters.MysticClothingAdapter;
import blizzard.development.core.clothing.adapters.RareClothingAdapter;
import blizzard.development.core.managers.GeneratorManager;
import com.github.stefvanschie.inventoryframework.gui.GuiItem;
import com.github.stefvanschie.inventoryframework.gui.type.ChestGui;
import com.github.stefvanschie.inventoryframework.pane.StaticPane;
import com.github.stefvanschie.inventoryframework.pane.util.Slot;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public class GeneratorInventory {
    public static void open(Player player) {
        ChestGui inventory = new ChestGui(3, "Gerador");
        StaticPane pane = new StaticPane(0, 0, 9, 3);

        GuiItem cancel = new GuiItem(cancel(), event -> {
            event.setCancelled(true);
            GeneratorManager.getInstance().activeGenerator(player);
            player.sendMessage("ativou");
            player.closeInventory();
        });

        pane.addItem(cancel, Slot.fromIndex(16));

        inventory.addPane(pane);
        inventory.show(player);
    }

    public static ItemStack cancel() {
        return new ItemBuilder(Material.COAL)
                .setDisplayName("§6COMBUSTÍVEL")
                .setLore(List.of(
                        "§7Ative seu gerador",
                        "usando esse combustível.",
                        "",
                        "§6Clique para ativar."))
                .build();
    }
}
