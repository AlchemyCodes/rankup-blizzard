package blizzard.development.spawners.inventories.ranking;

import blizzard.development.spawners.inventories.ranking.items.RankingItems;
import com.github.stefvanschie.inventoryframework.gui.GuiItem;
import com.github.stefvanschie.inventoryframework.gui.type.ChestGui;
import com.github.stefvanschie.inventoryframework.pane.StaticPane;
import com.github.stefvanschie.inventoryframework.pane.util.Slot;
import org.bukkit.entity.Player;

public class RankingInventory {
    private static RankingInventory instance;

    private final RankingItems items = RankingItems.getInstance();

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

    public static RankingInventory getInstance() {
        if (instance == null) instance = new RankingInventory();
        return instance;
    }
}
