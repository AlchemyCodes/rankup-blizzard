package blizzard.development.farm.inventories;

import blizzard.development.farm.builders.item.ItemBuilder;
import blizzard.development.farm.database.cache.methods.StorageCacheMethod;
import blizzard.development.farm.utils.NumberFormat;
import com.github.stefvanschie.inventoryframework.gui.GuiItem;
import com.github.stefvanschie.inventoryframework.gui.type.ChestGui;
import com.github.stefvanschie.inventoryframework.pane.StaticPane;
import com.github.stefvanschie.inventoryframework.pane.util.Slot;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;

public class StorageInventory {

    public void open(Player player, Player owner) {
        ChestGui inventory = new ChestGui(4, "Armazém §8[" + owner.getName() + "]");
        StaticPane pane = new StaticPane(0, 0, 9, 4);

        int carrots_stored = StorageCacheMethod.getInstance().getCarrotsAmount(owner);
        int potatoes_stored = StorageCacheMethod.getInstance().getPotatoesAmount(owner);
        int wheat_stored = StorageCacheMethod.getInstance().getWheatAmount(owner);
        int melon_stored = StorageCacheMethod.getInstance().getMelonAmount(owner);
        int cactus_stored = StorageCacheMethod.getInstance().getCactusAmount(owner);

        GuiItem carrots = new GuiItem(carrots(carrots_stored), event -> {
            StorageCacheMethod.getInstance()
                .setCarrotsAmount(
                    owner,
                    0
                );

            open(player, owner);
            event.setCancelled(true);
        });

        GuiItem potatoes = new GuiItem(potatoes(potatoes_stored), event -> {
            StorageCacheMethod.getInstance()
                .setPotatoesAmount(
                    owner,
                    0
                );

            open(player, owner);
            event.setCancelled(true);
        });

        GuiItem wheat = new GuiItem(wheat(wheat_stored), event -> {
            StorageCacheMethod.getInstance()
                .setWheatAmount(
                    owner,
                    0
                );

            open(player, owner);
            event.setCancelled(true);
        });

        GuiItem melon = new GuiItem(melon(melon_stored), event -> {
            StorageCacheMethod.getInstance()
                .setMelonAmount(
                    owner,
                    0
                );

            open(player, owner);
            event.setCancelled(true);
        });

        GuiItem cactus = new GuiItem(cactus(cactus_stored), event -> {
            StorageCacheMethod.getInstance()
                    .setCactusAmount(
                        owner,
                        0
                    );

            open(player, owner);
            event.setCancelled(true);
        });

        pane.addItem(carrots, Slot.fromIndex(10));
        pane.addItem(potatoes, Slot.fromIndex(11));
        pane.addItem(cactus, Slot.fromIndex(13));
        pane.addItem(wheat, Slot.fromIndex(15));
        pane.addItem(melon, Slot.fromIndex(16));


        inventory.addPane(pane);
        inventory.show(player);
    }

    private ItemStack carrots(int carrots) {
        return new ItemBuilder(Material.CARROT)
            .setDisplayName("§6Cenoura")
            .setLore(Arrays.asList(
                "",
                " §fQuantia: §7" + NumberFormat.formatNumber(carrots),
                "",
                "§6Clique para vender."
            ))
            .build(1);
    }

    private ItemStack potatoes(int potatoes) {
        return new ItemBuilder(Material.POTATO)
            .setDisplayName("§eBatata")
            .setLore(Arrays.asList(
                "",
                " §fQuantia: §7" + NumberFormat.formatNumber(potatoes),
                "",
                "§eClique para vender."
            ))
            .build(1);
    }

    private ItemStack wheat(int wheat) {
        return new ItemBuilder(Material.WHEAT)
            .setDisplayName("§eTrigo")
            .setLore(Arrays.asList(
                "",
                " §fQuantia: §7" + NumberFormat.formatNumber(wheat),
                "",
                "§eClique para vender."
            ))
            .build(1);
    }

    private ItemStack melon(int melon) {
        return new ItemBuilder(Material.MELON_SLICE)
            .setDisplayName("§cMelancia")
            .setLore(Arrays.asList(
                "",
                " §fQuantia: §7" + NumberFormat.formatNumber(melon),
                "",
                "§cClique para vender."
            ))
            .build(1);
    }

    private ItemStack cactus(int cactus) {
        return new ItemBuilder(Material.CACTUS)
            .setDisplayName("§aCacto")
            .setLore(Arrays.asList(
                "",
                " §fQuantia: §7" + NumberFormat.formatNumber(cactus),
                "",
                "§aClique para vender."
            ))
            .build(1);
    }
}
