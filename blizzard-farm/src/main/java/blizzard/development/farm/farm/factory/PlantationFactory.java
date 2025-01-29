package blizzard.development.farm.farm.factory;

import org.bukkit.block.Block;
import org.bukkit.entity.Player;

public interface PlantationFactory {

    void handleBreakCarrots(Player player, Block block);
    void handleBreakPotatoes(Player player, Block block);
    void handleBreakWheat(Player player, Block block);
    void handleBreakMelon(Player player, Block block);
    void handleBreakCactus(Player player, Block block);
}
