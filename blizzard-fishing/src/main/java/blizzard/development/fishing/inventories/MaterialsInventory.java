package blizzard.development.fishing.inventories;

import com.github.stefvanschie.inventoryframework.gui.GuiItem;
import com.github.stefvanschie.inventoryframework.gui.type.ChestGui;
import com.github.stefvanschie.inventoryframework.pane.StaticPane;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;

public class MaterialsInventory {
    public static void openMaterials(Player player) {

        ChestGui inventory = new ChestGui(3, "Inventario Skins");

        StaticPane pane = new StaticPane(0, 0, 9, 5);

        GuiItem infoItem = new GuiItem(item(), event -> {
            event.setCancelled(true);
        });

        inventory.addPane(pane);
        inventory.show(player);
    }

    public static ItemStack item() {
        ItemStack item = new ItemStack(Material.RABBIT);

        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName("a");
        meta.setLore(Arrays.asList(
                "§7a",
                "",
                "§aa"
        ));
        item.setItemMeta(meta);

        return item;
    }

}
