package blizzard.development.spawners.inventories.drops;

import blizzard.development.spawners.inventories.drops.items.DropsItems;
import blizzard.development.spawners.inventories.spawners.SpawnersInventory;
import blizzard.development.spawners.managers.SpawnerAccessManager;
import com.github.stefvanschie.inventoryframework.gui.GuiItem;
import com.github.stefvanschie.inventoryframework.gui.type.ChestGui;
import com.github.stefvanschie.inventoryframework.pane.StaticPane;
import com.github.stefvanschie.inventoryframework.pane.util.Slot;
import org.bukkit.entity.Player;

public class DropsInventory {
    private static DropsInventory instance;

    private final DropsItems items = DropsItems.getInstance();

    public void open(Player player, String id) {
        final SpawnerAccessManager accessManager = SpawnerAccessManager.getInstance();

        accessManager.addInventoryUser(id, player.getName());

        ChestGui inventory = new ChestGui(4, "§8Drops");
        StaticPane pane = new StaticPane(0, 0, 9, 4);

        GuiItem bonusItem = new GuiItem(items.bonus(), event -> {
            event.setCancelled(true);
        });

        GuiItem dropsItem = new GuiItem(items.drops(id), event -> {
            event.setCancelled(true);
        });

        GuiItem autoSellItem = new GuiItem(items.autoSell(), event -> {
            event.setCancelled(true);
        });


        GuiItem backItem = new GuiItem(items.back(), event -> {
            SpawnersInventory.getInstance().open(player, id);
            event.setCancelled(true);
        });

        pane.addItem(bonusItem, Slot.fromIndex(10));
        pane.addItem(dropsItem, Slot.fromIndex(13));
        pane.addItem(autoSellItem, Slot.fromIndex(16));
        pane.addItem(backItem, Slot.fromIndex(27));

        inventory.addPane(pane);
        inventory.setOnClose(event -> {
            accessManager.removeInventoryUser(id, player.getName());
        });
        inventory.show(player);
    }

    public static DropsInventory getInstance() {
        if (instance == null) instance = new DropsInventory();
        return instance;
    }
}
