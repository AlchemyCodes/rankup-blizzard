package blizzard.development.monsters.monsters.managers.monsters.rewards;

import blizzard.development.monsters.database.cache.managers.PlayersCacheManager;
import blizzard.development.monsters.database.cache.methods.PlayersCacheMethods;
import blizzard.development.monsters.database.storage.PlayersData;
import blizzard.development.monsters.utils.NumberFormatter;
import blizzard.development.monsters.utils.PluginImpl;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.*;
import java.util.stream.Collectors;

public class MonstersRewardManager {
    private static MonstersRewardManager instance;

    public void collectReward(Player player, String rewardType, boolean all) {
        PlayersCacheMethods methods = PlayersCacheMethods.getInstance();
        PlayersData data = PlayersCacheManager.getInstance().getPlayerData(player);

        if (data == null || data.getRewards().isEmpty()) {
            player.sendActionBar("§c§lEI! §cVocê não possui recompensas do tipo §7" + MonstersRewardManager.getInstance().getDisplayName(rewardType) + "§c para coletar.");
            return;
        }

        List<String> matchingRewards = data.getRewards().stream()
                .filter(reward -> reward.equalsIgnoreCase(rewardType))
                .collect(Collectors.toList());

        if (matchingRewards.isEmpty()) {
            player.sendActionBar("§c§lEI! §cVocê não possui recompensas do tipo §7" + MonstersRewardManager.getInstance().getDisplayName(rewardType) + "§c para coletar.");
            return;
        }

        int totalToCollect = all ? matchingRewards.size() : 1;

        Map<String, Integer> rewardsWithStackInfo = new HashMap<>();
        rewardsWithStackInfo.put(rewardType, totalToCollect);

        if (!hasEmptySlots(player, rewardsWithStackInfo)) {
            player.sendActionBar("§c§lEI! §cVocê não tem espaço suficiente no inventário para isso.");
            return;
        }

        if (all) {
            methods.removeRewards(player, matchingRewards);
        } else {
            methods.removeReward(player, matchingRewards.get(0));
        }

        String commandTemplate = getCommand(rewardType);
        if (commandTemplate != null && !commandTemplate.isEmpty()) {
            String groupedCommand = commandTemplate
                    .replace("{player}", player.getName())
                    .replace("{amount}", String.valueOf(totalToCollect));
            player.getServer().dispatchCommand(player.getServer().getConsoleSender(), groupedCommand);
        }

        Arrays.asList(
                "",
                " §b§lMonstros! §7Você coletou as recompensas.",
                " §7O total de §f" + NumberFormatter.getInstance().formatNumber(totalToCollect) + "§7 recompensa(s) do tipo",
                " §f" + MonstersRewardManager.getInstance().getDisplayName(rewardType) + "§7 foram coletadas com sucesso.",
                ""
        ).forEach(player::sendMessage);
    }

    public void collectAllRewards(Player player) {
        PlayersCacheMethods cacheMethods = PlayersCacheMethods.getInstance();
        PlayersData data = PlayersCacheManager.getInstance().getPlayerData(player);

        if (data == null || data.getRewards().isEmpty()) {
            player.sendActionBar("§c§lEI! §cVocê não possui recompensas para coletar.");
            return;
        }

        List<String> allRewards = new ArrayList<>(data.getRewards());
        int totalRewards = allRewards.size();

        Map<String, Integer> groupedRewards = allRewards.stream()
                .collect(Collectors.groupingBy(reward -> reward, Collectors.summingInt(reward -> 1)));

        if (!hasEmptySlots(player, groupedRewards)) {
            player.sendActionBar("§c§lEI! §cVocê não tem espaço suficiente no inventário para isso.");
            return;
        }

        cacheMethods.removeRewards(player, allRewards);

        groupedRewards.forEach((type, count) -> {
            String commandTemplate = getCommand(type);
            if (commandTemplate != null && !commandTemplate.isEmpty()) {
                String groupedCommand = commandTemplate
                        .replace("{player}", player.getName())
                        .replace("{amount}", String.valueOf(count));
                player.getServer().dispatchCommand(player.getServer().getConsoleSender(), groupedCommand);
            }
        });

        Arrays.asList(
                "",
                " §b§lMonstros! §7Você capturou todas as recompensas.",
                " §7O total de §f" + NumberFormatter.getInstance().formatNumber(totalRewards) + "§7 recompensa(s) foram",
                " §7coletadas com sucesso.",
                ""
        ).forEach(player::sendMessage);
    }

    public boolean hasEmptySlots(Player player, Map<String, Integer> rewardsWithStackInfo) {
        int requiredSlots = 0;

        for (Map.Entry<String, Integer> entry : rewardsWithStackInfo.entrySet()) {
            String rewardName = entry.getKey();
            int rewardCount = entry.getValue();

            boolean withStack = isWithStack(rewardName);
            if (withStack) {
                requiredSlots++;
            } else {
                requiredSlots += (int) Math.ceil((double) rewardCount / 64);
            }
        }

        int emptySlots = 0;

        for (ItemStack item : player.getInventory().getStorageContents()) {
            if (item == null || item.getType() == Material.AIR) {
                emptySlots++;
            }

            if (emptySlots >= requiredSlots) {
                return true;
            }
        }

        return false;
    }


    private final PluginImpl plugin = PluginImpl.getInstance();

    public String getMaterial(String rewardName) {
        return plugin.Rewards.getConfig().getString("rewards." + rewardName + ".material");
    }

    public String getDisplayName(String rewardName) {
        return plugin.Rewards.getConfig().getString("rewards." + rewardName + ".display-name");
    }

    public List<String> getLore(String rewardName) {
        return plugin.Rewards.getConfig().getStringList("rewards." + rewardName + ".lore");
    }

    public Boolean isWithStack(String rewardName) {
        return plugin.Rewards.getConfig().getBoolean("rewards." + rewardName + ".with-stack");
    }

    public String getCommand(String rewardName) {
        return plugin.Rewards.getConfig().getString("rewards." + rewardName + ".command");
    }

    public ConfigurationSection getSection() {
        return plugin.Rewards.getConfig().getConfigurationSection("rewards");
    }

    public Set<String> getAll() {
        if (getSection() == null) return null;

        Set<String> keys = getSection().getKeys(false);
        if (keys.isEmpty()) return null;

        return keys;
    }

    public static MonstersRewardManager getInstance() {
        if (instance == null) instance = new MonstersRewardManager();
        return instance;
    }
}
