package blizzard.development.spawners.inventories.shop;

import blizzard.development.spawners.inventories.ranking.PurchasedInventory;
import blizzard.development.spawners.inventories.ranking.items.RankingItems;
import blizzard.development.spawners.inventories.shop.items.ShopItems;
import blizzard.development.spawners.utils.NumberFormat;
import blizzard.development.spawners.utils.PluginImpl;
import com.github.stefvanschie.inventoryframework.gui.GuiItem;
import com.github.stefvanschie.inventoryframework.gui.type.ChestGui;
import com.github.stefvanschie.inventoryframework.pane.StaticPane;
import com.github.stefvanschie.inventoryframework.pane.util.Slot;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.List;
import java.util.Objects;
import java.util.Set;

public class ShopInventory {
    private static ShopInventory instance;

    public void open(Player player) {
        ChestGui inventory = new ChestGui(5, "§8Spawners");

        final ShopItems items = ShopItems.getInstance();
        final FileConfiguration config = PluginImpl.getInstance().Spawners.getConfig();
        final NumberFormat format = NumberFormat.getInstance();

        StaticPane pane = new StaticPane(0, 0, 9, 5);

        Set<String> spawnerKeys = Objects.requireNonNull(config.getConfigurationSection("spawners")).getKeys(false);

        int[] slots = {11, 12, 13, 14, 15, 20, 21, 22, 23, 24};

        int index = 0;
        for (String key : spawnerKeys) {
            if (index >= slots.length) break;

            String itemType = config.getString("spawners." + key + ".item");
            String displayName = config.getString("spawners." + key + ".display-name");
            List<String> lore = config.getStringList("spawners." + key + ".lore");
            boolean released = config.getBoolean("spawners." + key + ".permitted-purchase");
            String cost = format.formatNumber(config.getInt("spawners." + key + ".buy-price"));
            String dropCost = format.formatNumber(config.getInt("spawners." + key + ".sell-drop-price"));

            ItemStack spawnerItem = items.spawner(itemType, displayName, lore, released, cost, dropCost);

            GuiItem guiItem = new GuiItem(spawnerItem, event -> {
                event.setCancelled(true);
            });
            pane.addItem(guiItem, Slot.fromIndex(slots[index]));
            index++;
        }

        while (index < slots.length) {
            GuiItem comingSoonItem = new GuiItem(items.comingSoon(), event -> event.setCancelled(true));
            pane.addItem(comingSoonItem, Slot.fromIndex(slots[index]));
            index++;
        }

        GuiItem rankingItem = new GuiItem(items.ranking(), event -> {
            PurchasedInventory.getInstance().open(player);
            event.setCancelled(true);
        });

        GuiItem informationsItem = new GuiItem(items.informations(player), event -> event.setCancelled(true));

        pane.addItem(rankingItem, Slot.fromIndex(39));
        pane.addItem(informationsItem, Slot.fromIndex(41));

        inventory.addPane(pane);
        inventory.show(player);
    }

    public static ShopInventory getInstance() {
        if (instance == null) instance = new ShopInventory();
        return instance;
    }
}
