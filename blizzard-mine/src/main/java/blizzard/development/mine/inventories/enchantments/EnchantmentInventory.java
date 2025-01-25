package blizzard.development.mine.inventories.enchantments;

import blizzard.development.mine.builders.item.ItemBuilder;
import blizzard.development.mine.database.cache.methods.ToolCacheMethods;
import com.github.stefvanschie.inventoryframework.gui.GuiItem;
import com.github.stefvanschie.inventoryframework.gui.type.ChestGui;
import com.github.stefvanschie.inventoryframework.pane.StaticPane;
import com.github.stefvanschie.inventoryframework.pane.util.Slot;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public class EnchantmentInventory {

    private final ToolCacheMethods toolCacheMethods = ToolCacheMethods.getInstance();

    public void open(Player player) {
        ChestGui inventory = new ChestGui(3, "Encantamentos");

        StaticPane pane = new StaticPane(0, 0, 9, 3);

        GuiItem meteorItem = new GuiItem(createMeteorItem(), event -> {
            event.setCancelled(true);

            handleMeteorClick(player, event.isShiftClick(), event.isRightClick());
        });

        pane.addItem(meteorItem, Slot.fromIndex(13));

        inventory.addPane(pane);
        inventory.show(player);
    }

    private ItemStack createMeteorItem() {
        return new ItemBuilder(Material.MAGMA_BLOCK)
                .setDisplayName("§c§lMeteoro")
                .setLore(List.of(
                        "§7Esquerdo §c+1",
                        "§7Shift Esquerdo §c+10",
                        "§7Direito §c-1",
                        "§7Shift Direito §c-10"))
                .build();
    }

    private void handleMeteorClick(Player player, boolean isShiftClick, boolean isRightClick) {
        Integer meteor = toolCacheMethods.getMeteor(player);

        int increment = calculateIncrement(isShiftClick, isRightClick);
        int newMeteorValue = meteor + increment;
        toolCacheMethods.setMeteor(player, newMeteorValue);
        player.sendMessage("Nivel: " + toolCacheMethods.getMeteor(player));
    }

    private int calculateIncrement(boolean isShiftClick, boolean isRightClick) {
        int baseIncrement = isShiftClick ? 10 : 1;
        return isRightClick ? -baseIncrement : baseIncrement;
    }
}
