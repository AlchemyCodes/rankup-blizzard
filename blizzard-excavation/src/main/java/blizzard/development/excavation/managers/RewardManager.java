package blizzard.development.excavation.managers;

import java.util.concurrent.ThreadLocalRandom;

public class RewardManager {

    public static boolean reward(double percentage) {

        double randomValue = ThreadLocalRandom.current().nextDouble(0, 100);
        return randomValue < percentage;
    }

}
