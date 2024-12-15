package blizzard.development.spawners.inventories.shop;

import blizzard.development.currencies.api.CurrenciesAPI;
import blizzard.development.currencies.enums.Currencies;
import blizzard.development.spawners.handlers.enchantments.EnchantmentsHandler;
import blizzard.development.spawners.handlers.enums.Enchantments;
import blizzard.development.spawners.handlers.enums.Spawners;
import blizzard.development.spawners.handlers.mobs.MobsHandler;
import blizzard.development.spawners.handlers.spawners.SpawnersHandler;
import blizzard.development.spawners.inventories.ranking.PurchasedInventory;
import blizzard.development.spawners.inventories.ranking.items.RankingItems;
import blizzard.development.spawners.inventories.shop.items.ShopItems;
import blizzard.development.spawners.utils.NumberFormat;
import blizzard.development.spawners.utils.PluginImpl;
import blizzard.development.spawners.utils.items.TextAPI;
import com.github.stefvanschie.inventoryframework.gui.GuiItem;
import com.github.stefvanschie.inventoryframework.gui.type.ChestGui;
import com.github.stefvanschie.inventoryframework.pane.StaticPane;
import com.github.stefvanschie.inventoryframework.pane.util.Slot;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.List;
import java.util.Objects;
import java.util.Set;

public class ShopInventory {
    private static ShopInventory instance;

    public void open(Player player) {
        ChestGui inventory = new ChestGui(5, "§8Spawners");

        final ShopItems items = ShopItems.getInstance();
        final FileConfiguration config = PluginImpl.getInstance().Spawners.getConfig();
        final NumberFormat format = NumberFormat.getInstance();

        StaticPane pane = new StaticPane(0, 0, 9, 5);

        Set<String> spawnerKeys = Objects.requireNonNull(config.getConfigurationSection("spawners")).getKeys(false);

        int[] slots = {11, 12, 13, 14, 15, 20, 21, 22, 23, 24};

        int index = 0;
        for (String key : spawnerKeys) {
            if (index >= slots.length) break;

            String spawnerType = config.getString("spawners." + key + ".type");
            String itemType = config.getString("spawners." + key + ".item");
            String displayName = config.getString("spawners." + key + ".display-name");
            List<String> lore = config.getStringList("spawners." + key + ".lore");
            boolean released = config.getBoolean("spawners." + key + ".permitted-purchase");
            String cost = format.formatNumber(config.getInt("spawners." + key + ".buy-price"));
            String dropCost = format.formatNumber(config.getInt("spawners." + key + ".sell-drop-price"));

            ItemStack spawnerItem = items.spawner(itemType, displayName, lore, released, cost, dropCost);

            GuiItem guiItem = new GuiItem(spawnerItem, event -> {
                if (released) {
                    buySpawners(player, event, spawnerType, Currencies.COINS);
                    player.getOpenInventory().close();
                }
                event.setCancelled(true);
            });
            pane.addItem(guiItem, Slot.fromIndex(slots[index]));
            index++;
        }

        while (index < slots.length) {
            GuiItem comingSoonItem = new GuiItem(items.comingSoon(), event -> event.setCancelled(true));
            pane.addItem(comingSoonItem, Slot.fromIndex(slots[index]));
            index++;
        }

        GuiItem rankingItem = new GuiItem(items.ranking(), event -> {
            PurchasedInventory.getInstance().open(player);
            event.setCancelled(true);
        });

        GuiItem informationsItem = new GuiItem(items.informations(player), event -> event.setCancelled(true));

        pane.addItem(rankingItem, Slot.fromIndex(39));
        pane.addItem(informationsItem, Slot.fromIndex(41));

        inventory.addPane(pane);
        inventory.show(player);
    }

    public void buySpawners(Player player, InventoryClickEvent event, String spawnerType, Currencies currency) {
        final CurrenciesAPI currencies = CurrenciesAPI.getInstance();
        final SpawnersHandler handler = SpawnersHandler.getInstance();

        String spawner = spawnerType.toLowerCase();
        double spawnerPrice = handler.getBuyPrice(spawner);

        if (event.getClick().isLeftClick()) {
            player.sendMessage("Você apertou com o botao esquerdo no spawner de " + spawnerType);

        } else if (event.getClick().isRightClick()) {
            if (currencies.getBalance(player, currency) >= spawnerPrice) {
                giveSpawners(player, spawner, 1);
                currencies.removeBalance(player, currency, spawnerPrice);
            } else {
                player.sendActionBar(spawnersErrorMessage());
            }

        } else if (event.getAction().equals(InventoryAction.DROP_ONE_SLOT)
                || event.getAction().equals(InventoryAction.DROP_ALL_SLOT)) {

            double limits = CurrenciesAPI.getInstance().getBalance(player, Currencies.SPAWNERSLIMIT);

            if (currencies.getBalance(player, currency) >= (spawnerPrice * limits)) {
                giveSpawners(player, spawner, limits);
                currencies.removeBalance(player, currency, (spawnerPrice * limits));
            } else {
                player.sendActionBar(spawnersErrorMessage());
            }
        }
    }

    public static ShopInventory getInstance() {
        if (instance == null) instance = new ShopInventory();
        return instance;
    }

    public void giveSpawners(Player player, String spawnerType, double amount) {
        final EnchantmentsHandler handler = EnchantmentsHandler.getInstance();

        switch (spawnerType) {
            case "pigs", "pig", "porcos", "porco" -> {
                MobsHandler.giveMobSpawner(
                        player,
                        Spawners.PIG,
                        amount,
                        1,
                        handler.getInitialLevel(Enchantments.SPEED.getName()),
                        handler.getInitialLevel(Enchantments.LUCKY.getName()),
                        handler.getInitialLevel(Enchantments.EXPERIENCE.getName()),
                        PluginImpl.getInstance().Config.getInt("spawners.initial-friends-limit"),
                        false
                );
                player.sendActionBar(TextAPI.parse(spawnersSuccessMessage(spawnerType, amount)));
            }
            case "cows", "cow", "vacas", "vaca" -> {
                MobsHandler.giveMobSpawner(
                        player,
                        Spawners.COW,
                        amount,
                        1,
                        handler.getInitialLevel(Enchantments.SPEED.getName()),
                        handler.getInitialLevel(Enchantments.LUCKY.getName()),
                        handler.getInitialLevel(Enchantments.EXPERIENCE.getName()),
                        PluginImpl.getInstance().Config.getInt("spawners.initial-friends-limit"),
                        false
                );
                player.sendActionBar(TextAPI.parse(spawnersSuccessMessage(spawnerType, amount)));
            }
            case "mooshrooms", "mooshroom", "coguvacas", "coguvaca" -> {
                MobsHandler.giveMobSpawner(
                        player,
                        Spawners.MOOSHROOM,
                        amount,
                        1,
                        handler.getInitialLevel(Enchantments.SPEED.getName()),
                        handler.getInitialLevel(Enchantments.LUCKY.getName()),
                        handler.getInitialLevel(Enchantments.EXPERIENCE.getName()),
                        PluginImpl.getInstance().Config.getInt("spawners.initial-friends-limit"),
                        false
                );
                player.sendActionBar(TextAPI.parse(spawnersSuccessMessage(spawnerType, amount)));
            }
            case "sheeps", "sheep", "ovelhas", "ovelha" -> {
                MobsHandler.giveMobSpawner(
                        player,
                        Spawners.SHEEP,
                        amount,
                        1,
                        handler.getInitialLevel(Enchantments.SPEED.getName()),
                        handler.getInitialLevel(Enchantments.LUCKY.getName()),
                        handler.getInitialLevel(Enchantments.EXPERIENCE.getName()),
                        PluginImpl.getInstance().Config.getInt("spawners.initial-friends-limit"),
                        false
                );
                player.sendActionBar(TextAPI.parse(spawnersSuccessMessage(spawnerType, amount)));
            }
            case "zombies", "zombie", "zumbis", "zumbi" -> {
                MobsHandler.giveMobSpawner(
                        player,
                        Spawners.ZOMBIE,
                        amount,
                        1,
                        handler.getInitialLevel(Enchantments.SPEED.getName()),
                        handler.getInitialLevel(Enchantments.LUCKY.getName()),
                        handler.getInitialLevel(Enchantments.EXPERIENCE.getName()),
                        PluginImpl.getInstance().Config.getInt("spawners.initial-friends-limit"),
                        false
                );
                player.sendActionBar(TextAPI.parse(spawnersSuccessMessage(spawnerType, amount)));
            }
        }
    }

    public String spawnersSuccessMessage(String type, Double amount) {
        String formattedAmount = NumberFormat.getInstance().formatNumber(amount);
        return "§a§lYAY! §aVocê comprou §fx" + formattedAmount + " spawner(s) §ade §f" + type + "§a!";
    }

    public String spawnersErrorMessage() {
        return "§c§lEI! §cVocê não possui dinheiro para adquirir esse spawner.";
    }
}
