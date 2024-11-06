package blizzard.development.fishing.database.cache.methods;

import blizzard.development.fishing.database.cache.RodsCacheManager;
import blizzard.development.fishing.database.storage.RodsData;
import blizzard.development.fishing.enums.RodMaterials;
import org.bukkit.entity.Player;

import java.util.Arrays;
import java.util.List;

public class RodsCacheMethod {
    public static int getStrength(Player player) {
        RodsData data = RodsCacheManager.getPlayerData(player);
        return (data != null) ? data.getStrength() : 0;
    }

    public static double getXp(Player player) {
        RodsData data = RodsCacheManager.getPlayerData(player);
        return (data != null) ? data.getXp() : 0;
    }

    public static int getExperienced(Player player) {
        RodsData data = RodsCacheManager.getPlayerData(player);
        return (data != null) ? data.getExperienced() : 0;
    }

    public static int getLucky(Player player) {
        RodsData data = RodsCacheManager.getPlayerData(player);
        return (data != null) ? data.getLucky() : 0;
    }

    public static List<RodMaterials> getMaterials(Player player) {
        RodsData data = RodsCacheManager.getPlayerData(player);
        return (data != null) ? data.getRodMaterials() : Arrays.asList();
    }
}
