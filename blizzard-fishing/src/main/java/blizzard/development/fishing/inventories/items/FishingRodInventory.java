package blizzard.development.fishing.inventories.items;

import blizzard.development.fishing.database.cache.methods.RodsCacheMethod;
import blizzard.development.fishing.database.storage.RodsData;
import blizzard.development.fishing.inventories.MaterialsInventory;
import blizzard.development.fishing.utils.items.ItemBuilder;
import com.github.stefvanschie.inventoryframework.gui.GuiItem;
import com.github.stefvanschie.inventoryframework.gui.type.ChestGui;
import com.github.stefvanschie.inventoryframework.pane.StaticPane;
import com.github.stefvanschie.inventoryframework.pane.util.Slot;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public class FishingRodInventory {
    public static void openRod(Player player) {
        ChestGui gui = new ChestGui(5, "Vara de pesca");

        StaticPane pane = new StaticPane(0, 0, 9, 5);

        GuiItem enchant1 = new GuiItem(specialist(player), event -> {
            event.setCancelled(true);
        });
        GuiItem enchant2 = new GuiItem(experienced(player), event -> {
            event.setCancelled(true);
        });
        GuiItem enchant3 = new GuiItem(lucky(player), event -> {
            event.setCancelled(true);
        });
        GuiItem explanation = new GuiItem(explanation(), event -> {
            event.setCancelled(true);
        });
        GuiItem goSkinInv = new GuiItem(goSkinInv(), event -> {
            event.setCancelled(true);
            MaterialsInventory.materialsMenu(player);
        });

        pane.addItem(enchant1, Slot.fromIndex(11));
        pane.addItem(enchant2, Slot.fromIndex(13));
        pane.addItem(enchant3, Slot.fromIndex(15));
        pane.addItem(explanation, Slot.fromIndex(30));
        pane.addItem(goSkinInv, Slot.fromIndex(32));

        gui.addPane(pane);

        gui.show(player);
    }

    public static ItemStack explanation() {
        return new ItemBuilder(Material.GREEN_DYE)
                .setDisplayName("§aEncantamentos")
                .setLore(List.of(
                        "§7Nesse menu estão os encantamentos",
                        "§7que serão melhorados ao coletar lixo."
                )).build();
    }

    public static ItemStack goSkinInv() {
        return new ItemBuilder(Material.LIME_DYE)
                .setDisplayName("§6Skins")
                .setLore(List.of("§7Clique para ir ao",
                        "inventário de skins!")).build();
    }

    public static ItemStack specialist(Player player) {
        return new ItemBuilder(Material.BOOK)
                .setDisplayName("§6Especialista §7[§l" + RodsCacheMethod.getInstance().getSpecialist(player) + "§7]")
                .setLore(List.of(
                        "§7Esse encantamento duplica",
                        "§7a experiência e peixes que você ganha",
                        "§7por 10 segundos ao ser ativado."
                )).build();
    }

    public static ItemStack lucky(Player player) {
        return new ItemBuilder(Material.BOOK)
                .setDisplayName("§6Sortudo §7[§l" + RodsCacheMethod.getInstance().getLucky(player) + "§7]")
                .setLore(List.of(
                        "§7Esse encantamento adiciona",
                        "§7a chance de pescar mais peixes."
                )).build();
    }

    public static ItemStack experienced(Player player) {
        return new ItemBuilder(Material.BOOK)
                .setDisplayName("§6Experiente §7[§l" + RodsCacheMethod.getInstance().getExperienced(player) + "§7]")
                .setLore(List.of(
                        "§7Esse encantamento aumenta",
                        "§7a experiência que você ganha."
                )).build();
    }
}
