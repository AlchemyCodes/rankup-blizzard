package blizzard.development.rankup.inventories;

import blizzard.development.rankup.database.cache.PlayersCacheManager;
import blizzard.development.rankup.database.cache.method.PlayersCacheMethod;
import blizzard.development.rankup.database.storage.PlayersData;
import blizzard.development.rankup.utils.PluginImpl;
import blizzard.development.rankup.utils.PrestigeUtils;
import blizzard.development.rankup.utils.RanksUtils;
import blizzard.development.rankup.utils.items.skulls.SkullAPI;
import com.github.stefvanschie.inventoryframework.gui.GuiItem;
import com.github.stefvanschie.inventoryframework.gui.type.ChestGui;
import com.github.stefvanschie.inventoryframework.pane.StaticPane;
import com.github.stefvanschie.inventoryframework.pane.util.Slot;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class TopsInventory {
    public static void openTopInventory(Player player) {
        YamlConfiguration config = PluginImpl.getInstance().Inventories.getConfig();
        int size = config.getInt("topsInventory.size");
        String title = config.getString("topsInventory.title", "Tops Inventory");

        ChestGui inventory = new ChestGui(size, title);
        StaticPane pane = new StaticPane(0, 0, 9, size);

        List<PlayersData> topPlayers = PlayersCacheManager.getInstance().getAllPlayersData().stream()
                .sorted((p1, p2) -> Integer.compare(p2.getPrestige(), p1.getPrestige()))
                .limit(10)
                .toList();

        int[] itemSlots = {10, 11, 12, 13, 14, 15, 16, 21, 22, 23};

        ConfigurationSection inventoryConfig = config.getConfigurationSection("topsInventory.items.head");
        if (inventoryConfig != null) {
            Material itemType = Material.valueOf(inventoryConfig.getString("material"));
            for (int i = 0; i < topPlayers.size(); i++) {
                PlayersData playerData = topPlayers.get(i);
                ItemStack ranking = SkullAPI.withName(new ItemStack(itemType), playerData.getNickname());
                ItemMeta meta = ranking.getItemMeta();
                if (meta != null) {
                    meta.setDisplayName(inventoryConfig.getString("displayName").replace("{rank}", String.valueOf(i + 1)));
                    meta.setLore(inventoryConfig.getStringList("lore").stream()
                            .map(line -> line.replace("{nickname}", playerData.getNickname())
                                    .replace("{prestige}", String.valueOf(playerData.getPrestige())))
                            .collect(Collectors.toList()));
                    ranking.setItemMeta(meta);
                }
                pane.addItem(new GuiItem(ranking, event -> event.setCancelled(true)), Slot.fromIndex(itemSlots[i]));
            }
        }

        pane.addItem(new GuiItem(createItem("topsInventory.items.information", player), event -> event.setCancelled(true)), Slot.fromIndex(41));
        pane.addItem(new GuiItem(createItem("topsInventory.items.back", player), event -> {
            RankInventory.openRankInventory(player);
            event.setCancelled(true);
        }), Slot.fromIndex(27));

        inventory.addPane(pane);
        inventory.show(player);
        player.playSound(player.getLocation(), Sound.BLOCK_CHEST_OPEN, 0.5f, 0.5f);
    }

    private static ItemStack createItem(String path, Player player) {
        YamlConfiguration config = PluginImpl.getInstance().Inventories.getConfig();
        ConfigurationSection section = config.getConfigurationSection(path);
        if (section == null) return new ItemStack(Material.BARRIER);

        Material material = Material.valueOf(section.getString("material", "BARRIER"));
        String displayName = section.getString("displayName", "Item");
        List<String> lore = section.getStringList("lore").stream()
                .map(line -> formatPlaceholders(line, player))
                .collect(Collectors.toList());

        ItemStack item = new ItemStack(material);
        ItemMeta meta = item.getItemMeta();
        if (meta != null) {
            meta.setDisplayName(displayName);
            meta.setLore(lore);
            item.setItemMeta(meta);
        }
        return item;
    }

    private static String formatPlaceholders(String text, Player player) {
        PlayersCacheMethod playersData = PlayersCacheMethod.getInstance();
        YamlConfiguration ranksConfig = PluginImpl.getInstance().Ranks.getConfig();
        String currentRank = playersData.getRank(player);
        int prestige = playersData.getPrestige(player);
        ConfigurationSection currentRankSection = RanksUtils.getCurrentRankSection(ranksConfig, currentRank);

        return text.replace("{current_rank}", Objects.requireNonNull(RanksUtils.getCurrentRankName(ranksConfig, currentRank)))
                .replace("{next_rank}", RanksUtils.getNextRankName(ranksConfig, currentRankSection) != null ? Objects.requireNonNull(RanksUtils.getNextRankName(ranksConfig, currentRankSection)) : "Nenhum")
                .replace("{prestige}", String.valueOf(prestige))
                .replace("{next_prestige}", String.valueOf(prestige + 1))
                .replace("{prestige_cost}", String.valueOf(PrestigeUtils.prestigeCoinsCostAdd(prestige)));
    }
}
