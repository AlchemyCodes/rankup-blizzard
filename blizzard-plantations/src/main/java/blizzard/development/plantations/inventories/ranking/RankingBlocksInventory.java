package blizzard.development.plantations.inventories.ranking;

import blizzard.development.plantations.builder.ItemBuilder;
import blizzard.development.plantations.database.cache.PlayerCacheManager;
import blizzard.development.plantations.database.storage.PlayerData;
import blizzard.development.plantations.inventories.FarmInventory;
import blizzard.development.plantations.utils.PluginImpl;
import blizzard.development.plantations.utils.items.SkullAPI;
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

import static blizzard.development.plantations.utils.NumberFormat.formatNumber;

public class RankingBlocksInventory {
    public void open(Player player) {
        ChestGui inventory = new ChestGui(5, "§8Destaques (Blocos quebrados)");
        StaticPane pane = new StaticPane(0, 0, 9, 5);


        List<PlayerData> topPlayers = PlayerCacheManager.getInstance().getTopBlocks(10);

        for (String item : PluginImpl.getInstance().Ranking.getConfig().getConfigurationSection("ranking-blocks").getKeys(false)) {

            String itemType = PluginImpl.getInstance().Ranking.getConfig().getString("ranking-blocks." + item + ".item");
            int itemSlot = PluginImpl.getInstance().Ranking.getConfig().getInt("ranking-blocks." + item + ".slot");
            String displayName = PluginImpl.getInstance().Ranking.getConfig().getString("ranking-blocks." + item + ".display-name").replace("&", "§");
            int top = PluginImpl.getInstance().Ranking.getConfig().getInt("ranking-blocks." + item + ".top");
            List<String> lore = PluginImpl.getInstance().Ranking.getConfig().getStringList("ranking-blocks." + item + ".lore");

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
                FarmInventory farmInventory = new FarmInventory();
                farmInventory.open(player);
                event.setCancelled(true);
            });

            GuiItem changerItem = new GuiItem(changer(), event -> {
                RankingSeedsInventory rankingSeedsInventory = new RankingSeedsInventory();
                rankingSeedsInventory.open(player);
                event.setCancelled(true);
            });

            pane.addItem(rankingItem, Slot.fromIndex(itemSlot));
            pane.addItem(backItem, Slot.fromIndex(36));
            pane.addItem(changerItem, Slot.fromIndex(40));

        }

        inventory.addPane(pane);
        inventory.show(player);
        player.playSound(player.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 0.5f, 0.5f);
    }

    public static ItemStack changer() {
        return new ItemBuilder(Material.MINECART)
            .setDisplayName("§aMudar categoria")
            .setLore(Arrays.asList(
                "§7Mude a categoria",
                "§7de destaques.",
                "",
                " §3▼ §fPlantações Q.",
                " §a✿ §7Sementes",
                "",
                "§aClique para mudar."
            ))
            .build();
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