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
        ChestGui gui = new ChestGui(5, "Vara de pesca");

        StaticPane pane = new StaticPane(0, 0, 9, 5);

        GuiItem enchant2 = new GuiItem(experienced(player), event -> { event.setCancelled(true);
        });
        GuiItem enchant3 = new GuiItem(lucky(player), event -> { event.setCancelled(true);
        });

        GuiItem explanation = new GuiItem(explanation(), event -> { event.setCancelled(true);
        });

        pane.addItem(enchant2, Slot.fromIndex(13));
        pane.addItem(enchant3, Slot.fromIndex(15));
        pane.addItem(explanation, Slot.fromIndex(31));


        gui.addPane(pane);

        gui.show(player);
    }

    public static ItemStack explanation() {
        ItemStack back = new ItemStack(Material.GREEN_DYE);
        ItemMeta meta = back.getItemMeta();
        meta.setDisplayName("§aEncantamentos");
        meta.setLore(Arrays.asList(
                "§7Nesse menu estão os encantamentos",
                "§7que serão melhorados ao coletar lixo."
        ));

        back.setItemMeta(meta);

        return back;
    }

    public static ItemStack lucky(Player player) {
        ItemStack back = new ItemStack(Material.BOOK);
        ItemMeta meta = back.getItemMeta();
        meta.setDisplayName("§6Sortudo §7[§l" + RodsCacheMethod.getInstance().getLucky(player) + "§7]");
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
        meta.setDisplayName("§6Experiente §7[§l" + RodsCacheMethod.getInstance().getExperienced(player) + "§7]");
        meta.setLore(Arrays.asList(
                "§7Esse encantamento aumenta",
                "§7a experiência que você ganha."
        ));

        back.setItemMeta(meta);

        return back;
    }
}
