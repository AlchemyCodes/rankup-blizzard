package blizzard.development.rankup.inventories;

import blizzard.development.rankup.database.cache.method.PlayersCacheMethod;
import blizzard.development.rankup.utils.PluginImpl;
import blizzard.development.rankup.utils.PrestigeUtils;
import blizzard.development.rankup.utils.RanksUtils;
import com.github.stefvanschie.inventoryframework.gui.GuiItem;
import com.github.stefvanschie.inventoryframework.gui.type.ChestGui;
import com.github.stefvanschie.inventoryframework.pane.StaticPane;
import com.github.stefvanschie.inventoryframework.pane.util.Slot;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class RankInventory {

    public static boolean autoRank = false;

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
        GuiItem autorankup = new GuiItem(autoRankup(), event -> {
            event.setCancelled(true);
            if (!autoRank) {
                autoRank = true;
                player.sendMessage("ativou autorank");
            } else {
                autoRank = false;
                player.sendMessage("desativou autorank");
            }
        });

        pane.addItem(information, Slot.fromIndex(config.getInt("rankInventory.items.information.slot")));
        pane.addItem(ranks, Slot.fromIndex(config.getInt("rankInventory.items.ranks.slot")));
        pane.addItem(tops, Slot.fromIndex(config.getInt("rankInventory.items.tops.slot")));
        pane.addItem(rankup, Slot.fromIndex(config.getInt("rankInventory.items.rankup.slot")));
        pane.addItem(autorankup, Slot.fromIndex(config.getInt("rankInventory.items.auto-rankup.slot")));

        gui.addPane(pane);
        gui.show(player);
    }


    public static ItemStack information(Player player) {
        YamlConfiguration config = (PluginImpl.getInstance()).Inventories.getConfig();
        YamlConfiguration ranksConfig = (PluginImpl.getInstance()).Ranks.getConfig();

        PlayersCacheMethod playersData = PlayersCacheMethod.getInstance();
        String currentRank = playersData.getRank(player);
        int prestige = playersData.getPrestige(player);

        ConfigurationSection currentRankSection = RanksUtils.getCurrentRankSection(ranksConfig, currentRank);
        ConfigurationSection infoConfig = config.getConfigurationSection("rankInventory.items.information");

        assert infoConfig != null;
        Material material = Material.valueOf(infoConfig.getString("material"));
        String displayName = infoConfig.getString("displayName");

        List<String> lore = infoConfig.getStringList("lore")
                .stream().map(line -> {
                    assert currentRankSection != null;
                    return line.replace("{current_rank}", Objects.requireNonNull(RanksUtils.getCurrentRankName(ranksConfig, currentRank)))
                            .replace("{next_rank}", (RanksUtils.getNextRank(ranksConfig, currentRankSection) != null) ?
                                    Objects.requireNonNull(RanksUtils.getNextRank(ranksConfig, currentRankSection)) : "Nenhum").replace("{prestige}",
                                    String.valueOf(prestige)).replace("{next_prestige}", String.valueOf(prestige + 1))
                            .replace("{prestige_cost}", String.valueOf(PrestigeUtils.prestigeCoinsCostAdd(prestige)));
                })
                .collect(Collectors.toList());

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

        assert backConfig != null;
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

        assert backConfig != null;
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

        assert backConfig != null;
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

    public static ItemStack autoRankup() {
        YamlConfiguration config = (PluginImpl.getInstance()).Inventories.getConfig();
        ConfigurationSection backConfig = config.getConfigurationSection("rankInventory.items.auto-rankup");

        assert backConfig != null;
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
