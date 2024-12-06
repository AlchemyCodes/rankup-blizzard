package blizzard.development.fishing.inventories.items.bucket;

import blizzard.development.currencies.api.CurrenciesAPI;
import blizzard.development.currencies.enums.Currencies;
import blizzard.development.fishing.database.cache.methods.PlayersCacheMethod;
import blizzard.development.fishing.handlers.FishBucketHandler;
import blizzard.development.fishing.utils.ConfigUtils;
import blizzard.development.fishing.utils.NumberFormat;
import blizzard.development.fishing.utils.PluginImpl;
import blizzard.development.fishing.utils.items.ItemBuilder;
import com.github.stefvanschie.inventoryframework.gui.GuiItem;
import com.github.stefvanschie.inventoryframework.gui.type.ChestGui;
import com.github.stefvanschie.inventoryframework.pane.StaticPane;
import com.github.stefvanschie.inventoryframework.pane.util.Slot;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;

public class FishBucketUpgrades {
    public static void upgradesMenu(Player player) {
        CurrenciesAPI currenciesAPI = CurrenciesAPI.getInstance();
        PlayersCacheMethod cacheMethod = PlayersCacheMethod.getInstance();
        ConfigUtils enchantments = PluginImpl.getInstance().Enchantments;

        int storage = cacheMethod.getStorage(player);
        Currencies coins = Currencies.COINS;

        ChestGui gui = new ChestGui(3, "Melhorias do Balde");

        StaticPane pane = new StaticPane(0, 0, 9, 3);

        GuiItem capacity = new GuiItem(capacity(cacheMethod, player), event -> { event.setCancelled(true);
            if (currenciesAPI.getBalance(player, coins) < capacityPrice(cacheMethod, player)) {
                player.sendMessage("§cVocê não possue dinheiro suficiente!");
                return;
            }

            if (storage >= enchantments.getInt("enchantments.bucket.storage.maxstorage")) {
                player.sendMessage("§cSeu armazém já possui a capacidade máxima.");
                return;
            }

            currenciesAPI.removeBalance(player, coins, capacityPrice(cacheMethod, player));
            cacheMethod.setStorage(player, storage + enchantments.getInt("enchantments.bucket.storage.perupgrade"));
            FishBucketHandler.setBucket(player, player.getInventory().getHeldItemSlot());
            player.sendMessage("§b§lYAY! §7Armazém aumentado em 50.");
            upgradesMenu(player);});

        GuiItem back = new GuiItem(back(), event -> {
            event.setCancelled(true);
            FishBucketInventory.openBucket(player);
        });

        pane.addItem(capacity, Slot.fromIndex(13));
        pane.addItem(back, Slot.fromIndex(18));


        gui.addPane(pane);

        gui.show(player);
    }

    public static ItemStack back() {
        return new ItemBuilder(Material.RED_DYE)
                .setDisplayName("§cVoltar").setLore(Arrays.asList
                        ("§7Clique para voltar",
                        "§7ao menu anterior.")).build();
    }

    public static ItemStack capacity(PlayersCacheMethod playersCacheMethod, Player player) {
        return new ItemBuilder(Material.CHEST)
                .setDisplayName("§6Capacidade §7[" + playersCacheMethod.getStorage(player) + "]").setLore(Arrays.asList
                        ("§7Aumente a capacidade",
                                "§7de peixes do seu balde.",
                                "",
                                "§7Preço: §f" + NumberFormat.formatNumber(capacityPrice(playersCacheMethod, player)) + "§a$",
                                "",
                                "§6Clique para melhorar.")).build();
    }

    public static double capacityPrice(PlayersCacheMethod playersCacheMethod, Player player) {
        double initial = PluginImpl.getInstance().Enchantments.getDouble("enchantments.bucket.price.initial");
        double perLevel = PluginImpl.getInstance().Enchantments.getDouble("enchantments.bucket.price.perlevel");
        double multiplier = PluginImpl.getInstance().Enchantments.getDouble("enchantments.bucket.price.multiplier");

        int storage = playersCacheMethod.getStorage(player);
        int level = storage / 50;

        return initial + (perLevel * (level - 1)) * multiplier;
    }
}
