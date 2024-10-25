package blizzard.development.excavation.managers;

import blizzard.development.excavation.api.FossilAPI;
import blizzard.development.excavation.tasks.HologramTask;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

import java.util.concurrent.ThreadLocalRandom;

public class RewardManager {

    public static void check(Player player, Block block, HologramTask hologramTask) {
        double percentage = 1;

        if (RewardManager.reward(percentage)) {
            hologramTask.initializeHologramTask(player, block);
            player.sendActionBar("§b§lYAY! §bVocê encontrou um Fóssil de Mamute.");

            FossilAPI.setFossilBalance(player, FossilAPI.getFossilBalance(player) + 1);
        }
    }
    public static boolean reward(double percentage) {

        double randomValue = ThreadLocalRandom.current().nextDouble(0, 100);
        return randomValue < percentage;
    }

}
