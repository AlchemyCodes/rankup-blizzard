package blizzard.development.bosses.inventories;

import blizzard.development.bosses.utils.items.ItemBuilder;
import com.github.stefvanschie.inventoryframework.gui.GuiItem;
import com.github.stefvanschie.inventoryframework.gui.type.ChestGui;
import com.github.stefvanschie.inventoryframework.pane.StaticPane;
import com.github.stefvanschie.inventoryframework.pane.util.Slot;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class BossesInventory {
    public static void open(Player player) {
        ChestGui inventory = new ChestGui(3, "§8Bosses");

        StaticPane pane = new StaticPane(0, 0, 9, 3);

        GuiItem comingSoonItem = new GuiItem(comingSoon(), event -> {
            player.getInventory().close();
            event.setCancelled(true);
        });

        pane.addItem(comingSoonItem, Slot.fromIndex(13));

        inventory.addPane(pane);

        player.playSound(player.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 0.5f, 0.5f);

        inventory.show(player);
    }

    public static ItemStack comingSoon() {
        String value = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvM2VkMWFiYTczZjYzOWY0YmM0MmJkNDgxOTZjNzE1MTk3YmUyNzEyYzNiOTYyYzk3ZWJmOWU5ZWQ4ZWZhMDI1In19fQ==";
        return new ItemBuilder(value)
                .setDisplayName("&cEm breve.")
                .build();
    }
}
