package blizzard.development.visuals.visuals.adapters;

import blizzard.development.visuals.visuals.enums.VisualEnum;
import blizzard.development.visuals.visuals.factory.VisualFactory;
import blizzard.development.visuals.visuals.item.DiamondVisualBuildItem;
import blizzard.development.visuals.visuals.item.GoldVisualBuildItem;
import blizzard.development.visuals.visuals.item.IronVisualBuildItem;
import blizzard.development.visuals.visuals.item.StoneVisualBuildItem;
import org.bukkit.entity.Player;

public class VisualAdapter implements VisualFactory {

    private static final VisualAdapter instance = new VisualAdapter();
    public static VisualAdapter getInstance() {
        return instance;
    }

    @Override
    public void giveVisual(Player player, VisualEnum visualEnum, int amount) {

        switch (visualEnum) {
            case STONE -> player.getInventory().addItem(StoneVisualBuildItem.stoneVisual(amount));
            case IRON -> player.getInventory().addItem(IronVisualBuildItem.ironVisual(amount));
            case GOLD -> player.getInventory().addItem(GoldVisualBuildItem.goldVisual(amount));
            case DIAMOND -> player.getInventory().addItem(DiamondVisualBuildItem.diamondVisual(amount));
        }
    }
}
