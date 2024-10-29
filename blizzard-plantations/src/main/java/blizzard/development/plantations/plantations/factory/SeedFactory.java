package blizzard.development.plantations.plantations.factory;

import blizzard.development.plantations.plantations.enums.SeedEnum;
import org.bukkit.entity.Player;

public interface SeedFactory {

    void giveSeed(SeedEnum seedEnum, Player player, int amount);
}
