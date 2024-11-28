package blizzard.development.spawners.handlers.rewards;

import blizzard.development.spawners.utils.PluginImpl;
import org.bukkit.Bukkit;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;

import java.util.*;

public class RewardsHandler {
    private static RewardsHandler instance;

    public static class Reward {
        private final String material;
        private final String display;
        private final String command;

        public Reward(String material, String display, String command) {
            this.material = material;
            this.display = display;
            this.command = command;
        }

        public String getMaterial() {
            return material;
        }

        public String getDisplay() {
            return display;
        }

        public String getCommand() {
            return command;
        }
    }

    public List<Reward> getRewards(String rewardKey) {
        ConfigurationSection rewardSection = PluginImpl.getInstance().Rewards.getConfig()
                .getConfigurationSection("rewards." + rewardKey);
        List<Reward> rewards = new ArrayList<>();

        if (rewardSection == null) {
            return rewards;
        }

        for (String key : rewardSection.getKeys(false)) {
            String material = rewardSection.getString(key + ".material", "Sem Material");
            String display = rewardSection.getString(key + ".display", "Sem Display");
            String command = rewardSection.getString(key + ".command");

            if (command != null) {
                rewards.add(new Reward(material, display, command));
            }
        }

        return rewards;
    }

    public Reward giveRandomReward(Player player) {
        List<String> rewardKeys = getAllRewardKeys();
        if (rewardKeys.isEmpty()) {
            return null;
        }

        Random random = new Random();
        String randomRewardKey = rewardKeys.get(random.nextInt(rewardKeys.size()));

        List<Reward> rewards = getRewards(randomRewardKey);
        if (rewards.isEmpty()) {
            return null;
        }

        Reward randomReward = rewards.get(random.nextInt(rewards.size()));
        String formattedCommand = randomReward.getCommand().replace("{player}", player.getName());
        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), formattedCommand);

        return randomReward;
    }


    public List<String> getAllRewardKeys() {
        ConfigurationSection rewardsSection = PluginImpl.getInstance().Rewards.getConfig()
                .getConfigurationSection("rewards");
        List<String> rewardKeys = new ArrayList<>();

        if (rewardsSection != null) {
            rewardKeys.addAll(rewardsSection.getKeys(false));
        }

        return rewardKeys;
    }

    public static RewardsHandler getInstance() {
        if (instance == null) instance = new RewardsHandler();
        return instance;
    }
}
