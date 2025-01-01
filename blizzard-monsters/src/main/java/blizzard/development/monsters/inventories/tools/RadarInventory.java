package blizzard.development.monsters.inventories.tools;

import blizzard.development.monsters.database.cache.methods.MonstersCacheMethods;
import blizzard.development.monsters.inventories.tools.items.RadarItems;
import blizzard.development.monsters.monsters.handlers.monsters.MonstersHandler;
import com.github.stefvanschie.inventoryframework.gui.GuiItem;
import com.github.stefvanschie.inventoryframework.gui.type.ChestGui;
import com.github.stefvanschie.inventoryframework.pane.StaticPane;
import com.github.stefvanschie.inventoryframework.pane.util.Slot;

import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

import java.util.Arrays;
import java.util.List;

public class RadarInventory {
    private static RadarInventory instance;

    private final RadarItems items = RadarItems.getInstance();
    private final MonstersHandler handler = MonstersHandler.getInstance();

    public void open(Player player, int page) {
        ChestGui inventory = new ChestGui(5, "§8Gerenciar monstros (Página " + page + " )");

        StaticPane pane = new StaticPane(0, 0, 9, 5);

        List<String> monsters = handler.getMonsters(player);

        String[] slots = {"11", "12", "13", "14", "15", "20", "21", "22", "23", "24"};

        int startIndex = (page - 1) * slots.length;
        int endIndex = Math.min(startIndex + slots.length, monsters.size());

        for (int i = 0; i < slots.length; i++) {
            int monsterIndex = startIndex + i;
            if (monsterIndex < endIndex) {
                String monsterUuid = monsters.get(monsterIndex);

                MonstersCacheMethods methods = MonstersCacheMethods.getInstance();

                String monsterName = methods.getType(monsterUuid);
                String monsterDisplay = handler.getDisplayName(monsterName);
                Location monsterLocation = methods.getLocation(monsterUuid);

                GuiItem monsterItem = new GuiItem(items.monster(monsterName), event -> {
                    setRadarLocation(player, monsterDisplay, monsterLocation);
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

        GuiItem profileItem = new GuiItem(items.profile(player), event -> {
            event.setCancelled(true);
        });

        pane.addItem(profileItem, Slot.fromIndex(40));

        inventory.addPane(pane);
        inventory.show(player);
    }

    private void setRadarLocation(Player player, String displayName, Location location) {
        List<String> messages = Arrays.asList(
                "",
                "§b§l Você localizou um monstro!",
                "§f O monstro " + displayName + "§f foi encontrado.",
                ""
        );

        messages.forEach(player::sendMessage);

        player.setCompassTarget(location);
        player.playSound(player.getLocation(), Sound.ENTITY_VILLAGER_YES, 1.0f, 1.0f);
        player.getOpenInventory().close();
    }

    public static RadarInventory getInstance() {
        if (instance == null) instance = new RadarInventory();
        return instance;
    }
}
