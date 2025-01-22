package blizzard.development.farm.inventories;

import blizzard.development.currencies.enums.Currencies;
import blizzard.development.farm.builders.item.ItemBuilder;
import blizzard.development.farm.listeners.storage.CactusDropListener;
import com.github.stefvanschie.inventoryframework.gui.GuiItem;
import com.github.stefvanschie.inventoryframework.gui.type.ChestGui;
import com.github.stefvanschie.inventoryframework.pane.StaticPane;
import com.github.stefvanschie.inventoryframework.pane.util.Slot;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class StorageInventory {

    public void open(Player player) {
        ChestGui inventory = new ChestGui(4, "Armazém");
        StaticPane pane = new StaticPane(0, 0, 9, 4);

        int cactus = CactusDropListener.getCactusAmount(player);

        GuiItem cactusItem = new GuiItem(cactus(cactus), event -> {
            event.setCancelled(true);
        });

        pane.addItem(cactusItem, Slot.fromIndex(13));

        inventory.addPane(pane);
        inventory.show(player);
    }

    private ItemStack cactus(int amount) {
        return new ItemBuilder(Material.CACTUS)
            .setDisplayName("Cacto")
            .build(amount);
    }
}
