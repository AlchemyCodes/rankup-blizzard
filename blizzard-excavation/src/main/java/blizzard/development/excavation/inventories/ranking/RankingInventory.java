package blizzard.development.excavation.inventories.ranking;

import blizzard.development.excavation.builder.ItemBuilder;
import blizzard.development.excavation.database.cache.PlayerCacheManager;
import blizzard.development.excavation.database.storage.PlayerData;
import blizzard.development.excavation.inventories.ExcavationInventory;
import blizzard.development.excavation.utils.PluginImpl;
import blizzard.development.excavation.utils.items.SkullAPI;
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
import java.util.stream.Collectors;

import static blizzard.development.excavation.utils.NumberFormat.formatNumber;


public class RankingInventory {

    public final PlayerCacheManager playerCacheManager = new PlayerCacheManager();

    public void open(Player player) {
        ChestGui inventory = new ChestGui(5, "§8Destaques");
        StaticPane pane = new StaticPane(0, 0, 9, 5);


        List<PlayerData> topPlayers = playerCacheManager.getTopPlayers();

        for (String item : PluginImpl.getInstance().Ranking.getConfig().getConfigurationSection("ranking").getKeys(false)) {

            String itemType = PluginImpl.getInstance().Ranking.getConfig().getString("ranking." + item + ".item");
            int itemSlot = PluginImpl.getInstance().Ranking.getConfig().getInt("ranking." + item + ".slot");
            String displayName = PluginImpl.getInstance().Ranking.getConfig().getString("ranking." + item + ".display-name").replace("&", "§");
            int top = PluginImpl.getInstance().Ranking.getConfig().getInt("ranking." + item + ".top");
            List<String> lore = PluginImpl.getInstance().Ranking.getConfig().getStringList("ranking." + item + ".lore");

            List<String> replacedLore;
            ItemStack ranking;

            if (top >= 0 && top < topPlayers.size()) {
                replacedLore = lore.stream()
                        .map(s -> s.replace("&", "§")
                                .replace("{player-name}", topPlayers.get(top).getNickname())
                                .replace("{blocks}", String.valueOf(formatNumber(topPlayers.get(top).getBlocks())))
                        ).collect(Collectors.toList());

                ranking = SkullAPI.withName(new ItemStack(Material.PLAYER_HEAD), topPlayers.get(top).getNickname());
            } else {
                replacedLore = Arrays.asList(
                        "§7Nenhuma informação."
                );
                ranking = new ItemStack(Material.BARRIER);
            }

            ItemMeta meta = ranking.getItemMeta();
            meta.setDisplayName(displayName);
            meta.setLore(replacedLore);

            ranking.setItemMeta(meta);


            GuiItem rankingItem = new GuiItem(ranking, event -> {
                event.setCancelled(true);
            });


            GuiItem backItem = new GuiItem(back(), event -> {
                ExcavationInventory excavationInventory = new ExcavationInventory();
                excavationInventory.open(player);
                event.setCancelled(true);
            });

            pane.addItem(rankingItem, Slot.fromIndex(itemSlot));
            pane.addItem(backItem, Slot.fromIndex(40));

        }

        inventory.addPane(pane);
        inventory.show(player);
        player.playSound(player.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 0.5f, 0.5f);
    }


    public static ItemStack back() {
        return new ItemBuilder(Material.REDSTONE)
                .setDisplayName("§cVoltar")
                .setLore(Arrays.asList(
                        "§7Clique para voltar",
                        "§7ao menu anterior."
                ))
                .build();
    }
}
