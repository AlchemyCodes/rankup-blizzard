package blizzard.development.events.inventories;

import blizzard.development.events.managers.SumoManager;
import blizzard.development.events.utils.PluginImpl;
import blizzard.development.events.utils.items.ItemBuilder;
import com.github.stefvanschie.inventoryframework.gui.GuiItem;
import com.github.stefvanschie.inventoryframework.gui.type.ChestGui;
import com.github.stefvanschie.inventoryframework.pane.StaticPane;
import com.github.stefvanschie.inventoryframework.pane.util.Slot;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;

public class EventsInventory {
    public static void openEventsInventory(Player player) {
        ChestGui gui = new ChestGui(5, "Events");

        StaticPane pane = new StaticPane(0, 0, 9, 5);

        GuiItem sumo = new GuiItem(sumoItem(), event -> {
            event.setCancelled(true);

            BuyInventory.openBuyInventory(player);
        });

        pane.addItem(sumo, Slot.fromIndex(13));

        gui.addPane(pane);
        gui.show(player);
    }

    public static ItemStack sumoItem() {
        return new ItemBuilder(Material.BLACK_TERRACOTTA)
                .setDisplayName("§6Sumo")
                .setLore(Arrays.asList(
                        "§7⚔Evento de Sumo! ",
                        "",
                        " §6Custos:",
                        "  §f15k §aMoney",
                        "  §7 ou ",
                        "  §f2k §bFlocos",
                        "",
                        "§6Clique para comprar."))
                .build();
    }
}
