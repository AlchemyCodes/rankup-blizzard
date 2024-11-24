package blizzard.development.time.inventories.missions;

import blizzard.development.time.database.cache.getters.PlayersCacheGetters;
import blizzard.development.time.database.cache.setters.PlayersCacheSetters;
import blizzard.development.time.handlers.RewardsHandler;
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
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class MissionsInventory {
    private static MissionsInventory instance;

    public void open(Player player, int page) {
        ChestGui inventory = new ChestGui(4, "§8Missões");

        StaticPane pane = new StaticPane(0, 0, 9, 4);

        List<Integer> missions = RewardsHandler.getInstance().getMissions();

        String[] slots = {"11", "12", "13", "14", "15", "20", "21", "22", "23", "24"};

        int startIndex = (page - 1) * slots.length;
        int endIndex = Math.min(startIndex + slots.length, missions.size());

        for (int i = 0; i < slots.length; i++) {
            int missionIndex = startIndex + i;
            if (missionIndex < endIndex) {
                int missionId = missions.get(missionIndex);
                GuiItem missionItem = new GuiItem(mission(player, missionId), event -> {
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

    private ItemStack mission(Player player, int missionId) {
        PlayersCacheGetters getters = PlayersCacheGetters.getInstance();
        RewardsHandler handler = RewardsHandler.getInstance();

        boolean hasTime = getters.getPlayTime(player) >= handler.getMissionTime(missionId);
        boolean containsMission = getters.getCompletedMissions(player).contains(String.valueOf(missionId));

        String value;
        if (containsMission) {
            value = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvOGM3Y2U2ZjdhNzk3YTcxYTkxMWRiYzhlNjI2NzAyYjk3MzViN2QzYzJlOWZjYjI2YjgyY2FjZmM2Y2UwMWYxYSJ9fX0=";
        } else if (hasTime) {
            value = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvM2Q1MWM4M2NjMWViY2E1YTFiNmU2Nzk0N2UyMGI0YTJhNmM5ZWZlYTBjZjQ2OTI5NDQ4ZTBlMzc0MTZkNTgzMyJ9fX0=";
        } else {
            value = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZjEyZjc4N2M1NGRkODlkMTI2OThkZDE3YjU2NTEyOTRjZmI4MDE3ZDZhZDRkMjZlZTZhOTFjZjFkMGMxYzQifX19";
        }

        ItemStack item = SkullAPI.fromBase64(SkullAPI.Type.BLOCK, value);
        ItemMeta meta = item.getItemMeta();

        if (hasTime) {
            meta.addEnchant(Enchantment.DURABILITY, 1, true);
            meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        }

        List<Integer> rewards = handler.getMissionRewards(missionId);

        String display;
        if (containsMission) {
            display = "§eMissão §l#" + missionId;
        } else if (hasTime) {
            display = "§aMissão §l#" + missionId;
        } else {
            display = "§cMissão §l#" + missionId;
        }

        String footer;
        if (containsMission) {
            footer = "§eVocê já coletou essa recompensa.";
        } else if (hasTime) {
            footer = "§aClique aqui para coletar.";
        } else {
            footer = "§cVocê não tem tempo suficiente.";
        }

        List<String> lore = new ArrayList<>();

        lore.add("");
        lore.add("§7 Tempo: §f" + TimeConverter.convertSecondsToTimeFormat(handler.getMissionTime(missionId)));
        lore.add("§7 Recompensas:");
        for (int rewardId : rewards) {
            lore.add(" §8■ " + handler.getRewardDisplay(rewardId));
        }
        lore.add("");
        lore.add(footer);

        meta.displayName(TextAPI.parse(display));
        meta.setLore(lore);
        item.setItemMeta(meta);
        return item;
    }

    private ItemStack nothing(int missionId) {
        ItemStack item = new ItemStack(Material.BARRIER);
        ItemMeta meta = item.getItemMeta();
        meta.displayName(TextAPI.parse("§aMissão §l#" + missionId));
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
        PlayersCacheSetters setters = PlayersCacheSetters.getInstance();
        PlayersCacheGetters getters = PlayersCacheGetters.getInstance();
        RewardsHandler handler = RewardsHandler.getInstance();

        boolean containsMission = getters.getCompletedMissions(player).contains(String.valueOf(missionId));

        if (getters.getPlayTime(player) >= handler.getMissionTime(missionId)) {
             if (getters.getPlayTime(player) >= handler.getMissionTime(missionId) && containsMission) {
                 player.sendActionBar(TextAPI.parse(
                         "§c§lEI! §cVocê já coletou as recompensas da §7Missão " + missionId + "§a."
                 ));
                 player.playSound(player.getLocation(), Sound.ENTITY_VILLAGER_NO, 0.5f, 0.5f);
                 player.getOpenInventory().close();
             } else if (handler.giveMissionRewards(player, missionId)) {
                setters.addCompletedMission(player, List.of(String.valueOf(missionId)));

                player.sendActionBar(TextAPI.parse(
                        "§a§lYAY! §aVocê coletou as recompensas da §7Missão " + missionId + "§a."
                ));
                player.playSound(player.getLocation(), Sound.ENTITY_VILLAGER_YES, 1.0f, 1.0f);
                open(player, 1);
            } else {
                player.sendActionBar(TextAPI.parse(
                        "§a§lYAY! §cOcorreu um erro ao coletar as recompensas da §7Missão " + missionId + "§a."
                ));
                player.playSound(player.getLocation(), Sound.ENTITY_VILLAGER_NO, 0.5f, 0.5f);
                player.getOpenInventory().close();
            }
        } else {
            player.sendActionBar(TextAPI.parse(
                    "§c§lEI! §cVocê não possui tempo suficiente para coletar as" +
                            " recompensas da §7Missão " + missionId + "§c."
            ));
            player.playSound(player.getLocation(), Sound.ENTITY_VILLAGER_NO, 0.5f, 0.5f);
            player.getOpenInventory().close();
        }
    }

    public static MissionsInventory getInstance() {
        if (instance == null) instance = new MissionsInventory();
        return instance;
    }
}
