package blizzard.development.spawners.listeners.limits;

import blizzard.development.currencies.api.CurrenciesAPI;
import blizzard.development.currencies.enums.Currencies;
import blizzard.development.spawners.builders.ItemBuilder;
import blizzard.development.spawners.handlers.limits.LimitsHandler;
import blizzard.development.spawners.utils.NumberFormat;
import blizzard.development.spawners.utils.PluginImpl;
import blizzard.development.spawners.utils.items.TextAPI;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

import java.util.ArrayList;
import java.util.List;

public class CommonLimitInteractListener implements Listener {

    private static final String purchaseKey = "blizzard.spawners-purchaselimit";
    private static final String friendsKey = "blizzard.spawners-friendslimit";

    @EventHandler
    public void onPurchaseLimitInteract(PlayerInteractEvent event) {
        final Player player = event.getPlayer();

        final ItemStack limitItem = player.getInventory().getItemInMainHand();

        if (event.getAction().isRightClick()) {
            if (ItemBuilder.hasPersistentData(PluginImpl.getInstance().plugin, limitItem, purchaseKey)) {

                String amount = ItemBuilder.getPersistentData(PluginImpl.getInstance().plugin, limitItem, purchaseKey);
                if (amount == null) return;

                activatePurchaseLimit(player, amount);

                event.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onFriendsLimitInteract(PlayerInteractEvent event) {
        final Player player = event.getPlayer();

        final ItemStack limitItem = player.getInventory().getItemInMainHand();

        if (event.getAction().equals(Action.RIGHT_CLICK_AIR)) {
            if (ItemBuilder.hasPersistentData(PluginImpl.getInstance().plugin, limitItem, friendsKey)) {

                String amount = ItemBuilder.getPersistentData(PluginImpl.getInstance().plugin, limitItem, friendsKey);
                if (amount == null) return;

                activateFriendsLimit(player);

                event.setCancelled(true);
            }
        }
    }

    public void activatePurchaseLimit(Player player, String limitAmount) {
        PlayerInventory inventory = player.getInventory();
        CurrenciesAPI api = CurrenciesAPI.getInstance();

        double amount;
        List<Integer> limitsToRemove = new ArrayList<>();

        if (player.isSneaking()) {
            ItemStack[] contents = inventory.getContents();
            double totalLimitAmount = 0;
            int totalAmount = 0;

            for (int i = 0; i < contents.length; i++) {
                ItemStack item = contents[i];
                if (item != null && ItemBuilder.hasPersistentData(PluginImpl.getInstance().plugin, item, purchaseKey)) {
                    String itemLimitAmount = ItemBuilder.getPersistentData(PluginImpl.getInstance().plugin, item, purchaseKey);

                    if (itemLimitAmount != null) {
                        totalLimitAmount += Double.parseDouble(itemLimitAmount) * item.getAmount();
                        totalAmount += item.getAmount();
                        limitsToRemove.add(i);
                    }
                }
            }

            for (Integer index : limitsToRemove) {
                if (totalAmount > 1) {
                    inventory.setItem(index, null);
                }
            }

            if (totalAmount > 1) {
                LimitsHandler.givePurchaseLimit(player, totalLimitAmount, 1);
                player.sendMessage("§a§lYAY! §aVocê agrupou todos seus limites em um só.");
                return;
            }
        }

        ItemStack mainHandItem = inventory.getItemInMainHand();
        int limitItemAmount = mainHandItem.getAmount();

        if (limitItemAmount == 1) {
            inventory.setItemInMainHand(null);
        } else {
            mainHandItem.setAmount(limitItemAmount - 1);
        }

        amount = Double.parseDouble(limitAmount) * (player.isSneaking() ? limitItemAmount : 1);

        String formattedAmount = NumberFormat.getInstance().formatNumber(amount);
        player.sendActionBar(TextAPI.parse("§a§lYAY! §aVocê ativou §fx" + formattedAmount + " limite(s) §ade §fcompra§a."));
        api.addBalance(player, Currencies.SPAWNERSLIMIT, amount);
    }

    public void activateFriendsLimit(Player player) {
        PlayerInventory inventory = player.getInventory();

        List<Integer> limitsToRemove = new ArrayList<>();

        if (player.isSneaking()) {
            ItemStack[] contents = inventory.getContents();
            double totalLimitAmount = 0;
            int totalAmount = 0;

            for (int i = 0; i < contents.length; i++) {
                ItemStack item = contents[i];
                if (item != null && ItemBuilder.hasPersistentData(PluginImpl.getInstance().plugin, item, friendsKey)) {
                    String itemLimitAmount = ItemBuilder.getPersistentData(PluginImpl.getInstance().plugin, item, friendsKey);

                    if (itemLimitAmount != null) {

                        totalLimitAmount += Double.parseDouble(itemLimitAmount) * item.getAmount();
                        totalAmount += item.getAmount();
                        limitsToRemove.add(i);
                    }
                }
            }

            for (Integer index : limitsToRemove) {
                if (totalAmount > 1) {
                    inventory.setItem(index, null);
                }
            }

            if (totalAmount > 1) {
                LimitsHandler.giveFriendsLimit(player, totalLimitAmount, 1);
                player.sendMessage("§a§lYAY! §aVocê agrupou todos seus limites em um só.");
            }
        }
    }
}