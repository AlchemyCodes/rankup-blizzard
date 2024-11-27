package blizzard.development.spawners.inventories.spawners;

import blizzard.development.spawners.inventories.enchantments.EnchantmentsInventory;
import blizzard.development.spawners.inventories.spawners.items.SpawnersItems;
import com.github.stefvanschie.inventoryframework.gui.GuiItem;
import com.github.stefvanschie.inventoryframework.gui.type.ChestGui;
import com.github.stefvanschie.inventoryframework.pane.StaticPane;
import com.github.stefvanschie.inventoryframework.pane.util.Slot;
import org.bukkit.entity.Player;

public class SpawnersInventory {
    private static SpawnersInventory instance;

    private final SpawnersItems items = SpawnersItems.getInstance();

    public void open(Player player, String id) {
        ChestGui inventory = new ChestGui(3, "§8Gerenciar Gerador");
        StaticPane pane = new StaticPane(0, 0, 9, 3);

        GuiItem infoItem = new GuiItem(items.info(id), event -> {
            event.setCancelled(true);
        });

        GuiItem enchantmentsItem = new GuiItem(items.enchantments(), event -> {
            EnchantmentsInventory.getInstance().open(player, id);
            event.setCancelled(true);
        });

        GuiItem dropsItem = new GuiItem(items.drops(id), event -> {
            event.setCancelled(true);
        });

        GuiItem friendsItem = new GuiItem(items.friends(), event -> {
            event.setCancelled(true);
        });

        GuiItem rankingItem = new GuiItem(items.ranking(), event -> {
            event.setCancelled(true);
        });

        pane.addItem(infoItem, Slot.fromIndex(10));
        pane.addItem(enchantmentsItem, Slot.fromIndex(11));
        pane.addItem(dropsItem, Slot.fromIndex(13));
        pane.addItem(friendsItem, Slot.fromIndex(15));
        pane.addItem(rankingItem, Slot.fromIndex(16));

        inventory.addPane(pane);
        inventory.show(player);
    }

    public static SpawnersInventory getInstance() {
        if (instance == null) instance = new SpawnersInventory();
        return instance;
    }
}
