package blizzard.development.mine.mine.factory;

import org.bukkit.block.Block;
import org.bukkit.entity.Player;

public interface ExtractorFactory {

    void activeExtractor(Player player, Block block, double money, double blocks);
}
