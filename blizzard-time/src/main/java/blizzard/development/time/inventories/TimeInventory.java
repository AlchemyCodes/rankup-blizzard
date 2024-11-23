package blizzard.development.time.inventories;

import blizzard.development.time.database.cache.getters.PlayersCacheGetters;
import blizzard.development.time.database.cache.managers.PlayersCacheManager;
import blizzard.development.time.database.storage.PlayersData;
import blizzard.development.time.inventories.ranking.RankingInventory;
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

public class TimeInventory {
    private static TimeInventory instance;

    public void open(Player player) {
        ChestGui inventory = new ChestGui(3, "§8Tempo");
        StaticPane pane = new StaticPane(0, 0, 9, 3);

        GuiItem profileItem = new GuiItem(profile(player), event -> {
            event.setCancelled(true);
        });

        GuiItem rewardsItem = new GuiItem(rewards(), event -> {
            event.setCancelled(true);
        });

        GuiItem rankingItem = new GuiItem(ranking(), event -> {
            RankingInventory.getInstance().open(player);
            event.setCancelled(true);
        });


        pane.addItem(profileItem, Slot.fromIndex(10));
        pane.addItem(rewardsItem, Slot.fromIndex(13));
        pane.addItem(rankingItem, Slot.fromIndex(16));


        inventory.addPane(pane);
        inventory.show(player);
        player.playSound(player.getLocation(), Sound.BLOCK_CHEST_OPEN, 0.5f, 0.5f);
    }

    public ItemStack profile(Player player) {
        ItemStack skull = SkullAPI.withName(new ItemStack(Material.PLAYER_HEAD), player.getName());
        ItemMeta meta = skull.getItemMeta();
        meta.displayName(TextAPI.parse("§ePerfil"));

        PlayersData data = PlayersCacheManager.getInstance().getPlayerData(player);

        String formattedTime = TimeConverter.convertSecondsToTimeFormat(data.getPlayTime());

        meta.setLore(Arrays.asList(
                "",
                "§7 Tempo Online",
                "§8 ■§f " + formattedTime,
                ""
        ));
        skull.setItemMeta(meta);

        return skull;
    }

    public ItemStack rewards() {
        ItemStack item = new ItemStack(Material.ENDER_EYE);
        ItemMeta meta = item.getItemMeta();
        meta.displayName(TextAPI.parse("§aRecompensas"));
        meta.setLore(Arrays.asList(
                "§7Visualize as recompensas dadas",
                "§7a partir do seu tempo online",
                "",
                "§aClique para visualizar."
        ));

        item.setItemMeta(meta);
        return item;
    }

    public ItemStack ranking() {
        String value = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZTM0YTU5MmE3OTM5N2E4ZGYzOTk3YzQzMDkxNjk0ZmMyZmI3NmM4ODNhNzZjY2U4OWYwMjI3ZTVjOWYxZGZlIn19fQ==";

        ItemStack skull = SkullAPI.fromBase64(SkullAPI.Type.BLOCK, value);
        ItemMeta meta = skull.getItemMeta();
        meta.displayName(TextAPI.parse("§6Destaques"));
        meta.setLore(Arrays.asList(
                "§7Confira agora os jogadores",
                "§7que mais de destacam.",
                "",
                "§6Clique para acessar."
        ));

        skull.setItemMeta(meta);
        return skull;
    }

    public static TimeInventory getInstance() {
        if (instance == null) instance = new TimeInventory();
        return instance;
    }
}
