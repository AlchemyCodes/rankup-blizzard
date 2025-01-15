package blizzard.development.monsters.inventories.rewards.items;

import blizzard.development.monsters.builders.ItemBuilder;
import blizzard.development.monsters.database.cache.managers.PlayersCacheManager;
import blizzard.development.monsters.database.storage.PlayersData;
import blizzard.development.monsters.monsters.managers.monsters.rewards.MonstersRewardManager;
import blizzard.development.monsters.utils.NumberFormatter;
import blizzard.development.monsters.utils.items.TextAPI;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;
import java.util.List;

public class RewardsItems {
    private static RewardsItems instance;

    public ItemStack reward(Player player, String reward) {
        MonstersRewardManager rewardManager = MonstersRewardManager.getInstance();

        Material material;
        if (rewardManager.getMaterial(reward) != null) {
            material = Material.getMaterial(rewardManager.getMaterial(reward));
        } else {
            material = Material.GRASS_BLOCK;
        }

        ItemStack item = new ItemBuilder(material).build(false);
        ItemMeta meta = item.getItemMeta();

        String display;
        if (rewardManager.getDisplayName(reward) != null) {
            display = rewardManager.getDisplayName(reward);
        } else {
            display = "§aRecompensa";
        }

        meta.displayName(TextAPI.parse(display));
        meta.setLore(Arrays.asList(
                "§7Colete esta recompensa.",
                "",
                "§8 ▶ §fQuantia: §7" + NumberFormatter.getInstance().formatNumber(getRewardsAmount(player, reward)),
                "",
                "§f Opções disponíveis:",
                "§8  ■ §fBotão Esquerdo: §7Colete uma.",
                "§8  ■ §fBotão Direito: §7Colete todas.",
                ""
        ));

        int rewardsAmount;
        if (getRewardsAmount(player, reward) <= 0) {
            rewardsAmount = 1;
        } else if (getRewardsAmount(player, reward) >= 64) {
            rewardsAmount = 64;
        } else {
            rewardsAmount = getRewardsAmount(player, reward);
        }

        item.setAmount(rewardsAmount);

        item.setItemMeta(meta);
        return item;
    }

    public ItemStack collectAll() {
        ItemStack item = new ItemStack(Material.HOPPER_MINECART);

        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName("§aColetar todas");
        meta.setLore(Arrays.asList(
                "§7Colete as recompensas",
                "§7que estão armazenadas.",
                "",
                "§aClique para coletar."
        ));
        item.setItemMeta(meta);

        return item;
    }

    public ItemStack nothing() {
        ItemStack item = new ItemStack(Material.BARRIER);
        ItemMeta meta = item.getItemMeta();
        meta.displayName(TextAPI.parse("§7Nenhuma recompensa"));
        item.setItemMeta(meta);
        return item;
    }

    public ItemStack previous() {
        ItemStack item = new ItemStack(Material.RED_DYE);
        ItemMeta meta = item.getItemMeta();
        meta.displayName(TextAPI.parse("§cVoltar"));
        meta.setLore(List.of("§7Clique aqui para voltar."));
        item.setItemMeta(meta);
        return item;
    }

    public ItemStack next() {
        ItemStack item = new ItemStack(Material.LIME_DYE);
        ItemMeta meta = item.getItemMeta();
        meta.displayName(TextAPI.parse("§aAvançar"));
        meta.setLore(List.of("§7Clique aqui para avançar."));
        item.setItemMeta(meta);
        return item;
    }

    private int getRewardsAmount(Player player, String rewardName) {
        int playerRewards = 0;

        for (PlayersData data : PlayersCacheManager.getInstance().playersCache.values()) {
            if (data.getUuid().equals(player.getUniqueId().toString())) {
                for (String reward : data.getRewards()) {
                    if (reward.equals(rewardName)) {
                        playerRewards++;
                    }
                }
            }
        }

        return playerRewards;
    }


    public static RewardsItems getInstance() {
        if (instance == null) instance = new RewardsItems();
        return instance;
    }
}
