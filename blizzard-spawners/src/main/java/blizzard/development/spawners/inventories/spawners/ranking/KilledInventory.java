package blizzard.development.spawners.inventories.spawners.ranking;

import blizzard.development.spawners.database.cache.getters.PlayersCacheGetters;
import blizzard.development.spawners.database.storage.PlayersData;
import blizzard.development.spawners.inventories.spawners.ranking.items.RankingItems;
import blizzard.development.spawners.inventories.spawners.shop.ShopInventory;
import com.github.stefvanschie.inventoryframework.gui.GuiItem;
import com.github.stefvanschie.inventoryframework.gui.type.ChestGui;
import com.github.stefvanschie.inventoryframework.pane.StaticPane;
import com.github.stefvanschie.inventoryframework.pane.util.Slot;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

import java.util.Arrays;
import java.util.List;

public class KilledInventory {
    private static KilledInventory instance;

    public void open(Player player) {
        ChestGui inventory = new ChestGui(5, "§8Destaques - Spawners");

        final RankingItems items = RankingItems.getInstance();
        final PlayersCacheGetters getters = PlayersCacheGetters.getInstance();

        StaticPane pane = new StaticPane(0, 0, 9, 5);

        List<PlayersData> topPlayers = getters.getTopKilledMobs();

        String[] slots = {"10", "11", "12", "13", "14", "15", "16", "21", "22", "23"};

        for (int i = 0; i < slots.length; i++) {
            if (i < topPlayers.size()) {
                PlayersData playersData = topPlayers.get(i);
                String name = playersData.getNickname();

                double amount = playersData.getKilledMobs();

                GuiItem spawnersItem = new GuiItem(items.spawners(name, "Mobs Mortos", i, amount), event -> event.setCancelled(true));
                pane.addItem(spawnersItem, Slot.fromIndex(Integer.parseInt(slots[i])));
            } else {
                GuiItem placeholderItem = new GuiItem(items.nothing(i), event -> event.setCancelled(true));
                pane.addItem(placeholderItem, Slot.fromIndex(Integer.parseInt(slots[i])));
            }
        }

        GuiItem backItem = new GuiItem(items.back(), event -> {
            ShopInventory.getInstance().open(player);
            event.setCancelled(true);
        });

        GuiItem filterItem = new GuiItem(items.filter(
                Arrays.asList(
                        "§7Mude a categoria de destaques",
                        "",
                        "§8 ▶ §7Comprados",
                        "§8 ▶ §7Limites",
                        "§8 ▶ §fMobs Mortos",
                        "",
                        "§aClique aqui para mudar."
                )
        ), event -> {
            PurchasedInventory.getInstance().open(player);
            player.playSound(player.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 0.5f, 0.5f);
            event.setCancelled(true);
        });

        pane.addItem(backItem, Slot.fromIndex(36));
        pane.addItem(filterItem, Slot.fromIndex(40));

        inventory.addPane(pane);

        inventory.show(player);
    }

    public static KilledInventory getInstance() {
        if (instance == null) instance = new KilledInventory();
        return instance;
    }
}
