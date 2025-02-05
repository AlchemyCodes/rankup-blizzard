package blizzard.development.mine.inventories.booster;

import blizzard.development.mine.builders.item.ItemBuilder;
import blizzard.development.mine.mine.adapters.BoosterAdapter;
import blizzard.development.mine.mine.enums.BoosterEnum;
import com.github.stefvanschie.inventoryframework.gui.GuiItem;
import com.github.stefvanschie.inventoryframework.gui.type.ChestGui;
import com.github.stefvanschie.inventoryframework.pane.StaticPane;
import com.github.stefvanschie.inventoryframework.pane.util.Slot;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public class BoosterInventory {
    public void open(Player player) {
        ChestGui inventory = new ChestGui(3, "Boosters");
        StaticPane pane = new StaticPane(0, 0, 9, 3);

        int[] slots = {11, 13, 15};
        BoosterEnum[] boosters = BoosterEnum.values();

        for (int i = 0; i < boosters.length; i++) {
            if (i >= slots.length) break;
            BoosterEnum booster = boosters[i];

            GuiItem boosterItem = new GuiItem(createBoosterItem(booster), event -> {
                event.setCancelled(true);

                BoosterAdapter.getInstance().giveBooster(player, booster);
            });

            pane.addItem(boosterItem, Slot.fromIndex(slots[i]));
        }

        inventory.addPane(pane);
        inventory.show(player);
    }

    private ItemStack createBoosterItem(BoosterEnum booster) {
        return new ItemBuilder(Material.EXPERIENCE_BOTTLE)
                .setDisplayName(booster.getName())
                .setLore(List.of(
                        "§7Ultilize esse booster para ganhar",
                        "§7bonûs de " + booster.getMultiplier() + " na mina.",
                        "",
                        "§dClique para pegar."
                ))
                .build();
    }
}
