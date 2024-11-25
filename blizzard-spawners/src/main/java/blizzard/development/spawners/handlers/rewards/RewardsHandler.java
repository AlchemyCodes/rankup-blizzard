package blizzard.development.spawners.handlers.rewards;

import blizzard.development.spawners.utils.PluginImpl;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.List;

public class RewardsHandler {
    private static RewardsHandler instance;

    public List<String> getRewardCommands(String rewardKey) {
        return PluginImpl.getInstance().Rewards.getConfig().getStringList("rewards." + rewardKey);
    }

    public boolean giveReward(Player player, String rewardKey) {
        List<String> commands = getRewardCommands(rewardKey);
        if (commands.isEmpty()) {
            return false;
        }

        for (String command : commands) {
            String formattedCommand = command.replace("{player}", player.getName());
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), formattedCommand);
        }
        return true;
    }

    public static RewardsHandler getInstance() {
        if (instance == null) instance = new RewardsHandler();
        return instance;
    }
}
