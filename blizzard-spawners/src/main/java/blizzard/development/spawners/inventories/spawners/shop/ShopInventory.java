package blizzard.development.spawners.inventories.spawners.shop;

import blizzard.development.currencies.api.CurrenciesAPI;
import blizzard.development.currencies.enums.Currencies;
import blizzard.development.spawners.database.cache.setters.PlayersCacheSetters;
import blizzard.development.spawners.handlers.bonus.BonusHandler;
import blizzard.development.spawners.handlers.enchantments.EnchantmentsHandler;
import blizzard.development.spawners.handlers.enums.spawners.Enchantments;
import blizzard.development.spawners.handlers.enums.spawners.Spawners;
import blizzard.development.spawners.handlers.mobs.MobsHandler;
import blizzard.development.spawners.handlers.spawners.SpawnersHandler;
import blizzard.development.spawners.inventories.spawners.ranking.PurchasedInventory;
import blizzard.development.spawners.inventories.spawners.shop.items.ShopItems;
import blizzard.development.spawners.listeners.chat.spawners.SpawnerPurchaseListener;
import blizzard.development.spawners.utils.NumberFormat;
import blizzard.development.spawners.utils.PluginImpl;
import blizzard.development.spawners.utils.SpawnersUtils;
import blizzard.development.spawners.utils.items.TextAPI;
import com.github.stefvanschie.inventoryframework.gui.GuiItem;
import com.github.stefvanschie.inventoryframework.gui.type.ChestGui;
import com.github.stefvanschie.inventoryframework.pane.StaticPane;
import com.github.stefvanschie.inventoryframework.pane.util.Slot;
import org.bukkit.Sound;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;
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
            String discount = (format.formatNumber(BonusHandler.getInstance().getPlayerBonus(player)));

            ItemStack spawnerItem = items.spawner(itemType, displayName, lore, released, cost, dropCost, discount);

            GuiItem guiItem = new GuiItem(spawnerItem, event -> {
                if (released) {
                    buySpawners(player, event, Objects.requireNonNull(spawnerType), Currencies.COINS);
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
        final PlayersCacheSetters setters = PlayersCacheSetters.getInstance();

        String spawner = spawnerType.toLowerCase();
        double spawnerPrice = handler.getBuyPrice(spawner);
        double playerBalance = currencies.getBalance(player, currency);

        if (event.getClick().isLeftClick()) {
            SpawnerPurchaseListener.startPurchaseProcess(player, spawnerType);
            List<String> messages = Arrays.asList(
                    "",
                    "§a Digite no chat a quantia de spawners que deseja!",
                    "§7 Digite 'cancelar' para cancelar a compra.",
                    ""
            );
            messages.forEach(player::sendMessage);
            player.getOpenInventory().close();

        } else if (event.getClick().isRightClick()) {
            double finalValue = (spawnerPrice) * (1 - (BonusHandler.getInstance().getPlayerBonus(player) / 100) );
            if (playerBalance >= finalValue) {
                if (hasEmptySlot(player))  {
                    if (currencies.getBalance(player, Currencies.SPAWNERSLIMIT) >= 1) {
                        giveSpawners(player, spawner, 1, finalValue);
                        currencies.removeBalance(player, currency, finalValue);
                        setters.addPurchasedSpawners(player, 1);
                        player.playSound(player.getLocation(), Sound.ENTITY_VILLAGER_YES, 1.0f, 1.0f);
                    } else {
                        player.sendActionBar(limitsErrorMessage());
                    }
                } else {
                    player.sendActionBar(TextAPI.parse("§c§lEI! §cVocê precisa de pelo menos um slot vazio."));
                }
            } else {
                double missingBalance = finalValue - playerBalance;
                player.sendActionBar(spawnersErrorMessage(missingBalance));
                player.playSound(player.getLocation(), Sound.ENTITY_VILLAGER_NO, 0.5f, 0.5f);
            }

        } else if (event.getAction().equals(InventoryAction.DROP_ONE_SLOT)
                || event.getAction().equals(InventoryAction.DROP_ALL_SLOT)) {

            double limits = CurrenciesAPI.getInstance().getBalance(player, Currencies.SPAWNERSLIMIT);

            double finalValue = (spawnerPrice * limits) * (1 - (BonusHandler.getInstance().getPlayerBonus(player) / 100) );
            if (playerBalance >= finalValue) {
                if (hasEmptySlot(player)) {
                    giveSpawners(player, spawner, limits, finalValue);
                    currencies.removeBalance(player, currency, finalValue);
                    setters.addPurchasedSpawners(player, limits);
                    player.playSound(player.getLocation(), Sound.ENTITY_VILLAGER_YES, 1.0f, 1.0f);
                } else {
                    player.sendActionBar(TextAPI.parse("§c§lEI! §cVocê precisa de pelo menos um slot vazio."));
                }
            } else {
                double missingBalance = finalValue - playerBalance;
                player.sendActionBar(spawnersErrorMessage(missingBalance));
                player.playSound(player.getLocation(), Sound.ENTITY_VILLAGER_NO, 0.5f, 0.5f);
            }
        }
    }

    public void giveSpawners(Player player, String spawnerType, double amount, double price) {
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
                player.sendActionBar(TextAPI.parse(spawnersSuccessMessage(player, spawnerType, amount, price)));
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
                player.sendActionBar(TextAPI.parse(spawnersSuccessMessage(player, spawnerType, amount, price)));
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
                player.sendActionBar(TextAPI.parse(spawnersSuccessMessage(player, spawnerType, amount, price)));
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
                player.sendActionBar(TextAPI.parse(spawnersSuccessMessage(player, spawnerType, amount, price)));
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
                player.sendActionBar(TextAPI.parse(spawnersSuccessMessage(player, spawnerType, amount, price)));
            }
        }
    }

    public boolean hasEmptySlot(Player player) {
        return player.getInventory().firstEmpty() != -1;
    }

    public String spawnersSuccessMessage(Player player, String type, Double amount, Double price) {
        String formattedAmount = NumberFormat.getInstance().formatNumber(amount);
        return "§a§lYAY! §aVocê comprou §fx" + formattedAmount + " §aspawner(s) de §f" +
                SpawnersUtils.getInstance().getMobNameBySpawner(
                        Spawners.valueOf(type.toUpperCase())) + "§a por §2§l$§a" + NumberFormat.getInstance().formatNumber(price) + " §7(" + NumberFormat.getInstance().formatNumber(BonusHandler.getInstance().getPlayerBonus(player)) + "% de desconto)§a.";
    }

    public String spawnersErrorMessage(double amount) {
        String formattedAmount = NumberFormat.getInstance().formatNumber(amount);
        return "§c§lEI! §cVocê precisa de mais §l$" + formattedAmount + "§c para comprar esse spawner.";
    }

    public String limitsErrorMessage() {
        return "§c§lEI! §cVocê não tem limite suficiente para comprar esse spawner.";
    }

    public static ShopInventory getInstance() {
        if (instance == null) instance = new ShopInventory();
        return instance;
    }
}
