package blizzard.development.mine.utils.text;

import blizzard.development.mine.mine.adapters.ExtractorAdapter;
import org.bukkit.entity.Player;

public class ProgressBarUtils {
    private static final ProgressBarUtils instance = new ProgressBarUtils();

    public static ProgressBarUtils getInstance() {
        return instance;
    }

    public String extractor(Player player) {
        int currentProgress = ExtractorAdapter.getInstance().getExtractorBlocks(player) - 1;
        int maxProgress = 200;
        int greenBars = (int) ((currentProgress / (double) maxProgress) * 10);
        int grayBars = 10 - greenBars;

        return "§a" + "|".repeat(greenBars) + "§7" + "|".repeat(grayBars);
    }
}
