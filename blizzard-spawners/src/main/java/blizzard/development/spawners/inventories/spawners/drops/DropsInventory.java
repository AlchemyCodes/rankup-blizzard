package blizzard.development.spawners.inventories.spawners.drops;

import blizzard.development.currencies.api.CurrenciesAPI;
import blizzard.development.currencies.enums.Currencies;
import blizzard.development.spawners.database.cache.getters.SpawnersCacheGetters;
import blizzard.development.spawners.database.cache.managers.SpawnersCacheManager;
import blizzard.development.spawners.database.cache.setters.SpawnersCacheSetters;
import blizzard.development.spawners.database.storage.SpawnersData;
import blizzard.development.spawners.handlers.bonus.BonusHandler;
import blizzard.development.spawners.handlers.spawners.SpawnersHandler;
import blizzard.development.spawners.inventories.spawners.drops.items.DropsItems;
import blizzard.development.spawners.inventories.spawners.main.MainInventory;
import blizzard.development.spawners.managers.spawners.SpawnerAccessManager;
import blizzard.development.spawners.tasks.spawners.drops.DropsAutoSellTaskManager;
import blizzard.development.spawners.utils.CooldownUtils;
import blizzard.development.spawners.utils.NumberFormat;
import blizzard.development.spawners.utils.SpawnersUtils;
import blizzard.development.spawners.utils.items.TextAPI;
import com.github.stefvanschie.inventoryframework.gui.GuiItem;
import com.github.stefvanschie.inventoryframework.gui.type.ChestGui;
import com.github.stefvanschie.inventoryframework.pane.StaticPane;
import com.github.stefvanschie.inventoryframework.pane.util.Slot;
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

        GuiItem dropsItem = new GuiItem(items.drops(player, id), event -> {
            sellDrops(player, id, Currencies.COINS);
            event.setCancelled(true);
        });

        GuiItem autoSellItem = new GuiItem(items.autoSell(id), event -> {
            if (!player.getName().equals(getters.getSpawnerOwner(id)) && !player.hasPermission("blizzard.spawners.admin")) {
                player.sendActionBar(TextAPI.parse("§c§lEI! §cVocê não é dono desse spawner."));
                player.getInventory().close();
                return;
            }

            changeAutoSellState(player, id);
            event.setCancelled(true);
        });

        GuiItem backItem = new GuiItem(items.back(), event -> {
            MainInventory.getInstance().open(player, id);
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
            player.getInventory().close();
            return;
        }

        double finalValue = (drops * unitValue) * (1 + (BonusHandler.getInstance().getPlayerBonus(player) / 100) );

        currencies.addBalance(player, currency, finalValue);
        setters.setSpawnerDrops(id, 0);

        utils.createCountdown(cooldownKey, 1, TimeUnit.SECONDS);

        String formattedValue = NumberFormat.getInstance().formatNumber(finalValue);

        player.sendActionBar(TextAPI.parse(
                "§a§lYAY! §aVocê vendeu os drops desse gerador por §2§l$§a§l" + formattedValue + " §7(" + NumberFormat.getInstance().formatNumber(BonusHandler.getInstance().getPlayerBonus(player)) + "% de bônus)§a."
        ));
        player.playSound(player.getLocation(), Sound.ENTITY_VILLAGER_YES, 1.0f, 1.0f);
        player.getOpenInventory().close();

    }

    public void changeAutoSellState(Player player, String id) {
        DropsAutoSellTaskManager manager = DropsAutoSellTaskManager.getInstance();
        SpawnersData data = SpawnersCacheManager.getInstance().getSpawnerData(id);

        if (getters.getDropsAutoSell(id)) {
            if (getters.getDropsAutoSellState(id)) {
                setters.setDropsAutoSellState(id, false);
                manager.stopTask(data.getId());
            } else {
                setters.setDropsAutoSellState(id, true);
                manager.startTask(data);
            }
            open(player, id);
            player.playSound(player.getLocation(), Sound.ENTITY_VILLAGER_YES, 1.0f, 1.0f);
        }
    }

    public static DropsInventory getInstance() {
        if (instance == null) instance = new DropsInventory();
        return instance;
    }
}
