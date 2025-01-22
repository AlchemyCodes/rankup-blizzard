package blizzard.development.monsters.inventories.cage;

import blizzard.development.monsters.inventories.cage.items.CageItems;
import blizzard.development.monsters.monsters.managers.monsters.cage.MonstersCageManager;
import blizzard.development.monsters.monsters.managers.monsters.MonstersGeneralManager;
import com.github.stefvanschie.inventoryframework.gui.GuiItem;
import com.github.stefvanschie.inventoryframework.gui.type.ChestGui;
import com.github.stefvanschie.inventoryframework.pane.StaticPane;
import com.github.stefvanschie.inventoryframework.pane.util.Slot;
import org.bukkit.entity.Player;

import java.util.*;

public class CageInventory {
    private final static CageItems items = CageItems.getInstance();

    public static void open(Player player, int page) {
        ChestGui inventory = new ChestGui(5, "§8Gaiola de monstros (Página " + page + ")");

        StaticPane pane = new StaticPane(0, 0, 9, 5);

        List<String> monsters = MonstersGeneralManager.getInstance().getAll().stream().toList();

        String[] slots = {"11", "12", "13", "14", "15", "20", "21", "22", "23", "24"};

        int startIndex = (page - 1) * slots.length;
        int endIndex = Math.min(startIndex + slots.length, monsters.size());

        MonstersCageManager manager = MonstersCageManager.getInstance();

        for (int i = 0; i < slots.length; i++) {

            int monsterIndex = startIndex + i;

            if (monsterIndex < endIndex) {
                String monsterType = monsters.get(monsterIndex);

                GuiItem monsterItem = new GuiItem(items.monster(player, monsterType), event -> {
                    event.setCancelled(true);
                    player.getInventory().close();

                    if (event.isLeftClick()) {
                        manager.catchMonster(player, monsterType, false);
                    } else if (event.isRightClick()) {
                        manager.catchMonster(player, monsterType, true);
                    }
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
            event.setCancelled(true);
            player.getInventory().close();

            if (!player.hasPermission("blizzard.monsters.vip")) {
                player.sendActionBar("§c§lEI! §cVocê não possui permissão para utilizar isso.");
                return;
            }

            manager.catchAllMonsters(player);
        });

        pane.addItem(catchAllItem, Slot.fromIndex(40));

        inventory.addPane(pane);
        inventory.show(player);
    }
}
