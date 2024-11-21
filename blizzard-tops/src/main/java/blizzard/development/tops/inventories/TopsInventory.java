package blizzard.development.tops.inventories;

import blizzard.development.tops.builders.ItemBuilder;
import com.github.stefvanschie.inventoryframework.gui.GuiItem;
import com.github.stefvanschie.inventoryframework.gui.type.ChestGui;
import com.github.stefvanschie.inventoryframework.pane.StaticPane;
import com.github.stefvanschie.inventoryframework.pane.util.Slot;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;

public class TopsInventory {
    private static TopsInventory instance;

    public void open(Player player) {
        ChestGui inventory = new ChestGui(4, "§8Destaques");

        StaticPane pane = new StaticPane(0, 0, 9, 4);

        GuiItem soulsItem = new GuiItem(souls(), event -> {
            CurrenciesInventory.getInstance().open(player, "Almas");
            event.setCancelled(true);
        });

        pane.addItem(soulsItem, Slot.fromIndex(13));

        inventory.addPane(pane);

        player.playSound(player.getLocation(), Sound.BLOCK_CHEST_OPEN, 0.5f, 0.5f);

        inventory.show(player);
    }

    public ItemStack souls() {
        return new ItemBuilder(Material.SOUL_SAND)
                .setDisplayName("&5Almas")
                .setLore(Arrays.asList(
                        "&7Visualize os destaques de almas",
                        "",
                        "&5Clique para visualizar."
                ))
                .addItemFlags(ItemFlag.HIDE_ATTRIBUTES)
                .build();
    }

    public static TopsInventory getInstance() {
        if (instance == null) instance = new TopsInventory();
        return instance;
    }
}
