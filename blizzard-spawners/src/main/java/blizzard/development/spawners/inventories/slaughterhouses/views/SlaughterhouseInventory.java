package blizzard.development.spawners.inventories.slaughterhouses.views;

import blizzard.development.spawners.inventories.slaughterhouses.views.items.SlaughterhouseItems;
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

public class SlaughterhouseInventory {
    private static SlaughterhouseInventory instance;

    public void open(Player player) {
        ChestGui inventory = new ChestGui(5, "§8Abatedouros");

        final SlaughterhouseItems items = SlaughterhouseItems.getInstance();
        final FileConfiguration config = PluginImpl.getInstance().Slaughterhouses.getConfig();

        StaticPane pane = new StaticPane(0, 0, 9, 5);

        Set<String> slaughterhousesKeys = Objects.requireNonNull(config.getConfigurationSection("slaughterhouses")).getKeys(false);

        int[] slots = {12, 13, 14, 21, 22, 23};

        int index = 0;
        for (String key : slaughterhousesKeys) {
            if (index >= slots.length) break;

            String itemType = config.getString("slaughterhouses." + key + ".item");
            String displayName = config.getString("slaughterhouses." + key + ".display-name");
            List<String> lore = config.getStringList("slaughterhouses." + key + ".menu-lore");
            boolean released = config.getBoolean("slaughterhouses." + key + ".released");
            int cooldown = config.getInt("slaughterhouses." + key + ".kill-cooldown");
            int area = config.getInt("slaughterhouses." + key + ".kill-area");
            int looting = config.getInt("slaughterhouses." + key + ".kill-looting");

            ItemStack slaughterhouseItem = items.slaughterhouse(itemType, displayName, lore, released, cooldown, area, looting);

            GuiItem guiItem = new GuiItem(slaughterhouseItem, event -> event.setCancelled(true));
            pane.addItem(guiItem, Slot.fromIndex(slots[index]));
            index++;
        }

        while (index < slots.length) {
            GuiItem comingSoonItem = new GuiItem(items.comingSoon(), event -> event.setCancelled(true));
            pane.addItem(comingSoonItem, Slot.fromIndex(slots[index]));
            index++;
        }

        inventory.addPane(pane);
        inventory.show(player);
    }

    public static SlaughterhouseInventory getInstance() {
        if (instance == null) instance = new SlaughterhouseInventory();
        return instance;
    }
}
