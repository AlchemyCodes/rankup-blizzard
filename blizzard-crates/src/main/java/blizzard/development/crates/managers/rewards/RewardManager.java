package blizzard.development.crates.managers.rewards;

import org.bukkit.entity.Player;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class RewardManager {

    public String generateReward(Player player) {

        List<String> rewards = Arrays.asList(
                "give " + player.getName() +" minecraft:packed_ice",
                "give " + player.getName() +" minecraft:blue_ice",
                "give " + player.getName() +" minecraft:ice",
                "give " + player.getName() +" minecraft:snowball"
        );

        int randomGenerator = new Random().nextInt(rewards.size());
        return rewards.get(randomGenerator);
    }

}
