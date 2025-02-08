package blizzard.development.mine.inventories.extractor;

import blizzard.development.mine.builders.item.ItemBuilder;
import blizzard.development.mine.utils.text.ProgressBarUtils;
import com.github.stefvanschie.inventoryframework.gui.GuiItem;
import com.github.stefvanschie.inventoryframework.gui.type.ChestGui;
import com.github.stefvanschie.inventoryframework.pane.StaticPane;
import com.github.stefvanschie.inventoryframework.pane.util.Slot;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public class ExtractorInventory {

    public void open(Player player) {
        ChestGui inventory = new ChestGui(3, "Extratora");

        StaticPane pane = new StaticPane(0, 0, 9, 3);

        GuiItem meteorItem = new GuiItem(createInfoItem(player), event -> {
            event.setCancelled(true);
        });

        pane.addItem(meteorItem, Slot.fromIndex(13));

        inventory.addPane(pane);
        inventory.show(player);
    }

    private ItemStack createInfoItem(Player player) {
        return new ItemBuilder(Material.END_CRYSTAL)
                .setDisplayName("§dExtratora")
                .setLore(List.of(
                        "§7Quebre blocos para",
                        "§7atingir seu objetivo",
                        "§7e utilizar a extratora.",
                        "",
                        "§d Informações",
                        "§f  Objetivo§8: §70/100", //ficticio
                        "§f  Progresso§8: " + ProgressBarUtils.getInstance().extractor(player),
                        ""
                        ))
                .build();
    }
}
