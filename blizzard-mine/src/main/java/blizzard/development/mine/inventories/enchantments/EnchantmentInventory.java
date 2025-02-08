package blizzard.development.mine.inventories.enchantments;

import blizzard.development.mine.builders.item.ItemBuilder;
import blizzard.development.mine.database.cache.ToolCacheManager;
import blizzard.development.mine.database.cache.methods.ToolCacheMethods;
import blizzard.development.mine.database.storage.ToolData;
import blizzard.development.mine.mine.adapters.EnchantmentAdapter;
import blizzard.development.mine.mine.enums.ToolEnum;
import blizzard.development.mine.utils.PluginImpl;
import blizzard.development.mine.utils.text.TextUtils;
import com.github.stefvanschie.inventoryframework.gui.GuiItem;
import com.github.stefvanschie.inventoryframework.gui.type.ChestGui;
import com.github.stefvanschie.inventoryframework.pane.StaticPane;
import com.github.stefvanschie.inventoryframework.pane.util.Slot;
import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class EnchantmentInventory {

    private final ToolCacheMethods toolCacheMethods = ToolCacheMethods.getInstance();
    private final EnchantmentAdapter enchantmentAdapter = EnchantmentAdapter.getInstance();

    public void open(Player player, String id) {
        ChestGui inventory = new ChestGui(3, "Encantamentos");

        StaticPane pane = new StaticPane(0, 0, 9, 3);

        ToolData toolData = ToolCacheManager.getInstance().getToolData(id);

        GuiItem meteorItem = new GuiItem(createMeteorItem(), event -> {
            event.setCancelled(true);

            if (!enchantmentAdapter.isTool(player)) {
                player.sendActionBar("§c§lEI! §cVocê precisa estar com uma picareta na mão.");
                player.closeInventory();
                return;
            }

            handleMeteorClick(id, event.isShiftClick(), event.isRightClick());
            enchantmentAdapter.updateToolLore(player, toolData);
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

    private void handleMeteorClick(String id, Boolean isShiftClick, Boolean isRightClick) {
        Integer meteor = toolCacheMethods.getMeteor(id);

        int increment = calculateIncrement(isShiftClick, isRightClick);
        int newMeteorValue = meteor + increment;
        toolCacheMethods.setMeteor(id, newMeteorValue);
    }

    private int calculateIncrement(Boolean isShiftClick, Boolean isRightClick) {
        int baseIncrement = isShiftClick ? 10 : 1;
        return isRightClick ? -baseIncrement : baseIncrement;
    }
}
