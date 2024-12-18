package blizzard.development.spawners.inventories.slaughterhouses.main;

import blizzard.development.spawners.builders.slaughterhouses.DisplayBuilder;
import blizzard.development.spawners.database.cache.getters.SlaughterhouseCacheGetters;
import blizzard.development.spawners.database.cache.managers.SlaughterhouseCacheManager;
import blizzard.development.spawners.database.cache.setters.SlaughterhouseCacheSetters;
import blizzard.development.spawners.database.storage.SlaughterhouseData;
import blizzard.development.spawners.handlers.enums.slaughterhouses.States;
import blizzard.development.spawners.inventories.slaughterhouses.friends.FriendsInventory;
import blizzard.development.spawners.inventories.slaughterhouses.main.items.MainItems;
import blizzard.development.spawners.managers.spawners.SpawnerAccessManager;
import blizzard.development.spawners.tasks.slaughterhouses.kill.SlaughterhouseKillTaskManager;
import blizzard.development.spawners.utils.LocationUtil;
import blizzard.development.spawners.utils.SlaughterhousesUtils;
import blizzard.development.spawners.utils.items.TextAPI;
import com.github.stefvanschie.inventoryframework.gui.GuiItem;
import com.github.stefvanschie.inventoryframework.gui.type.ChestGui;
import com.github.stefvanschie.inventoryframework.pane.StaticPane;
import com.github.stefvanschie.inventoryframework.pane.util.Slot;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

public class MainInventory {
    private static MainInventory instance;

    private final MainItems items = MainItems.getInstance();

    private final SlaughterhouseCacheGetters getters = SlaughterhouseCacheGetters.getInstance();

    public void open(Player player, String id) {
        final SpawnerAccessManager accessManager = SpawnerAccessManager.getInstance();

        accessManager.addInventoryUser(id, player.getName());

        ChestGui inventory = new ChestGui(3, "§8Gerenciar abatedouro");
        StaticPane pane = new StaticPane(0, 0, 9, 3);

        GuiItem infoItem = new GuiItem(items.info(id), event -> {
            event.setCancelled(true);
        });

        GuiItem stateItem = new GuiItem(items.state(id), event -> {
            changeState(player, id);
            event.setCancelled(true);
        });

        GuiItem friendsItem = new GuiItem(items.friends(), event -> {
            if (!player.getName().equals(getters.getSlaughterhouseOwner(id)) && !player.hasPermission("blizzard.spawners.admin")) {
                player.sendActionBar(TextAPI.parse("§c§lEI! §cVocê não é dono desse abatedouro."));
                player.getInventory().close();
                return;
            }
            FriendsInventory.getInstance().open(player, id);
            event.setCancelled(true);
        });

        pane.addItem(infoItem, Slot.fromIndex(10));
        pane.addItem(stateItem, Slot.fromIndex(13));
        pane.addItem(friendsItem, Slot.fromIndex(16));

        inventory.addPane(pane);
        inventory.setOnClose(event -> {
            accessManager.removeInventoryUser(id, player.getName());
        });
        inventory.show(player);
    }

    public void changeState(Player player, String id) {
        if (
                !player.getName().equals(getters.getSlaughterhouseOwner(id))
                        && !player.hasPermission("blizzard.spawners.admin")
                        && !getters.getSlaughterhouseFriends(id).contains(player.getName())
        ) {
            player.sendActionBar(TextAPI.parse("§c§lEI! §cVocê não é dono desse abatedouro."));
            player.getInventory().close();
            return;
        }

        SlaughterhouseData data = SlaughterhouseCacheManager.getInstance().getSlaughterhouseData(id);
        SlaughterhouseKillTaskManager manager = SlaughterhouseKillTaskManager.getInstance();


        String currentState = getters.getSlaughterhouseState(id);
        if (currentState.equals(States.ON.getState())) {
            SlaughterhouseCacheSetters.getInstance().setSlaughterhouseState(id, States.OFF.getState());
            manager.stopTask(data.getId());
        } else if (currentState.equals(States.OFF.getState())) {
            SlaughterhouseCacheSetters.getInstance().setSlaughterhouseState(id, States.ON.getState());
            manager.startTask(data);
        }

        Location location = LocationUtil.deserializeLocation(getters.getSlaughterhouseLocation(id));
        if (location == null) return;

        DisplayBuilder.removeSlaughterhouseDisplay(location);
        DisplayBuilder.createSlaughterhouseDisplay(
                location,
                Integer.parseInt(SlaughterhouseCacheGetters.getInstance().getSlaughterhouseTier(id)),
                SlaughterhousesUtils.getInstance().getSlaughterhouseState(
                        States.valueOf(SlaughterhouseCacheGetters.getInstance().getSlaughterhouseState(id).toUpperCase())
                ),
                SlaughterhouseCacheGetters.getInstance().getSlaughterhouseOwner(id)
        );

        player.playSound(player.getLocation(), Sound.ENTITY_VILLAGER_YES, 1.0f, 1.0f);
        open(player, id);
    }

    public static MainInventory getInstance() {
        if (instance == null) instance = new MainInventory();
        return instance;
    }
}
