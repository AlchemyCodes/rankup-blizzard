package blizzard.development.mine.utils.text;

import blizzard.development.mine.listeners.mine.MineBlockBreakListener;
import org.bukkit.entity.Player;

public class ProgressBarUtils {
    private static final ProgressBarUtils instance = new ProgressBarUtils();

    public static ProgressBarUtils getInstance() {
        return instance;
    }

    public String extractor(Player player) {
        int currentProgress = MineBlockBreakListener.extractorBlocks.getOrDefault(player, 0);
        int maxProgress = 200;
        int greenBars = (int) ((currentProgress / (double) maxProgress) * 10);
        int grayBars = 10 - greenBars;

        return "§a" + "|".repeat(greenBars) + "§7" + "|".repeat(grayBars);
    }
}
