package blizzard.development.fishing.inventories.items;

import blizzard.development.fishing.database.cache.methods.RodsCacheMethod;
import blizzard.development.fishing.database.storage.RodsData;
import com.github.stefvanschie.inventoryframework.gui.GuiItem;
import com.github.stefvanschie.inventoryframework.gui.type.ChestGui;
import com.github.stefvanschie.inventoryframework.pane.StaticPane;
import com.github.stefvanschie.inventoryframework.pane.util.Slot;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;

public class FishingRodInventory {
    public static void openRod(Player player) {
        ChestGui gui = new ChestGui(3, "Vara de pesca");

        StaticPane pane = new StaticPane(0, 0, 9, 3);

        GuiItem enchant2 = new GuiItem(experienced(player), event -> { event.setCancelled(true);
        });
        GuiItem enchant3 = new GuiItem(lucky(player), event -> { event.setCancelled(true);
        });

        GuiItem back = new GuiItem(back(), event -> { event.setCancelled(true);
        });

        pane.addItem(enchant2, Slot.fromIndex(13));
        pane.addItem(enchant3, Slot.fromIndex(15));
        pane.addItem(back, Slot.fromIndex(18));


        gui.addPane(pane);

        gui.show(player);
    }

    public static ItemStack back() {

        ItemStack back = new ItemStack(Material.RED_DYE);
        ItemMeta meta = back.getItemMeta();
        meta.setDisplayName("§cVoltar");
        meta.setLore(Arrays.asList(
                "§7Clique para voltar",
                "§7ao menu anterior."
        ));

        back.setItemMeta(meta);

        return back;
    }

    public static ItemStack lucky(Player player) {

        ItemStack back = new ItemStack(Material.BOOK);
        ItemMeta meta = back.getItemMeta();
        meta.setDisplayName("§6Sortudo §7[§l" + RodsCacheMethod.getLucky(player) + "§7]");
        meta.setLore(Arrays.asList(
                "§7Esse encantamento adiciona",
                "§7a chance de pescar mais peixes."
        ));

        back.setItemMeta(meta);

        return back;
    }

    public static ItemStack experienced(Player player) {

        ItemStack back = new ItemStack(Material.BOOK);
        ItemMeta meta = back.getItemMeta();
        meta.setDisplayName("§6Experiente §7[§l" + RodsCacheMethod.getExperienced(player) + "§7]");
        meta.setLore(Arrays.asList(
                "§7Esse encantamento aumenta",
                "§7a experiência que você ganha."
        ));

        back.setItemMeta(meta);

        return back;
    }
}
