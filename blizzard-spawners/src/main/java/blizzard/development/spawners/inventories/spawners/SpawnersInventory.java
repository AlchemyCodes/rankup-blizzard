package blizzard.development.spawners.inventories.spawners;

import blizzard.development.spawners.builders.DisplayBuilder;
import blizzard.development.spawners.database.cache.getters.SpawnersCacheGetters;
import blizzard.development.spawners.database.cache.setters.SpawnersCacheSetters;
import blizzard.development.spawners.handlers.enums.States;
import blizzard.development.spawners.inventories.enchantments.EnchantmentsInventory;
import blizzard.development.spawners.inventories.spawners.items.SpawnersItems;
import blizzard.development.spawners.managers.SpawnerAccessManager;
import blizzard.development.spawners.utils.LocationUtil;
import blizzard.development.spawners.utils.SpawnersUtils;
import blizzard.development.spawners.utils.items.TextAPI;
import com.github.stefvanschie.inventoryframework.gui.GuiItem;
import com.github.stefvanschie.inventoryframework.gui.type.ChestGui;
import com.github.stefvanschie.inventoryframework.pane.StaticPane;
import com.github.stefvanschie.inventoryframework.pane.util.Slot;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

public class SpawnersInventory {
    private static SpawnersInventory instance;

    private final SpawnersItems items = SpawnersItems.getInstance();

    private final SpawnersCacheGetters getters = SpawnersCacheGetters.getInstance();

    public void open(Player player, String id) {
        final SpawnerAccessManager accessManager = SpawnerAccessManager.getInstance();

        accessManager.addInventoryUser(id, player.getName());

        ChestGui inventory = new ChestGui(3, "§8Gerenciar gerador");
        StaticPane pane = new StaticPane(0, 0, 9, 3);

        GuiItem infoItem = new GuiItem(items.info(id), event -> {
            changeState(player, id);
            event.setCancelled(true);
        });

        GuiItem enchantmentsItem = new GuiItem(items.enchantments(), event -> {
            if (!checkPermission(player, id)) return;
            EnchantmentsInventory.getInstance().open(player, id);
            event.setCancelled(true);
        });

        GuiItem dropsItem = new GuiItem(items.drops(id), event -> {
            if (!checkPermission(player, id)) return;
            event.setCancelled(true);
        });

        GuiItem friendsItem = new GuiItem(items.friends(), event -> {
            if (!player.getName().equals(getters.getSpawnerOwner(id)) && !player.hasPermission("blizzard.spawners.admin")) {
                player.sendActionBar(TextAPI.parse("§c§lEI! §cVocê não é dono desse spawner."));
                player.getInventory().close();
                return;
            }
            event.setCancelled(true);
        });

        GuiItem rankingItem = new GuiItem(items.ranking(), event -> {
            if (!checkPermission(player, id)) return;
            event.setCancelled(true);
        });

        pane.addItem(infoItem, Slot.fromIndex(10));
        pane.addItem(enchantmentsItem, Slot.fromIndex(11));
        pane.addItem(dropsItem, Slot.fromIndex(13));
        pane.addItem(friendsItem, Slot.fromIndex(15));
        pane.addItem(rankingItem, Slot.fromIndex(16));

        inventory.addPane(pane);
        inventory.setOnClose(event -> {
            accessManager.removeInventoryUser(id, player.getName());
        });
        inventory.show(player);
    }

    public boolean checkPermission(Player player, String id) {
        if (
                !player.getName().equals(getters.getSpawnerOwner(id))
                && !player.hasPermission("blizzard.spawners.admin")
                && getters.getSpawnerState(id).equalsIgnoreCase(States.PRIVATE.getState())
        ) {
            player.sendActionBar(TextAPI.parse("§c§lEI! §cVocê não é dono desse spawner."));
            player.getInventory().close();
            return false;
        }
        return true;
    }

    public void changeState(Player player, String id) {
        if (!player.getName().equals(getters.getSpawnerOwner(id)) && !player.hasPermission("blizzard.spawners.admin")) {
            player.sendActionBar(TextAPI.parse("§c§lEI! §cVocê não é dono desse spawner."));
            player.getInventory().close();
            return;
        }

        String currentState = SpawnersCacheGetters.getInstance().getSpawnerState(id);
        if (currentState.equalsIgnoreCase(States.PRIVATE.getState())) {
            SpawnersCacheSetters.getInstance().setSpawnerState(id, States.PUBLIC.getState());
        } else {
            SpawnersCacheSetters.getInstance().setSpawnerState(id, States.PRIVATE.getState());
        }

        Location location = LocationUtil.deserializeLocation(getters.getSpawnerLocation(id));
        if (location == null) return;

        DisplayBuilder.removeSpawnerDisplay(location);
        DisplayBuilder.createSpawnerDisplay
                (
                location,
                SpawnersUtils.getInstance().getSpawnerFromName(getters.getSpawnerType(id)).getType(),
                getters.getSpawnerAmount(id),
                SpawnersUtils.getInstance().getSpawnerState(States.valueOf(getters.getSpawnerState(id).toUpperCase())),
                getters.getSpawnerOwner(id)
                );

        player.playSound(player.getLocation(), Sound.ENTITY_VILLAGER_YES, 1.0f, 1.0f);
        open(player, id);
    }

    public static SpawnersInventory getInstance() {
        if (instance == null) instance = new SpawnersInventory();
        return instance;
    }
}
