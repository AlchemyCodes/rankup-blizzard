package blizzard.development.visuals.visuals.factory;

import blizzard.development.visuals.visuals.enums.VisualEnum;
import org.bukkit.entity.Player;

public interface VisualFactory {

    void giveVisual(Player player, VisualEnum visualEnum, int amount);

}
