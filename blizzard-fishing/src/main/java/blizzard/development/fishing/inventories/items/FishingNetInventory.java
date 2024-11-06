package blizzard.development.fishing.inventories.items;

import com.github.stefvanschie.inventoryframework.gui.GuiItem;
import com.github.stefvanschie.inventoryframework.gui.type.ChestGui;
import com.github.stefvanschie.inventoryframework.pane.StaticPane;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;

public class FishingNetInventory {
    public static void openNet(Player player) {

        ChestGui inventory = new ChestGui(3, "Inventario Rede");

        StaticPane pane = new StaticPane(0, 0, 9, 5);

//        GuiItem infoItem = new GuiItem(item(), event -> {
//            event.setCancelled(true);
//        });

        inventory.addPane(pane);
        inventory.show(player);
    }

}
