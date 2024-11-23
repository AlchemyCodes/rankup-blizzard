package blizzard.development.time.handlers;

import blizzard.development.time.utils.PluginImpl;
import org.bukkit.Bukkit;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class RewardsHandler {
    private static RewardsHandler instance;

    public List<Integer> getMissions() {
        ConfigurationSection section = PluginImpl.getInstance().Missions.getConfig().getConfigurationSection("missions");
        return section == null ? new ArrayList<>() : new ArrayList<>(section.getKeys(false).stream().map(Integer::parseInt).toList());
    }

    public int getMissionTime(int missionId) {
        return PluginImpl.getInstance().Missions.getConfig().getInt("missions." + missionId + ".time", 0);
    }

    public List<Integer> getMissionRewards(int missionId) {
        return PluginImpl.getInstance().Missions.getConfig().getIntegerList("missions." + missionId + ".rewards");
    }

    public String getRewardDisplay(int rewardId) {
        return PluginImpl.getInstance().Rewards.getConfig().getString("rewards." + rewardId + ".display", "Recompensa desconhecida");
    }

    public String getRewardCommand(int rewardId) {
        return PluginImpl.getInstance().Rewards.getConfig().getString("rewards." + rewardId + ".command", "");
    }

    public boolean giveReward(Player player, int rewardId) {
        String command = getRewardCommand(rewardId);
        if (command.isEmpty()) {
            return false;
        }
        command = command.replace("{player}", player.getName());
        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), command);
       return true;
    }

    public boolean giveMissionRewards(Player player, int missionId) {
        List<Integer> rewards = getMissionRewards(missionId);
        if (rewards.isEmpty()) {
            return false;
        }
        rewards.forEach(reward -> giveReward(player, reward));
        return true;
    }

    public static RewardsHandler getInstance() {
        if (instance == null) instance = new RewardsHandler();
        return instance;
    }
}
