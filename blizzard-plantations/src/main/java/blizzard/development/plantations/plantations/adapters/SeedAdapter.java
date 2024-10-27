package blizzard.development.plantations.plantations.adapters;

import blizzard.development.plantations.plantations.enums.SeedEnum;
import blizzard.development.plantations.plantations.factory.SeedFactory;

public class SeedAdapter implements SeedFactory {
    @Override
    public void giveSeed(SeedEnum seedEnum) {

        switch (seedEnum.getName()) {
            case "tomate":
                // enviar a semente do tomate
                break;
        }
    }
}
