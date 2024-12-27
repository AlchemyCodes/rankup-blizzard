package blizzard.development.monsters.inventories.main;

import blizzard.development.monsters.inventories.main.items.MonstersItems;
import com.github.stefvanschie.inventoryframework.gui.GuiItem;
import com.github.stefvanschie.inventoryframework.gui.type.ChestGui;
import com.github.stefvanschie.inventoryframework.pane.StaticPane;
import com.github.stefvanschie.inventoryframework.pane.util.Slot;
import org.bukkit.entity.Player;

public class MonstersInventory {
    private static MonstersInventory instance;

    private final MonstersItems items = MonstersItems.getInstance();

    public void open(Player player) {

        ChestGui inventory = new ChestGui(3, "§8Monstros");
        StaticPane pane = new StaticPane(0, 0, 9, 3);

        GuiItem manageItem = new GuiItem(items.manage(), event -> {
            event.setCancelled(true);
        });

        GuiItem rewardsItem = new GuiItem(items.rewards(), event -> {
            event.setCancelled(true);
        });

        GuiItem goItem = new GuiItem(items.go(true), event -> {
            event.setCancelled(true);
        });

        GuiItem rankingItem = new GuiItem(items.ranking(), event -> {
            event.setCancelled(true);
        });

        GuiItem swordItem = new GuiItem(items.sword(), event -> {
            event.setCancelled(true);
        });

        pane.addItem(manageItem, Slot.fromIndex(10));
        pane.addItem(rewardsItem, Slot.fromIndex(11));
        pane.addItem(goItem, Slot.fromIndex(13));
        pane.addItem(rankingItem, Slot.fromIndex(15));
        pane.addItem(swordItem, Slot.fromIndex(16));

        inventory.addPane(pane);
        inventory.show(player);
    }

    public static MonstersInventory getInstance() {
        if (instance == null) instance = new MonstersInventory();
        return instance;
    }
}
