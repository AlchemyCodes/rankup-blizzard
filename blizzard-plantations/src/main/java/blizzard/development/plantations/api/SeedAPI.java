package blizzard.development.plantations.api;

import blizzard.development.plantations.database.cache.methods.PlayerCacheMethod;
import org.bukkit.entity.Player;

public class SeedAPI {

    private static SeedAPI instance;

    public double getSeedBalance(Player player) {
        PlayerCacheMethod playerCacheMethod = PlayerCacheMethod.getInstance();

        if (player != null) {
            return playerCacheMethod.getPlantations(player);
        }
        return 0;
    }

    public void setSeed(Player player, int amount) {
        PlayerCacheMethod playerCacheMethod = PlayerCacheMethod.getInstance();

        if (player != null) {
            playerCacheMethod.setPlantations(player, amount);
        }
    }

    public void addSeed(Player player, int amount) {
        PlayerCacheMethod playerCacheMethod = PlayerCacheMethod.getInstance();

        if (player != null) {
            playerCacheMethod.setPlantations(player, playerCacheMethod.getPlantations(player) + amount);
        }
    }

    public void removeSeed(Player player, int amount) {
        PlayerCacheMethod playerCacheMethod = PlayerCacheMethod.getInstance();

        if (player != null) {
            playerCacheMethod.setPlantations(player, playerCacheMethod.getPlantations(player) - amount);
        }
    }

    public static SeedAPI getInstance() {
        if (instance == null) {
            instance = new SeedAPI();
        }

        return instance;
    }
}
