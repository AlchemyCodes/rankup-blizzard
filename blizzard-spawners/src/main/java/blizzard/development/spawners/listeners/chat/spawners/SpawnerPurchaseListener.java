package blizzard.development.spawners.listeners.chat.spawners;

import blizzard.development.currencies.api.CurrenciesAPI;
import blizzard.development.currencies.enums.Currencies;
import blizzard.development.spawners.handlers.bonus.BonusHandler;
import blizzard.development.spawners.handlers.spawners.SpawnersHandler;
import blizzard.development.spawners.inventories.spawners.shop.ShopInventory;
import blizzard.development.spawners.utils.NumberFormat;
import blizzard.development.spawners.utils.PluginImpl;
import blizzard.development.spawners.utils.items.TextAPI;
import io.papermc.paper.event.player.AsyncChatEvent;
import net.kyori.adventure.text.serializer.plain.PlainTextComponentSerializer;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashMap;
import java.util.Map;

public class SpawnerPurchaseListener implements Listener {

    private static final Map<Player, BukkitRunnable> purchaseTimeouts = new HashMap<>();
    public static final Map<Player, String> pendingPurchases = new HashMap<>();

    private final NumberFormat format = NumberFormat.getInstance();
    private final ShopInventory shop = ShopInventory.getInstance();

    public static void startPurchaseProcess(Player player, String spawnerType) {
        if (purchaseTimeouts.containsKey(player)) {
            purchaseTimeouts.get(player).cancel();
        }

        BukkitRunnable timeoutTask = new BukkitRunnable() {
            @Override
            public void run() {
                player.sendActionBar("§c§lEI! §cO tempo para definir a quantidade expirou.");
                cancelPurchaseProcess(player);
            }
        };
        timeoutTask.runTaskLater(PluginImpl.getInstance().plugin, 20 * 30);

        purchaseTimeouts.put(player, timeoutTask);
        pendingPurchases.put(player, spawnerType);
    }

    public static void cancelPurchaseProcess(Player player) {
        if (purchaseTimeouts.containsKey(player)) {
            purchaseTimeouts.get(player).cancel();
            purchaseTimeouts.remove(player);
        }
        pendingPurchases.remove(player);
    }

    @EventHandler
    public void onPlayerChat(AsyncChatEvent event) {
        Player player = event.getPlayer();
        if (!pendingPurchases.containsKey(player)) {
            return;
        }

        event.setCancelled(true);
        String message = PlainTextComponentSerializer.plainText().serialize(event.message());

        if (message.equalsIgnoreCase("cancelar")) {
            player.sendActionBar("§c§lEI! §cVocê cancelou a compra de spawners.");
            cancelPurchaseProcess(player);
            return;
        }

        processPurchase(player, message);

    }

    private void processPurchase(Player player, String amount) {

        double finalAmount;
        try {
            finalAmount = Double.parseDouble(amount);
            if (finalAmount <= 0) {
                player.sendActionBar(TextAPI.parse("§c§lEI! §cO valor inserido é inválido."));
                return;
            }
        } catch (NumberFormatException ex) {
            if (format.parseNumber(amount) == null) {
                player.sendActionBar(TextAPI.parse("§c§lEI! §cO valor inserido é inválido."));
                return;
            }
            finalAmount = format.parseNumber(amount);
        }

        String spawnerType = pendingPurchases.get(player);
        SpawnersHandler handler = SpawnersHandler.getInstance();
        CurrenciesAPI currencies = CurrenciesAPI.getInstance();

        double spawnerPrice = handler.getBuyPrice(spawnerType.toLowerCase());
        double totalCost = (spawnerPrice * finalAmount) * (1 - (BonusHandler.getInstance().getPlayerBonus(player) / 100) );
        double playerBalance = currencies.getBalance(player, Currencies.COINS);

        if (playerBalance >= totalCost) {
            if (shop.hasEmptySlot(player)) {
                if (currencies.getBalance(player, Currencies.SPAWNERSLIMIT) >= finalAmount) {
                    double finalValue = (spawnerPrice) * (1 - (BonusHandler.getInstance().getPlayerBonus(player) / 100) );
                    shop.giveSpawners(player, spawnerType.toLowerCase(), finalAmount, finalValue);
                    currencies.removeBalance(player, Currencies.COINS, totalCost);
                    player.playSound(player.getLocation(), Sound.ENTITY_VILLAGER_YES, 1.0f, 1.0f);
                } else {
                    player.sendActionBar(shop.limitsErrorMessage());
                }
            } else {
                player.sendActionBar(TextAPI.parse("§c§lEI! §cVocê precisa de pelo menos um slot vazio."));
            }
        } else {
            double missingBalance = totalCost - playerBalance;
            player.sendActionBar(shop.spawnersErrorMessage(missingBalance));
            player.playSound(player.getLocation(), Sound.ENTITY_VILLAGER_NO, 0.5f, 0.5f);
        }
        cancelPurchaseProcess(player);
    }
}
