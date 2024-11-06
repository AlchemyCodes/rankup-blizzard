package blizzard.development.fishing.utils.fish;

public class FishesUtils {
    public static int getStrengthNecessary(String rarity) {
        return switch (rarity.toLowerCase()) {
            case "common" -> 1;
            case "rare" -> 2;
            case "legendary" -> 3;
            case "mystic" -> 4;
            default -> 0;
        };
    }
}
