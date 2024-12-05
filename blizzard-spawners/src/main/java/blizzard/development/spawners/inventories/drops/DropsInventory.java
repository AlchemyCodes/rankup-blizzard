package blizzard.development.spawners.inventories.drops;

import blizzard.development.currencies.api.CurrenciesAPI;
import blizzard.development.currencies.enums.Currencies;
import blizzard.development.spawners.database.cache.getters.SpawnersCacheGetters;
import blizzard.development.spawners.database.cache.setters.SpawnersCacheSetters;
import blizzard.development.spawners.handlers.spawners.SpawnersHandler;
import blizzard.development.spawners.inventories.drops.items.DropsItems;
import blizzard.development.spawners.inventories.spawners.SpawnersInventory;
import blizzard.development.spawners.managers.SpawnerAccessManager;
import blizzard.development.spawners.utils.CooldownUtils;
import blizzard.development.spawners.utils.SpawnersUtils;
import blizzard.development.spawners.utils.items.TextAPI;
import com.github.stefvanschie.inventoryframework.gui.GuiItem;
import com.github.stefvanschie.inventoryframework.gui.type.ChestGui;
import com.github.stefvanschie.inventoryframework.pane.StaticPane;
import com.github.stefvanschie.inventoryframework.pane.util.Slot;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

import java.util.concurrent.TimeUnit;

public class DropsInventory {
    private static DropsInventory instance;

    private final SpawnersCacheGetters getters = SpawnersCacheGetters.getInstance();
    private final SpawnersCacheSetters setters = SpawnersCacheSetters.getInstance();
    private final CurrenciesAPI currencies = CurrenciesAPI.getInstance();
    private final SpawnersHandler handler = SpawnersHandler.getInstance();
    private final CooldownUtils utils = CooldownUtils.getInstance();

    private final DropsItems items = DropsItems.getInstance();

    public void open(Player player, String id) {
        final SpawnerAccessManager accessManager = SpawnerAccessManager.getInstance();

        accessManager.addInventoryUser(id, player.getName());

        ChestGui inventory = new ChestGui(4, "§8Drops");
        StaticPane pane = new StaticPane(0, 0, 9, 4);

        GuiItem bonusItem = new GuiItem(items.bonus(), event -> {
            sendBonusMessage(player);
            event.setCancelled(true);
        });

        GuiItem dropsItem = new GuiItem(items.drops(id), event -> {
            sellDrops(player, id, Currencies.COINS);
            event.setCancelled(true);
        });

        GuiItem autoSellItem = new GuiItem(items.autoSell(), event -> {
            event.setCancelled(true);
        });

        GuiItem backItem = new GuiItem(items.back(), event -> {
            SpawnersInventory.getInstance().open(player, id);
            event.setCancelled(true);
        });

        pane.addItem(bonusItem, Slot.fromIndex(10));
        pane.addItem(dropsItem, Slot.fromIndex(13));
        pane.addItem(autoSellItem, Slot.fromIndex(16));
        pane.addItem(backItem, Slot.fromIndex(27));

        inventory.addPane(pane);
        inventory.setOnClose(event -> {
            accessManager.removeInventoryUser(id, player.getName());
        });
        inventory.show(player);
    }

    public void sendBonusMessage(Player player) {
        player.sendMessage("");
        TextComponent donateLink = new TextComponent("§7 Clique §a§lAQUI §7para visitar o nosso site.");
        donateLink.setBold(true);
        donateLink.setClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, "https://www.alchemynetwork.net"));
        player.spigot().sendMessage(donateLink);
        player.sendMessage("");
        player.playSound(player.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 0.5f, 0.5f);
        player.getInventory().close();
    }

    public void sellDrops(Player player, String id, Currencies currency) {
        String cooldownKey = "blizzard.spawners.drops-cooldown;" + id;

        if (utils.isInCountdown(cooldownKey)) {
            player.sendActionBar(TextAPI.parse("§c§lEI! §cAguarde um pouco antes de vender."));
            player.getOpenInventory().close();
            player.playSound(player.getLocation(), Sound.ENTITY_VILLAGER_NO, 0.5f, 0.5f);
            return;
        }

        double drops = getters.getSpawnerDrops(id);
        double unitValue = handler.getSellDropPrice(
                SpawnersUtils.getInstance().getSpawnerFromName(getters.getSpawnerType(id)).toString().toLowerCase()
        );


        if (drops <= 0) {
            player.sendActionBar(TextAPI.parse("§c§lEI! §cA quantia de drops precisa ser maior que 0."));
            player.playSound(player.getLocation(), Sound.ENTITY_VILLAGER_NO, 0.5f, 0.5f);
            return;
        }

        currencies.addBalance(player, currency, drops * unitValue);
        setters.setSpawnerDrops(id, 0);

        utils.createCountdown(cooldownKey, 1, TimeUnit.SECONDS);

        player.playSound(player.getLocation(), Sound.ENTITY_VILLAGER_YES, 1.0f, 1.0f);
        DropsInventory.getInstance().open(player, id);
    }

    public static DropsInventory getInstance() {
        if (instance == null) instance = new DropsInventory();
        return instance;
    }
}
