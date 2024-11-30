package blizzard.development.spawners.inventories.friends;

import blizzard.development.spawners.inventories.friends.items.FriendsItems;
import blizzard.development.spawners.managers.SpawnerAccessManager;
import blizzard.development.spawners.utils.items.TextAPI;
import com.github.stefvanschie.inventoryframework.gui.GuiItem;
import com.github.stefvanschie.inventoryframework.gui.type.ChestGui;
import com.github.stefvanschie.inventoryframework.pane.StaticPane;
import com.github.stefvanschie.inventoryframework.pane.util.Slot;
import org.bukkit.entity.Player;

public class FriendsInventory {
    private static FriendsInventory instance;

    private final FriendsItems items = FriendsItems.getInstance();

    public void open(Player player, String id) {
        ChestGui inventory = new ChestGui(3, "§8Spawner");
        StaticPane pane = new StaticPane(0, 0, 9, 3);

        GuiItem infoItem = new GuiItem(items.info(id), event -> {
            event.setCancelled(true);
        });

        GuiItem enchantmentsItem = new GuiItem(items.enchantments(), event -> {
            event.setCancelled(true);
        });

        GuiItem friendsItem = new GuiItem(items.friends(), event -> {
            event.setCancelled(true);
        });

        GuiItem rankingItem = new GuiItem(items.ranking(), event -> {
            event.setCancelled(true);
        });

        pane.addItem(infoItem, Slot.fromIndex(10));
        pane.addItem(enchantmentsItem, Slot.fromIndex(12));
        pane.addItem(friendsItem, Slot.fromIndex(14));
        pane.addItem(rankingItem, Slot.fromIndex(16));

        inventory.addPane(pane);
        inventory.show(player);
    }

    public static FriendsInventory getInstance() {
        if (instance == null) instance = new FriendsInventory();
        return instance;
    }
}
