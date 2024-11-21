package blizzard.development.tops.inventories;

import blizzard.development.currencies.api.CurrenciesAPI;
import blizzard.development.currencies.database.storage.PlayersData;
import blizzard.development.currencies.enums.Currencies;
import blizzard.development.tops.builders.ItemBuilder;
import blizzard.development.tops.utils.items.SkullAPI;
import blizzard.development.tops.utils.items.TextAPI;
import com.github.stefvanschie.inventoryframework.gui.GuiItem;
import com.github.stefvanschie.inventoryframework.gui.type.ChestGui;
import com.github.stefvanschie.inventoryframework.pane.StaticPane;
import com.github.stefvanschie.inventoryframework.pane.util.Slot;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;
import java.util.List;

public class CurrenciesInventory {
    private static CurrenciesInventory instance;

    public void open(Player player, String currency) {
        ChestGui inventory = new ChestGui(4, "§8Destaques - " + currency);

        StaticPane pane = new StaticPane(0, 0, 9, 4);

        CurrenciesAPI api = CurrenciesAPI.getInstance();

        List<PlayersData> topPlayers = api.getTopPlayers(Currencies.SOULS);

        String[] slots = {"10", "11", "12", "13", "14", "15", "16", "21", "22", "23"};

        for (int i = 0; i < topPlayers.size(); i++) {
            if (topPlayers.get(i) != null) {
                String name = topPlayers.get(i).getNickname();
                int position = (i + 1);
                double amount = topPlayers.get(i).getSouls();

                GuiItem currencyItem = new GuiItem(currency(name, position, currency, amount), event -> {
                    event.setCancelled(true);
                });

                pane.addItem(currencyItem, Slot.fromIndex(Integer.parseInt(slots[i])));
            }
        }

        inventory.addPane(pane);

        player.playSound(player.getLocation(), Sound.BLOCK_CHEST_OPEN, 0.5f, 0.5f);

        inventory.show(player);
    }

    public ItemStack currency(String player, int position, String currency, double amount) {
        ItemStack item = SkullAPI.withName(new ItemStack(Material.PLAYER_HEAD), player);
        ItemMeta meta = item.getItemMeta();

        String display = "§8 " + player;
        List<String> lore = Arrays.asList(
                "§8Posição: #" + position,
                "§7 " + currency + ":" + amount
        );

        meta.displayName(TextAPI.parse(display));
        meta.setLore(lore);
        item.setItemMeta(meta);

        return item;
    }

    public static CurrenciesInventory getInstance() {
        if (instance == null) instance = new CurrenciesInventory();
        return instance;
    }
}
