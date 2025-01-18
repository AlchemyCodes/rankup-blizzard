package blizzard.development.mine.inventories.management;

import com.github.stefvanschie.inventoryframework.gui.type.ChestGui;
import com.github.stefvanschie.inventoryframework.pane.StaticPane;
import org.bukkit.entity.Player;

public class ManagementInventory {

    public void open(Player player) {
        ChestGui inventory = new ChestGui(3, "Gerenciamento");
        StaticPane pane = new StaticPane(0, 0, 9, 3);

        inventory.addPane(pane);
        inventory.show(player);
    }
}
