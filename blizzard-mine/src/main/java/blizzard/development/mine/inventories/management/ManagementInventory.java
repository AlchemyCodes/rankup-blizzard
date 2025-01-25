package blizzard.development.mine.inventories.management;

import blizzard.development.mine.builders.item.ItemBuilder;
import blizzard.development.mine.database.cache.methods.PlayerCacheMethods;
import blizzard.development.mine.mine.adapters.MineAdapter;
import blizzard.development.mine.mine.enums.BlockEnum;
import com.github.stefvanschie.inventoryframework.gui.GuiItem;
import com.github.stefvanschie.inventoryframework.gui.type.ChestGui;
import com.github.stefvanschie.inventoryframework.pane.StaticPane;
import com.github.stefvanschie.inventoryframework.pane.util.Slot;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public class ManagementInventory {

    public void open(Player player) {
        ChestGui inventory = new ChestGui(4, "Gerenciamento");

        StaticPane pane = new StaticPane(0, 0, 9, 4);

        int[] blockSlots = {11, 12, 13, 14, 15};
        int[] barrierSlots = {20,21,22,23,24};

        BlockEnum[] blocks = BlockEnum.values();

        for (int i = 0; i < blocks.length && i < blockSlots.length; i++) {
            BlockEnum block = blocks[i];
            GuiItem blockItem = new GuiItem(createBlockItem(block), event -> {
                event.setCancelled(true);

                PlayerCacheMethods.getInstance().setAreaBlock(player, block);
                MineAdapter.getInstance().generateMine(player);
            });
            pane.addItem(blockItem, Slot.fromIndex(blockSlots[i]));
        }

        for (int barrierSlot : barrierSlots) {
            GuiItem barrierItem = new GuiItem(createBarrierItem(), event -> {
                event.setCancelled(true);
            });
            pane.addItem(barrierItem, Slot.fromIndex(barrierSlot));
        }

        inventory.addPane(pane);
        inventory.show(player);
    }

    public ItemStack createBlockItem(BlockEnum block) {
        return new ItemBuilder(Material.getMaterial(block.name()))
                .setDisplayName("§bBloco de §b§l" + block.getType())
                .setLore(List.of(
                        "§7Melhore o bloco",
                        "§7de sua mina.",
                        "",
                        " §fCusto: §b10k",
                        "",
                        "§bClique para upar."
                ))
                .build();
    }

    public ItemStack createBarrierItem() {
        return new ItemBuilder(Material.BARRIER)
                .setDisplayName("")
                .setLore(List.of(""))
                .build();
    }
}
