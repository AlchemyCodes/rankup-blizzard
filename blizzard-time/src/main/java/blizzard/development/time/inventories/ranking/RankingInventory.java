package blizzard.development.time.inventories.ranking;

import blizzard.development.time.database.cache.getters.PlayersCacheGetters;
import blizzard.development.time.database.storage.PlayersData;
import blizzard.development.time.inventories.TimeInventory;
import blizzard.development.time.utils.TimeConverter;
import blizzard.development.time.utils.items.SkullAPI;
import blizzard.development.time.utils.items.TextAPI;
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

public class RankingInventory {
    private static RankingInventory instance;

    public void open(Player player) {
        ChestGui inventory = new ChestGui(4, "§8Destaques");

        StaticPane pane = new StaticPane(0, 0, 9, 4);

        final List<PlayersData> topPlayers = PlayersCacheGetters.getInstance().getTopPlayers();

        String[] slots = {"10", "11", "12", "13", "14", "15", "16", "21", "22", "23"};

        for (int i = 0; i < slots.length; i++) {
            if (i < topPlayers.size()) {
                PlayersData playerData = topPlayers.get(i);
                String name = playerData.getNickname();

                long time = playerData.getPlayTime();

                GuiItem currencyItem = new GuiItem(currency(name, i, time), event -> event.setCancelled(true));
                pane.addItem(currencyItem, Slot.fromIndex(Integer.parseInt(slots[i])));
            } else {
                GuiItem placeholderItem = new GuiItem(nothing(i), event -> event.setCancelled(true));
                pane.addItem(placeholderItem, Slot.fromIndex(Integer.parseInt(slots[i])));
            }
        }

        GuiItem backItem = new GuiItem(back(), event -> {
            TimeInventory.getInstance().open(player);
            event.setCancelled(true);
        });

        pane.addItem(backItem, Slot.fromIndex(27));

        inventory.addPane(pane);
        inventory.show(player);
    }

    public ItemStack currency(String player, int position, long time) {
        ItemStack item = SkullAPI.withName(new ItemStack(Material.PLAYER_HEAD), player);
        ItemMeta meta = item.getItemMeta();

        String timeToString = TimeConverter.convertSecondsToTimeFormat(time);

        String display = "§6Destaque #§l" + (position + 1);
        List<String> lore = Arrays.asList(
                "",
                " §7Nome: §f" + player,
                " §7Tempo: §f" + timeToString,
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

    public static RankingInventory getInstance() {
        if (instance == null) instance = new RankingInventory();
        return instance;
    }
}
