package blizzard.development.tops.inventories.coins;

import app.miyuki.miyukieconomy.common.user.User;
import blizzard.development.tops.handlers.CurrenciesHandler;
import blizzard.development.tops.inventories.TopsInventory;
import blizzard.development.tops.inventories.currencies.CurrenciesInventory;
import blizzard.development.tops.utils.NumberFormat;
import blizzard.development.tops.utils.items.SkullAPI;
import blizzard.development.tops.utils.items.TextAPI;
import com.github.stefvanschie.inventoryframework.gui.GuiItem;
import com.github.stefvanschie.inventoryframework.gui.type.ChestGui;
import com.github.stefvanschie.inventoryframework.pane.StaticPane;
import com.github.stefvanschie.inventoryframework.pane.util.Slot;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;
import java.util.List;

public class CoinsInventory {
    private static CoinsInventory instance;

    public void open(Player player, String currency) {
        ChestGui inventory = new ChestGui(4, "§8Destaques - " + currency);

        StaticPane pane = new StaticPane(0, 0, 9, 4);

        CurrenciesHandler currenciesHandler = CurrenciesHandler.getInstance();

        List<User> topPlayers = currenciesHandler.getCoinsTopPlayers();

        String[] slots = {"10", "11", "12", "13", "14", "15", "16", "21", "22", "23"};

        for (int i = 0; i < slots.length; i++) {
            if (i < topPlayers.size()) {
                User user = topPlayers.get(i);
                String name = user.getName();

                double amount = user.getCurrency("yen").getBalance().doubleValue();

                GuiItem currencyItem = new GuiItem(currency(name, i, currency, amount), event -> event.setCancelled(true));
                pane.addItem(currencyItem, Slot.fromIndex(Integer.parseInt(slots[i])));
            } else {
                GuiItem placeholderItem = new GuiItem(nothing(i), event -> event.setCancelled(true));
                pane.addItem(placeholderItem, Slot.fromIndex(Integer.parseInt(slots[i])));
            }
        }

        GuiItem backItem = new GuiItem(back(), event -> {
            TopsInventory.getInstance().open(player);
            event.setCancelled(true);
        });

        pane.addItem(backItem, Slot.fromIndex(27));

        inventory.addPane(pane);

        player.playSound(player.getLocation(), Sound.BLOCK_CHEST_OPEN, 0.5f, 0.5f);

        inventory.show(player);
    }

    public ItemStack currency(String player, int position, String currency, double amount) {
        ItemStack item = SkullAPI.withName(new ItemStack(Material.PLAYER_HEAD), player);
        ItemMeta meta = item.getItemMeta();

        String display = "§6Destaque #§l" + (position + 1);
        List<String> lore = Arrays.asList(
                "",
                " §7Nome: §f" + player,
                " §7" + currency + ": §f" + NumberFormat.getInstance().formatNumber(amount),
                ""
        );

        meta.displayName(TextAPI.parse(display));
        meta.setLore(lore);
        item.setItemMeta(meta);
        return item;
    }

    public ItemStack nothing(int position) {
        ItemStack item = new ItemStack(Material.BARRIER);
        ItemMeta meta = item.getItemMeta();
        meta.displayName(TextAPI.parse("§6Destaque #§l" + (position + 1)));
        meta.setLore(List.of("§7Nenhuma informação."));
        item.setItemMeta(meta);
        return item;
    }

    public ItemStack back() {
        ItemStack item = new ItemStack(Material.RED_DYE);
        ItemMeta meta = item.getItemMeta();
        meta.displayName(TextAPI.parse("§cVoltar"));
        meta.setLore(List.of("§7Clique aqui para voltar."));
        item.setItemMeta(meta);
        return item;
    }

    public static CoinsInventory getInstance() {
        if (instance == null) instance = new CoinsInventory();
        return instance;
    }
}
