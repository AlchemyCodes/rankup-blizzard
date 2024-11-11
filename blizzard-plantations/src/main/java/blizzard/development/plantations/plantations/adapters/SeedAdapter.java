package blizzard.development.plantations.plantations.adapters;

import blizzard.development.plantations.plantations.enums.SeedEnum;
import blizzard.development.plantations.plantations.factory.SeedFactory;
import blizzard.development.plantations.plantations.item.seeds.CarrotSeedBuildItem;
import blizzard.development.plantations.plantations.item.seeds.CornSeedBuildItem;
import blizzard.development.plantations.plantations.item.seeds.PotatoSeedBuildItem;
import blizzard.development.plantations.plantations.item.seeds.TomatoSeedBuildItem;
import org.bukkit.entity.Player;

public class SeedAdapter implements SeedFactory {
    @Override
    public void giveSeed(SeedEnum seedEnum, Player player, int amount) {

        switch (seedEnum.getName()) {
            case "batata" -> player.getInventory().addItem(PotatoSeedBuildItem.potatoSeed(amount));
            case "cenoura" -> player.getInventory().addItem(CarrotSeedBuildItem.carrotSeed(amount));
            case "milho" -> player.getInventory().addItem(CornSeedBuildItem.cornSeed(amount));
            case "tomate" -> player.getInventory().addItem(TomatoSeedBuildItem.tomatoSeed(amount));
        }
    }
}
