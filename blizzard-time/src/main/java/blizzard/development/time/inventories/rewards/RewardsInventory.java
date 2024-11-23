package blizzard.development.time.inventories.rewards;

import blizzard.development.time.database.cache.getters.PlayersCacheGetters;
import blizzard.development.time.handlers.RewardsHandler;
import blizzard.development.time.inventories.TimeInventory;
import blizzard.development.time.utils.TimeConverter;
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

import java.util.ArrayList;
import java.util.List;

public class RewardsInventory {
    private static RewardsInventory instance;

    public void open(Player player, int page) {
        ChestGui inventory = new ChestGui(4, "§8Recompensas");

        StaticPane pane = new StaticPane(0, 0, 9, 4);

        List<Integer> missions = RewardsHandler.getInstance().getMissions();

        String[] slots = {"10", "11", "12", "13", "14", "15", "16", "21", "22", "23"};

        int startIndex = (page - 1) * slots.length;
        int endIndex = Math.min(startIndex + slots.length, missions.size());

        for (int i = 0; i < slots.length; i++) {
            int missionIndex = startIndex + i;
            if (missionIndex < endIndex) {
                int missionId = missions.get(missionIndex);
                GuiItem missionItem = new GuiItem(mission(missionId), event -> {
                    giveReward(player, missionId);
                    event.setCancelled(true);
                });

                GuiItem backItem = new GuiItem(back(), event -> {
                    TimeInventory.getInstance().open(player);
                    event.setCancelled(true);
                });

                pane.addItem(backItem, Slot.fromIndex(27));
                pane.addItem(missionItem, Slot.fromIndex(Integer.parseInt(slots[i])));
            } else {
                GuiItem placeholderItem = new GuiItem(nothing(missionIndex + 1), event -> event.setCancelled(true));
                pane.addItem(placeholderItem, Slot.fromIndex(Integer.parseInt(slots[i])));
            }
        }

        if (missions.size() > endIndex) {
            GuiItem nextItem = new GuiItem(next(), event -> {
                open(player, page + 1);
                event.setCancelled(true);
            });
            pane.addItem(nextItem, Slot.fromIndex(35));
        }

        if (page > 1) {
            GuiItem previousItem = new GuiItem(previous(), event -> {
                open(player, page - 1);
                event.setCancelled(true);
            });
            pane.addItem(previousItem, Slot.fromIndex(27));
        }

        inventory.addPane(pane);
        inventory.show(player);
    }

    private ItemStack mission(int missionId) {
        RewardsHandler handler = RewardsHandler.getInstance();

        ItemStack item = new ItemStack(Material.CHEST);
        ItemMeta meta = item.getItemMeta();

        String display = "§3Missão §l#" + missionId;
        List<Integer> rewards = handler.getMissionRewards(missionId);

        List<String> lore = new ArrayList<>();
        lore.add("");
        lore.add("§7 Tempo: §7" + TimeConverter.convertSecondsToTimeFormat(handler.getMissionTime(missionId)));
        lore.add("§7 Recompensas:");
        for (int rewardId : rewards) {
            lore.add(" §8■ " + handler.getRewardDisplay(rewardId));
        }
        lore.add("");
        lore.add("§3Clique aqui para coletar.");

        meta.displayName(TextAPI.parse(display));
        meta.setLore(lore);
        item.setItemMeta(meta);
        return item;
    }

    private ItemStack nothing(int missionId) {
        ItemStack item = new ItemStack(Material.BARRIER);
        ItemMeta meta = item.getItemMeta();
        meta.displayName(TextAPI.parse("§3Recompensa §l#" + missionId));
        meta.setLore(List.of("§7Nenhuma informação."));
        item.setItemMeta(meta);
        return item;
    }

    private ItemStack back() {
        ItemStack item = new ItemStack(Material.RED_DYE);
        ItemMeta meta = item.getItemMeta();
        meta.displayName(TextAPI.parse("§cVoltar"));
        meta.setLore(List.of("§7Clique aqui para voltar", "§7ao menu anterior."));
        item.setItemMeta(meta);
        return item;
    }

    private ItemStack previous() {
        ItemStack item = new ItemStack(Material.RED_DYE);
        ItemMeta meta = item.getItemMeta();
        meta.displayName(TextAPI.parse("§cVoltar"));
        meta.setLore(List.of("§7Clique aqui para voltar."));
        item.setItemMeta(meta);
        return item;
    }

    private ItemStack next() {
        ItemStack item = new ItemStack(Material.LIME_DYE);
        ItemMeta meta = item.getItemMeta();
        meta.displayName(TextAPI.parse("§aAvançar"));
        meta.setLore(List.of("§7Clique aqui para avançar."));
        item.setItemMeta(meta);
        return item;
    }

    private void giveReward(Player player, int missionId) {
        PlayersCacheGetters getters = PlayersCacheGetters.getInstance();
        RewardsHandler handler = RewardsHandler.getInstance();

        if (getters.getPlayTime(player) >= handler.getMissionTime(missionId)) {
            if (handler.giveMissionRewards(player, missionId)) {
                player.sendActionBar(TextAPI.parse(
                        "§a§lYAY! §aVocê coletou as recompensas da §7missão " + missionId + "§a."
                ));
                player.playSound(player.getLocation(), Sound.ENTITY_VILLAGER_YES, 1.0f, 1.0f);
            } else {
                player.sendActionBar(TextAPI.parse(
                        "§a§lYAY! §cOcorreu um erro ao coletar as recompensas da §7missão " + missionId + "§a."
                ));
                player.playSound(player.getLocation(), Sound.ENTITY_VILLAGER_NO, 0.5f, 0.5f);
            }
        } else {
            player.sendActionBar(TextAPI.parse(
                    "§c§lEI! §cVocê não possui tempo suficiente para coletar as" +
                            " recompensas da §7missão " + missionId + "§c."
            ));
            player.playSound(player.getLocation(), Sound.ENTITY_VILLAGER_NO, 0.5f, 0.5f);
            player.getOpenInventory().close();
        }
    }

    public static RewardsInventory getInstance() {
        if (instance == null) instance = new RewardsInventory();
        return instance;
    }
}
