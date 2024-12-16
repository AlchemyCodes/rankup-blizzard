package blizzard.development.spawners.inventories.spawners.friends;

import blizzard.development.spawners.database.cache.getters.SpawnersCacheGetters;
import blizzard.development.spawners.database.cache.setters.SpawnersCacheSetters;
import blizzard.development.spawners.inventories.spawners.friends.items.FriendsItems;
import blizzard.development.spawners.inventories.spawners.main.MainInventory;
import blizzard.development.spawners.listeners.chat.spawners.SpawnerFriendsListener;
import blizzard.development.spawners.managers.spawners.SpawnerAccessManager;
import com.github.stefvanschie.inventoryframework.gui.GuiItem;
import com.github.stefvanschie.inventoryframework.gui.type.ChestGui;
import com.github.stefvanschie.inventoryframework.pane.StaticPane;
import com.github.stefvanschie.inventoryframework.pane.util.Slot;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

import java.util.Arrays;
import java.util.List;

public class FriendsInventory {
    private static FriendsInventory instance;

    private final SpawnersCacheGetters getters = SpawnersCacheGetters.getInstance();
    private final SpawnersCacheSetters setters = SpawnersCacheSetters.getInstance();

    public void open(Player player, String id) {
        final SpawnerAccessManager accessManager = SpawnerAccessManager.getInstance();

        accessManager.addInventoryUser(id, player.getName());

        ChestGui inventory = new ChestGui(5, "§8Amigos");

        final FriendsItems items = FriendsItems.getInstance();

        StaticPane pane = new StaticPane(0, 0, 9, 5);

        List<String> friends = getters.getSpawnerFriends(id);

        String[] slots = {"11", "12", "13", "14", "15", "20", "21", "22", "23", "24"};

        for (int i = 0; i < slots.length; i++) {
            if (i < friends.size()) {
                String spawnerFriend = friends.get(i);

                GuiItem spawnersItem = new GuiItem(items.friends(spawnerFriend, i), event -> {
                    removeFriend(player, id, spawnerFriend);
                    event.setCancelled(true);
                });
                pane.addItem(spawnersItem, Slot.fromIndex(Integer.parseInt(slots[i])));
            } else {
                GuiItem placeholderItem = new GuiItem(items.nothing(i), event -> event.setCancelled(true));
                pane.addItem(placeholderItem, Slot.fromIndex(Integer.parseInt(slots[i])));
            }
        }

        GuiItem manageItem = new GuiItem(items.manage(id), event -> {
            addFriend(id, player);
            event.setCancelled(true);
        });

        GuiItem backItem = new GuiItem(items.back(), event -> {
            MainInventory.getInstance().open(player, id);
            event.setCancelled(true);
        });

        pane.addItem(manageItem, Slot.fromIndex(31));
        pane.addItem(backItem, Slot.fromIndex(36));

        inventory.addPane(pane);
        inventory.setOnClose(event -> {
            accessManager.removeInventoryUser(id, player.getName());
        });
        inventory.show(player);
    }

    public void removeFriend(Player player, String id, String friend) {
        if (!getters.getSpawnerFriends(id).contains(friend)) {
            player.sendActionBar("§c§lEI! §cEste jogador não está mais na lista de amigos.");
            return;
        }

        player.sendActionBar("§a§lYAY! §aO jogador §7" + friend + "§a foi removido como amigo do gerador.");
        player.playSound(player.getLocation(), Sound.ENTITY_VILLAGER_YES, 1.0f, 1.0f);
        setters.removeSpawnerFriend(id, List.of(friend));
        open(player, id);
    }

    public void addFriend(String id, Player player) {
        if (getters.getSpawnerFriends(id).size() >= getters.getSpawnerFriendsLimit(id)) {
            player.sendActionBar("§c§lEI! §cO gerador já atingiu o limite máximo de amigos.");
            player.playSound(player.getLocation(), Sound.ENTITY_VILLAGER_NO, 0.5f, 0.5f);
            player.getOpenInventory().close();
            return;
        }

        SpawnerFriendsListener.addPendingInvite(player, id);
        List<String> messages = Arrays.asList(
                "",
                "§a Digite no chat o nome do jogador que deseja adicionar!",
                "§7 Digite 'cancelar' para cancelar a adição.",
                ""
        );
        messages.forEach(player::sendMessage);

        player.getOpenInventory().close();
    }

    public static FriendsInventory getInstance() {
        if (instance == null) instance = new FriendsInventory();
        return instance;
    }
}
