package blizzard.development.rankup.inventories;

import blizzard.development.rankup.database.cache.PlayersCacheManager;
import blizzard.development.rankup.database.storage.PlayersData;
import blizzard.development.rankup.utils.PluginImpl;
import blizzard.development.rankup.utils.PrestigeUtils;
import blizzard.development.rankup.utils.RanksUtils;
import com.github.stefvanschie.inventoryframework.gui.GuiItem;
import com.github.stefvanschie.inventoryframework.gui.type.ChestGui;
import com.github.stefvanschie.inventoryframework.pane.Pane;
import com.github.stefvanschie.inventoryframework.pane.StaticPane;
import com.github.stefvanschie.inventoryframework.pane.util.Slot;
import java.util.List;
import java.util.stream.Collectors;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class RankInventory {
    public static void openRankInventory(Player player) {
        YamlConfiguration config = (PluginImpl.getInstance()).Inventories.getConfig();
        int size = config.getInt("rankInventory.size");
        String title = config.getString("rankInventory.title", "Rank");

        ChestGui gui = new ChestGui(size, title);

        StaticPane pane = new StaticPane(0, 0, 9, size);

        GuiItem information = new GuiItem(information(player), event -> event.setCancelled(true));

        GuiItem ranks = new GuiItem(ranks(), event -> {
            event.setCancelled(true); RanksInventory.openRanksInventory(player);
        });
        GuiItem tops = new GuiItem(tops(), event -> {
            event.setCancelled(true); TopsInventory.openTopInventory(player);
        });
        GuiItem rankup = new GuiItem(rankup(), event -> {
            event.setCancelled(true); ConfirmationInventory.openConfirmationInventory(player);
        });
        pane.addItem(information, Slot.fromIndex(config.getInt("rankInventory.items.information.slot")));
        pane.addItem(ranks, Slot.fromIndex(config.getInt("rankInventory.items.ranks.slot")));
        pane.addItem(tops, Slot.fromIndex(config.getInt("rankInventory.items.tops.slot")));
        pane.addItem(rankup, Slot.fromIndex(config.getInt("rankInventory.items.rankup.slot")));

        gui.addPane((Pane)pane);
        gui.show((HumanEntity)player);
    }

    public static ItemStack back() {
        YamlConfiguration config = (PluginImpl.getInstance()).Inventories.getConfig();
        ConfigurationSection backConfig = config.getConfigurationSection("rankInventory.items.back");

        Material material = Material.valueOf(backConfig.getString("material"));
        String displayName = backConfig.getString("displayName");
        List<String> lore = backConfig.getStringList("lore");

        ItemStack back = new ItemStack(material);
        ItemMeta meta = back.getItemMeta();
        meta.setDisplayName(displayName);
        meta.setLore(lore);

        back.setItemMeta(meta);

        return back;
    }

    public static ItemStack information(Player player) {
        YamlConfiguration config = (PluginImpl.getInstance()).Inventories.getConfig();
        YamlConfiguration ranksConfig = (PluginImpl.getInstance()).Ranks.getConfig();

        PlayersData playersData = PlayersCacheManager.getPlayerData(player);
        String currentRank = playersData.getRank();
        int prestige = playersData.getPrestige();

        ConfigurationSection currentRankSection = RanksUtils.getCurrentRankSection(ranksConfig, currentRank);
        ConfigurationSection infoConfig = config.getConfigurationSection("rankInventory.items.information");

        Material material = Material.valueOf(infoConfig.getString("material"));
        String displayName = infoConfig.getString("displayName");

        List<String> lore = (List<String>)infoConfig.getStringList("lore").stream().map(line -> line.replace("{current_rank}", RanksUtils.getCurrentRank(ranksConfig, currentRank)).replace("{next_rank}", (RanksUtils.getNextRank(ranksConfig, currentRankSection) != null) ? RanksUtils.getNextRank(ranksConfig, currentRankSection) : "Nenhum").replace("{prestige}", String.valueOf(prestige)).replace("{next_prestige}", String.valueOf(prestige + 1)).replace("{prestige_cost}", String.valueOf(PrestigeUtils.prestigeCostAdd(prestige)))).collect(Collectors.toList());

        ItemStack info = new ItemStack(material);
        ItemMeta meta = info.getItemMeta();
        meta.setDisplayName(displayName);
        meta.setLore(lore);

        info.setItemMeta(meta);

        return info;
    }

    public static ItemStack ranks() {
        YamlConfiguration config = (PluginImpl.getInstance()).Inventories.getConfig();
        ConfigurationSection backConfig = config.getConfigurationSection("rankInventory.items.ranks");

        Material material = Material.valueOf(backConfig.getString("material"));
        String displayName = backConfig.getString("displayName");
        List<String> lore = backConfig.getStringList("lore");

        ItemStack back = new ItemStack(material);
        ItemMeta meta = back.getItemMeta();
        meta.setDisplayName(displayName);
        meta.setLore(lore);

        back.setItemMeta(meta);

        return back;
    }

    public static ItemStack tops() {
        YamlConfiguration config = (PluginImpl.getInstance()).Inventories.getConfig();
        ConfigurationSection backConfig = config.getConfigurationSection("rankInventory.items.tops");

        Material material = Material.valueOf(backConfig.getString("material"));
        String displayName = backConfig.getString("displayName");
        List<String> lore = backConfig.getStringList("lore");

        ItemStack back = new ItemStack(material);
        ItemMeta meta = back.getItemMeta();
        meta.setDisplayName(displayName);
        meta.setLore(lore);

        back.setItemMeta(meta);

        return back;
    }

    public static ItemStack rankup() {
        YamlConfiguration config = (PluginImpl.getInstance()).Inventories.getConfig();
        ConfigurationSection backConfig = config.getConfigurationSection("rankInventory.items.rankup");

        Material material = Material.valueOf(backConfig.getString("material"));
        String displayName = backConfig.getString("displayName");
        List<String> lore = backConfig.getStringList("lore");

        ItemStack back = new ItemStack(material);
        ItemMeta meta = back.getItemMeta();
        meta.setDisplayName(displayName);
        meta.setLore(lore);

        back.setItemMeta(meta);

        return back;
    }
}
