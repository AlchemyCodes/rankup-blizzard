package blizzard.development.monsters.inventories.cage;

import blizzard.development.monsters.database.cache.managers.MonstersCacheManager;
import blizzard.development.monsters.database.dao.MonstersDAO;
import blizzard.development.monsters.database.storage.MonstersData;
import blizzard.development.monsters.inventories.cage.items.CageItems;
import blizzard.development.monsters.managers.monsters.MonstersCageManager;
import blizzard.development.monsters.monsters.handlers.eggs.MonstersEggHandler;
import blizzard.development.monsters.monsters.handlers.monsters.MonstersHandler;
import blizzard.development.monsters.utils.NumberFormatter;
import com.github.stefvanschie.inventoryframework.gui.GuiItem;
import com.github.stefvanschie.inventoryframework.gui.type.ChestGui;
import com.github.stefvanschie.inventoryframework.pane.StaticPane;
import com.github.stefvanschie.inventoryframework.pane.util.Slot;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.sql.SQLException;
import java.util.*;

public class CageInventory {
    private static CageInventory instance;

    private final CageItems items = CageItems.getInstance();

    public void open(Player player, int page) {
        ChestGui inventory = new ChestGui(5, "§8Gaiola de monstros (Página " + page + ")");

        StaticPane pane = new StaticPane(0, 0, 9, 5);

        List<String> monsters = MonstersHandler.getInstance().getAll().stream().toList();

        String[] slots = {"11", "12", "13", "14", "15", "20", "21", "22", "23", "24"};

        int startIndex = (page - 1) * slots.length;
        int endIndex = Math.min(startIndex + slots.length, monsters.size());

        MonstersCageManager manager = MonstersCageManager.getInstance();

        for (int i = 0; i < slots.length; i++) {

            int monsterIndex = startIndex + i;

            if (monsterIndex < endIndex) {
                String monsterType = monsters.get(monsterIndex);

                GuiItem monsterItem = new GuiItem(items.monster(player, monsterType), event -> {
                    if (event.isLeftClick()) {
                        manager.catchMonster(player, monsterType, false);
                    } else if (event.isRightClick()) {
                        manager.catchMonster(player, monsterType, true);
                    }
                    player.getOpenInventory().close();
                    event.setCancelled(true);
                });


                pane.addItem(monsterItem, Slot.fromIndex(Integer.parseInt(slots[i])));
            } else {
                GuiItem placeholderItem = new GuiItem(items.nothing(), event -> event.setCancelled(true));
                pane.addItem(placeholderItem, Slot.fromIndex(Integer.parseInt(slots[i])));
            }
        }

        if (monsters.size() > endIndex) {
            GuiItem nextItem = new GuiItem(items.next(), event -> {
                open(player, page + 1);
                event.setCancelled(true);
            });
            pane.addItem(nextItem, Slot.fromIndex(35));
        }

        if (page > 1) {
            GuiItem previousItem = new GuiItem(items.previous(), event -> {
                open(player, page - 1);
                event.setCancelled(true);
            });
            pane.addItem(previousItem, Slot.fromIndex(27));
        }

        GuiItem catchAllItem = new GuiItem(items.catchAll(), event -> {
            manager.catchAllMonsters(player);
            player.getInventory().close();
            event.setCancelled(true);
        });

        pane.addItem(catchAllItem, Slot.fromIndex(40));

        inventory.addPane(pane);
        inventory.show(player);
    }

    public static CageInventory getInstance() {
        if (instance == null) instance = new CageInventory();
        return instance;
    }
}
