package blizzard.development.monsters.inventories.ranking;

import blizzard.development.currencies.api.CurrenciesAPI;
import blizzard.development.currencies.currencies.SoulsCurrency;
import blizzard.development.currencies.database.storage.PlayersData;
import blizzard.development.currencies.enums.Currencies;
import blizzard.development.monsters.inventories.main.MonstersInventory;
import blizzard.development.monsters.inventories.ranking.items.RankingItems;
import com.github.stefvanschie.inventoryframework.gui.GuiItem;
import com.github.stefvanschie.inventoryframework.gui.type.ChestGui;
import com.github.stefvanschie.inventoryframework.pane.StaticPane;
import com.github.stefvanschie.inventoryframework.pane.util.Slot;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

import java.util.Arrays;
import java.util.List;

public class SoulsInventory {
    private static SoulsInventory instance;

    public void open(Player player) {
        ChestGui inventory = new ChestGui(5, "§8Destaques (Almas)");

        RankingItems items = RankingItems.getInstance();

        StaticPane pane = new StaticPane(0, 0, 9, 5);

        List<PlayersData> topPlayers = SoulsCurrency.getInstance().getTopPlayers();

        String[] slots = {"10", "11", "12", "13", "14", "15", "16", "21", "22", "23"};

        for (int i = 0; i < slots.length; i++) {
            if (i < topPlayers.size()) {
                PlayersData playersData = topPlayers.get(i);
                String name = playersData.getNickname();

                double amount = playersData.getSouls();

                GuiItem monstersItem = new GuiItem(items.monsters(name, "Almas", i, amount), event -> event.setCancelled(true));
                pane.addItem(monstersItem, Slot.fromIndex(Integer.parseInt(slots[i])));
            } else {
                GuiItem placeholderItem = new GuiItem(items.nothing(i), event -> event.setCancelled(true));
                pane.addItem(placeholderItem, Slot.fromIndex(Integer.parseInt(slots[i])));
            }
        }

        GuiItem backItem = new GuiItem(items.back(), event -> {
            MonstersInventory.getInstance().open(player);
            event.setCancelled(true);
        });

        GuiItem filterItem = new GuiItem(items.filter(
                Arrays.asList(
                        "§7Mude a categoria",
                        "§7de destaques.",
                        "",
                        "§d 👻 §fAlmas",
                        "§c ✞ §7Mortos",
                        "",
                        "§aClique para mudar."
                )
        ), event -> {
            //KilledInventory.getInstance().open(player);
            player.playSound(player.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 0.5f, 0.5f);
            event.setCancelled(true);
        });

        pane.addItem(backItem, Slot.fromIndex(36));
        pane.addItem(filterItem, Slot.fromIndex(40));

        inventory.addPane(pane);

        inventory.show(player);
    }

    public static SoulsInventory getInstance() {
        if (instance == null) instance = new SoulsInventory();
        return instance;
    }
}
