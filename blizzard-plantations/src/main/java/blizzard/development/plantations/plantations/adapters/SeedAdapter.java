package blizzard.development.plantations.plantations.adapters;

import blizzard.development.plantations.plantations.enums.SeedEnum;
import blizzard.development.plantations.plantations.factory.SeedFactory;
import blizzard.development.plantations.plantations.item.seeds.LettuceSeedBuildItem;
import blizzard.development.plantations.plantations.item.seeds.TomatoSeedBuildItem;
import org.bukkit.entity.Player;

public class SeedAdapter implements SeedFactory {
    @Override
    public void giveSeed(SeedEnum seedEnum, Player player, int amount) {

        switch (seedEnum.getName()) {
            case "tomate" -> player.getInventory().addItem(TomatoSeedBuildItem.tomatoSeed(amount));
            case "alface" -> player.getInventory().addItem(LettuceSeedBuildItem.lettuceSeed(amount));
        }
    }
}
